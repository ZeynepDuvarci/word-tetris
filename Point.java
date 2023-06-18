package com.yazlab.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.Sort;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class Point extends AppCompatActivity {

    private TextView txt_points[]=new TextView[10];
    String Appid="application-0-cudew";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        Realm realm = null;

        int a,i;

        for (i=0;i<10;i++){
            a=getResources().getIdentifier("txt_point"+Integer.toString(i), "id", getPackageName());
            txt_points[i]=findViewById(a);
        }

        Realm.init(this);
        App app=new App(new AppConfiguration.Builder(Appid).build());
        // baska email girilecek
        Credentials credentials=Credentials.emailPassword("email@gmail.com","123456");
        ArrayList<Integer> puan = new ArrayList<Integer>();
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Log.v("User","Logged In succesfully");
                    User user= app.currentUser();
                    mongoClient=user.getMongoClient("mongodb-atlas");
                    mongoDatabase=mongoClient.getDatabase("DemoProje");
                    MongoCollection<Document> mongoCollection=mongoDatabase.getCollection("puanlar");


                    Document queryFlter=new Document().append("userid",user.getId());

                    RealmResultTask<MongoCursor<Document>> findTask=mongoCollection.find(queryFlter).iterator();

                    findTask.getAsync(task->{
                        if(task.isSuccess()){
                            MongoCursor<Document> results=task.get();
                            while(results.hasNext()){
                                Document currentDoc=results.next();
                                if(currentDoc.getInteger("puan")!=null){
                                    int val=currentDoc.getInteger("puan");
                                    puan.add(val);
                                }
                            }

                            Collections.sort(puan, (a, b) -> b.compareTo(a));

                            Log.v("Sonuc",puan.toString());


                            for(int i=0;i<10;i++){
                                if(puan.size()>i){
                                    txt_points[i].setText(Integer.toString(puan.get(i)));
                                }
                            }
                        }
                        else{

                        }
                    });
                }
                else
                {
                    Log.v("User","Failed to Login");
                }
            }
        });
        //veri tabanından en yüksek 10 puan çekilip, sırasıyla txt_points[i] lere yazılmalı
        Log.v("Data",puan.toString());
    }
}