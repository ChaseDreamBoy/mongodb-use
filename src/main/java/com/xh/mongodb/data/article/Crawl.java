package com.xh.mongodb.data.article;

import com.xh.mongodb.entity.Article;
import com.xh.mongodb.service.IArticleService;
import com.xh.mongodb.utils.CurDateUtils;
import com.xh.mongodb.utils.JsonUtils;
import com.xh.mongodb.utils.PostRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Crawl -- 抓取 https://springboot.io/ 的数据
 *
 * @author xiaohe
 */
@Component
public class Crawl {

    private IArticleService articleService;

    public Crawl(IArticleService articleServiceImpl) {
        this.articleService = articleServiceImpl;
    }

    private static int i = 0;

    public void getInfoList() {
        try {
            i = 0;
            String startUrl = "https://springboot.io/latest.json?order=default";
            getInfoList(startUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(i);
    }

    private void getInfoList(String url) throws Exception {
        Map<String, String> header = new HashMap<>();
        String result = PostRequest.doGet(url, header);
        @SuppressWarnings("rawtypes") Map map = JsonUtils.readValue(result, Map.class);
        Map<Integer, String> userInfos = new HashMap<>();
        @SuppressWarnings("unchecked") List<Map<String, Object>> users = (List<Map<String, Object>>) map.get("users");
        for (Map<String, Object> user : users) {
            userInfos.put((Integer) user.get("id"), (String) user.get("name"));
        }
        @SuppressWarnings("unchecked") LinkedHashMap<String, Object> topicList = (LinkedHashMap<String, Object>) map.get("topic_list");
        String nextUrlParam = (String) topicList.get("more_topics_url");
        if (StringUtils.isBlank(nextUrlParam)) {
            return;
        }
        String nextUrl = "https://springboot.io/" + nextUrlParam.replaceAll("\\?", ".json?");
        try {
            @SuppressWarnings("unchecked") List<Map<String, Object>> topics = (List<Map<String, Object>>) topicList.get("topics");

            List<Article> articleInfos = new ArrayList<>();
            for (Map<String, Object> topic : topics) {
                try {
                    Article articleInfo = new Article();

                    String articleId = String.valueOf(topic.get("id"));
                    String title = (String) topic.get("title");
                    String createTimeStr = (String) topic.get("created_at");
                    String createTime = CurDateUtils.dateConvertToStr(CurDateUtils.strConvertToDate(createTimeStr, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd HH:mm:ss");
                    String updateTimeStr = (String) topic.get("last_posted_at");
                    String updateTime = CurDateUtils.dateConvertToStr(CurDateUtils.strConvertToDate(updateTimeStr, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), "yyyy-MM-dd HH:mm:ss");
                    String replyCount = String.valueOf(topic.get("reply_count"));
                    String viewCount = String.valueOf(topic.get("views"));
                    @SuppressWarnings("unchecked") List<String> tags = (List<String>) topic.get("tags");
                    StringBuilder tagSb = new StringBuilder();
                    for (String tag : tags) {
                        if (StringUtils.isNotBlank(tagSb)) {
                            tagSb.append(",");
                        }
                        tagSb.append(tag);
                    }
                    String tagInfos = tagSb.toString();
                    @SuppressWarnings({"unchecked", "rawtypes"}) List<Map> posterInfos = (List<Map>) topic.get("posters");
                    StringBuilder sb = new StringBuilder();
                    //noinspection rawtypes
                    for (Map posterInfo : posterInfos) {
                        if (StringUtils.isNotBlank(sb)) {
                            sb.append(",");
                        }
                        //noinspection SuspiciousMethodCalls
                        sb.append(userInfos.get(posterInfo.get("user_id")));
                    }
                    String posters = sb.toString();

                    articleInfo.setArticleId(articleId);
                    articleInfo.setTitle(title);
                    articleInfo.setCreateTime(createTime);
                    articleInfo.setUpdateTime(updateTime);
                    articleInfo.setTags(tagInfos);
                    articleInfo.setReplyCount(Integer.parseInt(replyCount));
                    articleInfo.setViewCount(Integer.parseInt(viewCount));
                    articleInfo.setPosters(posters);
                    articleInfos.add(articleInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            this.articleService.saveBatch(articleInfos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        getInfoList(nextUrl);
    }

}

