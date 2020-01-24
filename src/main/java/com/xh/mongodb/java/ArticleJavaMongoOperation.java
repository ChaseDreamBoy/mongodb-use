package com.xh.mongodb.java;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * ArticleJavaMongo
 *
 * @author xiaohe
 */
public class ArticleJavaMongoOperation {

    public static void main(String[] args) throws Exception {
        final String dbName = "dream_java_test";
        final String collectionName = "article";
        OperationMongo operationMongo = new OperationMongo(dbName, collectionName);
        add(operationMongo);
        // update(operationMongo);
        // find(operationMongo);
        // delete(operationMongo);
    }

    private static void delete(OperationMongo operationMongo) {
        Document condition1 = new Document("type", "开发问题");
        Document titleCondition = new Document("$regex", "^.*索引疑.*$");
        Document condition2 = new Document("title", titleCondition);

        List<Document> conditions = new ArrayList<>();
        conditions.add(condition1);
        conditions.add(condition2);
        // 也可以使用 document
        BasicDBObject filter = new BasicDBObject("$and", conditions);
        operationMongo.delete(filter);
    }

    private static void find(OperationMongo operationMongo) {
        Document titleCondition = new Document("$regex", "^.*索引疑.*$");
        Document condition1 = new Document("title", titleCondition);
        Document condition2 = new Document("type", "开发问题");

        List<Document> conditions = new ArrayList<>();
        conditions.add(condition1);
        conditions.add(condition2);
        // 也可以使用 document
        Document filter = new Document("$and", conditions);

        FindIterable<Document> documents = operationMongo.find(filter);

        for (Document document : documents) {
            System.out.println(document);
        }
    }

    private static void update(OperationMongo operationMongo) {
        // 用于过滤哪些文档要更新
        BasicDBObject filter = new BasicDBObject();
        List<Document> conditions = new ArrayList<>();
        // 模糊匹配用正则 -- ^.*王.*$ (全匹配)
        Document titleCondition = new Document("$regex", "^.*索引疑.*$");
        Document condition1 = new Document("title", titleCondition);
        Document condition2 = new Document("type", "开发问题");
        conditions.add(condition1);
        conditions.add(condition2);
        // 也可以使用 document
        filter.put("$and", conditions);
        // 具体的 update 内容
        Bson update = new Document("$set", new Document("vote_num", 121));
        // 使用 updateOne 表示无论条件匹配多少条记录，始终只更新第一条；
        // 使用 updateMany 表示条件匹配多少条就更新多少条。
        operationMongo.update(filter, update);
        // 使用 #add() 方法里面的命令查看是否更新
    }

    private static void add(OperationMongo operationMongo) {
        // add
        List<Document> answers = new ArrayList<>();
        Document answer1 = new Document("content", "ps:后面网络恢复了，上面是使用tc命令限制eth0带宽复现的")
                .append("answers_user", "1066844321@qq.com")
                .append("answers_time", "2019-07-25 12:15:00");
        Document answer2 = new Document("content", "确实可以解决这个问题。 你要把这些依赖包放在你项目的WEB-INF/lib目录下面，或者是tomcat的本身的lib目录下面。")
                .append("answers_user", "hugogong TJ")
                .append("answers_time", "2018-05-23 09:36:00");
        answers.add(answer1);
        answers.add(answer2);

        Document articleDocument = new Document();
        articleDocument.append("title", "地理索引疑问")
                .append("type", "开发问题")
                .append("publish_user", "yangdaheng")
                .append("create_time", "2019-08-30 17:36:59")
                .append("content", "对于查询db.singapore_google_geo.aggregate([{\"$unwind\":\"$features\"}, {\"$match\":{\"features.geometry\":{ $geoIntersects: { $geometry: { type: \"MultiPoint\", coordinates: [[ 103.8532, 1.2821527777777777 ], [ 103.864326436447, 1.30971120650773 ]] } } }}}, {“$project”:{“features.properties.name”:1,_id:0}}]) 返回的数据我希望根据动态的入参Point的维度进行返回，例如 103.8532, 1.2821527777777777对应的name1; 103.864326436447, 1.30971120650773对应的name2 需要怎么写查询语句呢")
                .append("answer_num", 0)
                .append("read_num", 25)
                .append("vote_num", 8)
                .append("answers", answers);
        operationMongo.add(articleDocument);
        // 使用命令查看是否插入到数据库中
        // 1. 连接到 mongo : mongo --port 28017
        // 2. 查看是否自动创建数据库 : show dbs; # 可以看到 mongo 创建的 dream_java_test 数据库
        // 3. 使用 dream_java_test 数据库 : use dream_java_test;
        // 4. 查看该数据库中是否有 article 集合 : show collections;
        // 5. 在该集合中查找一条记录看看是不是刚刚插入的记录 : db.article.find().pretty();
    }

}
