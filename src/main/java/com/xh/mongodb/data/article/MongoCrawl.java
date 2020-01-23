package com.xh.mongodb.data.article;

import com.xh.mongodb.entity.MongoArticle;
import com.xh.mongodb.entity.MongoArticleAnswer;
import com.xh.mongodb.service.IMongoArticleAnswerService;
import com.xh.mongodb.service.IMongoArticleService;
import com.xh.mongodb.utils.CurDateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * MongoCrawl
 *
 * @author xiaohe
 */
@Component
public class MongoCrawl {

    private IMongoArticleAnswerService mongoArticleAnswerService;

    private IMongoArticleService mongoArticleService;

    public MongoCrawl(IMongoArticleAnswerService mongoArticleAnswerServiceImpl, IMongoArticleService mongoArticleServiceImpl) {
        this.mongoArticleAnswerService = mongoArticleAnswerServiceImpl;
        this.mongoArticleService = mongoArticleServiceImpl;
    }

    public void saveDate() {
        try {
            int maxPage = 30;
            int page = 1;
            while (page < maxPage) {
                crawlMongo(page);
                page++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void crawlMongo(int page) throws IOException {
        MongoArticle mongoArticle = new MongoArticle();
        Document doc = Jsoup.connect("http://mongoing.com/anspress/page/" + page + "?ap_s&sort=newest").get();
        Elements newsHeadlines = doc.select("#ap-lists > div.question-list");
        try {
            for (Element element : newsHeadlines) {
                Elements articleElements = element.select("article");
                for (Element articleElement : articleElements) {
                    Elements elements = articleElement.select("div.wrap-right");

                    // 回答数
                    String answerNum = elements.select("a.ap-answer-count > span").text();
                    mongoArticle.setAnswerNum(Integer.parseInt(answerNum));
                    // 阅读数
                    String readNum = elements.select("a:nth-child(2) > span").text();
                    mongoArticle.setReadNum(Integer.parseInt(readNum));
                    // 投票数
                    String voteNum = elements.select("a:nth-child(3) > span").text();
                    mongoArticle.setVoteNum(Integer.parseInt(voteNum));

                    Elements linkInfo = articleElement.select("div.ap-list-inner > div.summery > h3 > a");
                    Elements typeElements = articleElement.select(".question-categories-list");
                    StringBuilder typeSb = new StringBuilder();
                    for (Element typeElement : typeElements) {
                        if (StringUtils.isNotBlank(typeSb.toString())) {
                            typeSb.append(",");
                        }
                        typeSb.append(typeElement.text());
                    }
                    // 类别
                    String type = typeSb.toString();
                    mongoArticle.setTypes(type);
                    // 标题
                    String title = linkInfo.text();
                    mongoArticle.setTitle(title);
                    // 详情 url
                    String detailUrl = linkInfo.attr("href");
                    mongoArticle.setDetailUrl(detailUrl);
                    // page url detail url
                    // publish user 
                    String publishUser = articleElement.select("div.ap-list-inner > div.summery.wrap-left > ul.list-taxo.ap-inline-list.clearfix > li > span.ap-post-history > span.who > a").text();
                    mongoArticle.setPublishUser(publishUser);
                    // create time
                    String publishTimeStr = articleElement.select("div.ap-list-inner > div.summery.wrap-left > ul.list-taxo.ap-inline-list.clearfix > li > span.ap-post-history > time").attr("datetime");
                    // 
                    LocalDateTime publishTime = LocalDateTime.parse(publishTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:sszzz"));
                    mongoArticle.setCreateTime(CurDateUtils.dateConvertToStr(publishTime, "yyyy-MM-dd HH:mm:ss"));
                    handleDetail(detailUrl, mongoArticle);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDetail(String url, MongoArticle mongoArticle) throws Exception {
        // MongoArticle
        // MongoArticleAnswer mongo_article_answer
        Document doc = Jsoup.connect(url).get();
        String content = doc.select("#question > div > div.ap-content-inner.no-overflow > div.ap-qmainc > div.question-content").text();
        mongoArticle.setContent(content);
        // answer
        Elements answerElements = doc.select("#answers");

        List<MongoArticleAnswer> mongoArticleAnswers = new ArrayList<>();
        for (Element answerElement : answerElements) {
            MongoArticleAnswer mongoArticleAnswer = new MongoArticleAnswer();

            String answer = answerElement.select("div > div.ap-content-inner.no-overflow > div.ap-amainc > div.answer-content").text();
            String answerUser = answerElement.select("div > div.ap-content-inner.no-overflow > div.ap-amainc > div.ap-user-meta > div.ap-meta > a.author > span").text();
            String answerTimeStr = answerElement.select("div > div.ap-content-inner.no-overflow > div.ap-amainc > div.ap-user-meta > div.ap-meta > a:nth-child(3) > time").attr("title");
            LocalDateTime answerTime = LocalDateTime.parse(answerTimeStr.substring(0, 16), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            if (StringUtils.isNotBlank(answer)) {
                mongoArticleAnswer.setAnswerContent(answer);
                mongoArticleAnswer.setAnswerUser(answerUser);
                mongoArticleAnswer.setAnswerTime(CurDateUtils.dateConvertToStr(answerTime, "yyyy-MM-dd HH:mm:ss"));
                mongoArticleAnswers.add(mongoArticleAnswer);
            }
        }
        // mongoArticle
        MongoArticleInfo mongoArticleInfo = new MongoArticleInfo();
        mongoArticleInfo.setMongoArticle(mongoArticle);
        mongoArticleInfo.setMongoArticleAnswers(mongoArticleAnswers);
        saveArticle(mongoArticleInfo);
    }

    private void saveArticle(MongoArticleInfo mongoArticleInfo) {
        MongoArticle mongoArticle = mongoArticleInfo.getMongoArticle();
        List<MongoArticleAnswer> mongoArticleAnswers = mongoArticleInfo.getMongoArticleAnswers();

        boolean isSuccess = this.mongoArticleService.save(mongoArticle);
        if (isSuccess && CollectionUtils.isNotEmpty(mongoArticleAnswers)) {
            StringBuilder answerIdSb = new StringBuilder();
            for (MongoArticleAnswer answer : mongoArticleAnswers) {
                answer.setArticleId(mongoArticle.getId());
                this.mongoArticleAnswerService.save(answer);
                if (StringUtils.isNotBlank(answerIdSb)) {
                    answerIdSb.append(",");
                }
                answerIdSb.append(answer.getId());
            }
            mongoArticle.setAnswers(answerIdSb.toString());
            this.mongoArticleService.updateById(mongoArticle);
        }


        // this.mongoArticleAnswerService = mongoArticleAnswerServiceImpl;
        // this.mongoArticleService = mongoArticleServiceImpl;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class MongoArticleInfo {

        private MongoArticle mongoArticle;

        private List<MongoArticleAnswer> mongoArticleAnswers;

    }

}

