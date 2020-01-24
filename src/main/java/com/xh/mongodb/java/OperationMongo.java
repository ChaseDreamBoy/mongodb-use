package com.xh.mongodb.java;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * OperationMongo
 *
 * <p>
 * 参照链接 : https://docs.mongodb.com/ecosystem/drivers/java/
 * </p>
 *
 * @author xiaohe
 */
public class OperationMongo {

    private MongoCollection<Document> collection;

    public OperationMongo() {
        init("test", "test");
    }

    public OperationMongo(String dbName, String collectionName) {
        init(dbName, collectionName);
    }

    private void init(String dbName, String collectionName) {
        if (StringUtils.isBlank(dbName)) {
            throw new IllegalArgumentException("dbName is empty.");
        }
        if (StringUtils.isBlank(collectionName)) {
            throw new IllegalArgumentException("collectionName is empty.");
        }
        // 连接字符串没有加上 db 会出现以下错误 : The connection string contains options without trailing slash
        ConnectionString connString = new ConnectionString("mongodb://47.98.142.170:28017/" + dbName + "?w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        this.collection = mongoDatabase.getCollection(collectionName);

        System.out.println(collectionName);
    }

    public FindIterable<Document> find(Document document) {
        return this.collection.find(document);
    }

    public void add(Document document) {
        this.collection.insertOne(document);
    }

    public void update(Bson filter, Bson update) {
        this.collection.updateMany(filter, update);
    }

    public void delete(Bson filter) {
        this.collection.deleteOne(filter);
    }


}
