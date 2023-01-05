package de.kifo.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DataConnection {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public DataConnection() {
        mongoClient = MongoClients.create("mongodb+srv://Kifo:qqTmDXgNT6jqwvmI@chestlock.dkztakd.mongodb.net/?retryWrites=true&w=majority");
        database = mongoClient.getDatabase("JavaBot");
        collection = database.getCollection("UserInformation");
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
