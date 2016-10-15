package ase.smalloven;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Arrays;

/**
 * Created by daras on 09-Oct-16.
 */
public class mongoDB {

    public static void ListDocuments(String CollectionName)
    {
        try
        {
            MongoCredential credential = MongoCredential.createCredential("admin", "smalloven", "123456".toCharArray());


            MongoClient client = new MongoClient(new ServerAddress("ds021166.mlab.com",21166), Arrays.asList(credential));
          // MongoClientURI uri = new MongoClientURI("mongodb://admin:123456@ds053196.mlab.com:53196/recpie?authMechanism=SCRAM-SHA-1");
           // MongoClient client = new MongoClient(uri);
            MongoDatabase database = client.getDatabase("smalloven");
            MongoCollection<Document> collection = database.getCollection(CollectionName);
            Document document = new Document("x", 1);
            collection.insertOne(document);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



}
