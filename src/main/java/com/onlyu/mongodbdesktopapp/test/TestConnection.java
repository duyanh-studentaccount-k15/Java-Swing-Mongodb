package com.onlyu.mongodbdesktopapp.test;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


@SuppressWarnings({"unused", "rawtypes"})
public class TestConnection {


    public static void main(String[] args) {
//        MongoClient mongoClient = new MongoClient();
//        MongoClient mongoClient = new MongoClient( "localhost" );
//        MongoClient mongoClient = new MongoClient("localhost" , 27017);
//        MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
//                new ServerAddress("localhost", 27018),
//                new ServerAddress("localhost", 27019)));

//        MongoCredential credential = MongoCredential.createPlainCredential("onlyu", "coffee-shop", "kinlove32".toCharArray());
//        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
//        auths.add(testAuth);
//
//
//        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
//        MongoClient client = new MongoClient(serverAddress, credentials);

//        MongoDatabase database = mongoClient.getDatabase("coffee-shop");

//        MongoCredential credential = MongoCredential
//                .createCredential("onlyu", "coffee-shop", "kinlove32".toCharArray());
//
//        MongoClientOptions options = MongoClientOptions.builder().sslEnabled(true).build();
//
//        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017),
//                Arrays.asList(credential),
//                options);

        MongoClientURI uri = new MongoClientURI("mongodb://onlyu:kinlove32@192.168.1.6:27097/?authSource=coffee-shop&ssl=false");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("coffee-shop");
        //database.createCollection("coffees", null);

        MongoCollection collection = database.getCollection("coffees");
        long count = collection.countDocuments();
        if (count == 1) System.out.println("Success!");


        collection.find().forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {

                System.out.println(document.toJson());
            }
        });

//        try {
//            String host = System.getenv("DNS_MONGODB");
//            MongoClientURI uri = new MongoClientURI("mongodb://onlyu:kinlove32@" + host + ":27097/?authSource=coffee-shop&ssl=false");
//            mongoClient = new MongoClient(uri);
//            database = mongoClient.getDatabase("coffee-shop");
//            collection = database.getCollection("coffee");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
