package com.xh.mongodb.java;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;
import org.apache.commons.lang3.StringUtils;

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

    public OperationMongo() {
        init("test");
    }

    public OperationMongo(String dbName) {
        init(dbName);
    }

    private void init(String dbName) {
        if (StringUtils.isBlank(dbName)) {
            throw new IllegalArgumentException("dbName is empty.");
        }
        ConnectionString connString = new ConnectionString(
                "mongodb+srv://<username>:<password>@<cluster-address>/test?w=majority"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(dbName);
    }


}
