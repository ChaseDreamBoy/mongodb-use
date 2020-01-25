package com.xh.mongodb.sync;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.mongodb.entity.MongoArticle;
import com.xh.mongodb.entity.MongoArticleAnswer;
import com.xh.mongodb.java.OperationMongo;
import com.xh.mongodb.service.IMongoArticleAnswerService;
import com.xh.mongodb.service.IMongoArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleSync
 *
 * <p>
 * 使用 java 同步 mysql 数据到 mongo
 * </p>
 *
 * @author xiaohe
 */
@Slf4j
@Component
public class SimpleSync {

    private IMongoArticleAnswerService mongoArticleAnswerService;

    private IMongoArticleService mongoArticleService;

    public SimpleSync(IMongoArticleAnswerService mongoArticleAnswerServiceImpl,
                      IMongoArticleService mongoArticleServiceImpl) {
        this.mongoArticleAnswerService = mongoArticleAnswerServiceImpl;
        this.mongoArticleService = mongoArticleServiceImpl;
    }

    public void sync() {
        log.info("start sync data...");
        // get mongo collection
        OperationMongo mongo = new OperationMongo("dream1", "article");
        // get list
        List<MongoArticle> list = this.mongoArticleService.list();
        for (MongoArticle article : list) {
            mongo.add(composeData(article));
        }
        log.info("end sync data...");
    }

    private Document composeData(MongoArticle article) {
        List<Document> answers = new ArrayList<>();
        QueryWrapper<MongoArticleAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", article.getId());
        List<MongoArticleAnswer> list = this.mongoArticleAnswerService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            for (MongoArticleAnswer answer : list) {
                Document answerDocument = new Document("answer_user", answer.getAnswerUser())
                        .append("answer_time", answer.getAnswerTime())
                        .append("answer_content", answer.getAnswerContent());
                answers.add(answerDocument);
            }
        }
        Document document = new Document("answers", answers);
        document.append("title", article.getTitle())
                .append("types", article.getTypes())
                .append("publish_user", article.getPublishUser())
                .append("create_time", article.getCreateTime())
                .append("content", article.getContent())
                .append("answer_num", article.getAnswerNum())
                .append("read_num", article.getReadNum());

        return document;
    }

}
