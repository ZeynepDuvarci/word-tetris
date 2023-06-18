package com.yazlab.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Objects;

//#####################
import io.realm.mongodb.App;
import io.realm.Realm;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.options.InsertManyResult;
//#######################

public class MainActivity extends AppCompatActivity {

    private ImageView imageViews[][] = new ImageView[10][8];
    private TextView txt_chosen_word,txt_point;
    private Button btn_show_activity_point;
    private ArrayList<Integer> chosen_word_index=new ArrayList<>();
    private ImageButton btn_confirm,btn_remove;
    private ArrayList<LinkedHashMap<String,Object>> alphabet_unordered;
    private LinkedHashMap<String,Object>[][] matrix=new LinkedHashMap[10][8];
    private CountDownTimer ct,ct2,ct3,ct4,game_ct,start_ct,move_ct,wrong_ct;

    private ArrayList<Integer> random_numbers_for_ice_character=new ArrayList<>(8);
    private int count_of_wowels=0,count_of_consonants=0,point=0,count_of_wrong_word=0;
    private Random rand = new Random();

    private LinkedHashMap<String,Object> element,element2;
    private Boolean ice_original=Boolean.FALSE,ice_artificial=Boolean.FALSE,ice_artificial2=Boolean.FALSE,wait=Boolean.FALSE,wait2=Boolean.FALSE,wait3=Boolean.FALSE;
    private int[] temporary_index_array=new int[8],ice_check_array,ice_check_array2,ice_check_array3;
    private int[] index_in_word_array=new int[2];

    private int random_number,random_column,r;
    private int random_number2;

    private String mode;


    public CountDownTimer create_start_count_down_timer(int finish_time,int tick_time){
        ct=new CountDownTimer(finish_time,tick_time) {
            int k=0,m;
            @Override

            public void onTick(long l) {
                for (m=0;m<8;m++){
                    if (k!=0) {
                        imageViews[k - 1][m].setImageResource(R.color.black);
                        imageViews[k - 1][m].setTag("black");
                    }

                    //matris elemanına hangi harf geleceği belirleniyor(harfin alfabedeki sırası).

                    if(k==0) {
                        if (m % 2 == 0) {
                            random_number = rand.nextInt(8);
                        } else {
                            Random rand = new Random();
                            random_number = rand.nextInt(21) + 8;
                        }

                        temporary_index_array[m] = random_number;
                    }

                    imageViews[k][m].setImageResource((Integer) alphabet_unordered.get(temporary_index_array[m]).get("image_unselected")); // alphabet in temporary_index_array[m] indexindeki resmi
                    imageViews[k][m].setTag("not_black");
                }
                k++;
            }

            @Override
            public void onFinish() {

                k=(finish_time-500)/500;

                //harfin alfabedeki sırası matrix araylist ine[k][m] kaydediliyor.
                //harfin türü (buz veya normal...) matrix araylist ine[k][m] kaydediliyor.
                //tıklanma sayısı ilk etapta 0 olarak matrix araylist ine[k][m] kaydediliyor.
                for (m=0;m<8;m++) {
                    element = new LinkedHashMap<>();
                    element.put("index_in_alphabet", temporary_index_array[m]);
                    element.put("index_in_word",null);
                    element.put("type", "normal");
                    element.put("count_of_clicks", 0);
                    matrix[k][m] = element;
                }


                if(finish_time-500<4000) {
                    for(k=0;k<10;k++){
                        for(m=0;m<8;m++)
                            imageViews[k][m].setEnabled(true);
                    }
                    Toast.makeText(MainActivity.this,"Oyun başladı",Toast.LENGTH_LONG).show();
                    game_ct = create_game_count_down_timer(5000, 1000);
                    ct.cancel();

                }

                else{
                    create_start_count_down_timer(finish_time - 500, tick_time);
                }

            }
        };

        ct.start();
        return ct;
    }


    public CountDownTimer create_move_count_down_timer(int finish_time,int tick_time){
        wait=Boolean.TRUE;
        ice_artificial = Boolean.FALSE;

        if(wait3!=Boolean.TRUE) {
            random_column = rand.nextInt(8);

            if (count_of_wowels < count_of_consonants) {
                random_number = rand.nextInt(8);
                count_of_wowels++;
            } else {
                random_number = rand.nextInt(21) + 8;
                count_of_consonants++;
            }

            random_numbers_for_ice_character.clear();

            for (r = 0; r < 5; r++) {
                random_numbers_for_ice_character.add(rand.nextInt(29));
            }

            if (random_numbers_for_ice_character.contains(random_number))
                ice_original = Boolean.TRUE;

            else
                ice_original = Boolean.FALSE;

        }

        else
            wait3=Boolean.FALSE;

        ct2=new CountDownTimer(finish_time,tick_time) {
            int k=0,c;
            @Override

            public void onTick(long l) {

                if (k == 0) {
                    if (imageViews[k][random_column].getTag().toString().equals("not_black")) {
                        wait3 = Boolean.TRUE;
                        imageViews[k][random_column].setTag("black");
                        ct2.cancel();
                        new CountDownTimer(500, 500) {

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                move_ct = create_move_count_down_timer(finish_time, tick_time);
                            }
                        }.start();
                    }
                }


                if (wait3 != Boolean.TRUE){
                    if (matrix[k][random_column] == null) {
                        if (ice_original)
                            imageViews[k][random_column].setImageResource((Integer) alphabet_unordered.get(random_number).get("image_ice2"));

                        else
                            imageViews[k][random_column].setImageResource((Integer) alphabet_unordered.get(random_number).get("image_unselected")); // alfabetin random_number numaralı indexindeki resmi

                        imageViews[k][random_column].setTag("not_black");
                        if (k != 0) {
                            wait = Boolean.FALSE;
                            imageViews[k - 1][random_column].setImageResource(R.color.black);
                            imageViews[k - 1][random_column].setTag("black");
                        }
                    } else {

                        ice_check_array = new int[]{k - 1, random_column - 1, k - 1, random_column + 1, k, random_column, k - 2, random_column};

                        if (ice_original == Boolean.FALSE) {
                            for (c = 0; c < 5; c += 2) {
                                if (ice_check_array[c] >= 0 && ice_check_array[c] < 10 && ice_check_array[c + 1] >= 0 && ice_check_array[c + 1] < 8) {
                                    if (matrix[ice_check_array[c]][ice_check_array[c + 1]] != null) {
                                        if (Objects.requireNonNull(matrix[ice_check_array[c]][ice_check_array[c + 1]].get("type")).toString().equals("ice_original")) {
                                            ice_artificial = Boolean.TRUE;
                                            imageViews[k - 1][random_column].setImageResource((Integer) alphabet_unordered.get(random_number).get("image_ice"));
                                            imageViews[k - 1][random_column].setTag("not_black");
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            ice_artificial = Boolean.FALSE;
                            for (c = 0; c < 5; c += 2) {
                                if (ice_check_array[c] >= 0 && ice_check_array[c] < 10 && ice_check_array[c + 1] >= 0 && ice_check_array[c + 1] < 8) {
                                    if (matrix[ice_check_array[c]][ice_check_array[c + 1]] != null) {
                                        if (Objects.requireNonNull(matrix[ice_check_array[c]][ice_check_array[c + 1]].get("type")).toString().equals("normal") == Boolean.TRUE) {
                                            matrix[ice_check_array[c]][ice_check_array[c + 1]].put("type", "ice_artificial");

                                            if((Integer) matrix[ice_check_array[c]][ice_check_array[c + 1]].get("count_of_clicks")!=1)
                                                imageViews[ice_check_array[c]][ice_check_array[c + 1]].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[ice_check_array[c]][ice_check_array[c + 1]].get("index_in_alphabet")).get("image_ice"));

                                            imageViews[ice_check_array[c]][ice_check_array[c + 1]].setTag("not_black");
                                        }
                                    }
                                }
                            }
                        }


                        element = new LinkedHashMap<>();
                        element.put("index_in_alphabet", random_number);
                        element.put("index_in_word", null);
                        if (ice_original)
                            element.put("type", "ice_original");
                        else if (ice_artificial)
                            element.put("type", "ice_artificial");
                        else
                            element.put("type", "normal");
                        element.put("count_of_clicks", 0);
                        matrix[k - 1][random_column] = element;


                        if (k != 1) {
                            if (point >= 400)
                                game_ct = create_game_count_down_timer(1000, 1000);
                            else if (point >= 300)
                                game_ct = create_game_count_down_timer(2000, 1000);
                            else if (point >= 200)
                                game_ct = create_game_count_down_timer(3000, 1000);
                            else if (point >= 100)
                                game_ct = create_game_count_down_timer(4000, 1000);
                            else
                                game_ct = create_game_count_down_timer(5000, 1000);

                        }

                        if (k == 1) {
                            Toast.makeText(MainActivity.this, "Oyun Bitti", Toast.LENGTH_LONG).show();

                            for (k = 0; k < 10; k++) {
                                for (c = 0; c < 8; c++)
                                    imageViews[k][c].setEnabled(false);
                            }
                            //puan veritabanına kaydedilecek

                            App app=new App(new AppConfiguration.Builder(Appid).build());
                            //baska email girilecek
                            Credentials credentials=Credentials.emailPassword("email@gmail.com","123456");

                            app.loginAsync(credentials, new App.Callback<User>() {
                                @Override
                                public void onResult(App.Result<User> result) {
                                    if(result.isSuccess()){
                                        Log.v("User","Logged In succesfully");
                                        User user= app.currentUser();
                                        mongoClient=user.getMongoClient("mongodb-atlas");
                                        mongoDatabase=mongoClient.getDatabase("DemoProje");
                                        MongoCollection<Document> mongoCollection=mongoDatabase.getCollection("puanlar");

                                        String txt_p=txt_point.getText().toString();
                                        int s= Integer.valueOf(txt_p);

                                        mongoCollection.insertOne(new Document("userid",user.getId()).append("puan",s)).getAsync(result1 -> {
                                            if(result.isSuccess()){
                                                Log.v("Data","Data inserted succesfully");
                                            }
                                            else {
                                                Log.v("Data","Error"+result.getError().toString());
                                            }
                                        });

                                    }
                                    else
                                    {
                                        Log.v("User","Failed to Login");
                                    }
                                }
                            });


                        }

                        ct2.cancel();
                    }

                    k++;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();

        return ct2;
    }

    public CountDownTimer create_game_count_down_timer(int finish_time,int tick_time){

        ct3=new CountDownTimer(finish_time,tick_time){

            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (finish_time == 1000)
                    move_ct=create_move_count_down_timer(1500,150);
                else if (finish_time == 2000)
                    move_ct=create_move_count_down_timer(2000,200);
                else if (finish_time == 3000)
                    move_ct=create_move_count_down_timer(2500,250);
                else if (finish_time == 4000)
                    move_ct=create_move_count_down_timer(3000,300);
                else
                    move_ct=create_move_count_down_timer(4000,400);

            }
        };

        ct3.start();
        return ct3;
    }
    public CountDownTimer create_wrong_count_down_timer(int finish_time,int tick_time){

        ct4=new CountDownTimer(finish_time,tick_time) {
            int k=0,m,c;
            @Override

            public void onTick(long l) {

                if(k==0){
                    for (m = 0; m < 8; m++) {
                        if(imageViews[k][m].getTag().toString().equals("not_black")) {
                            wait2=Boolean.TRUE;
                            ct4.cancel();
                            break;
                        }
                    }
                }

                if(wait2!=Boolean.FALSE) {
                    for (int e = 0; e < 8; e++) {
                        imageViews[0][e].setTag("black");
                    }

                    wait2=Boolean.FALSE;
                    new CountDownTimer(500, 500) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            wrong_ct = create_wrong_count_down_timer(finish_time, tick_time);
                        }
                    }.start();
                }

                else {
                    for (m = 0; m < 8; m++) {
                        if (matrix[k][m] == null) {
                            if (k != 0) {
                                imageViews[k - 1][m].setImageResource(R.color.black);
                                imageViews[k - 1][m].setTag("black");
                            }


                            //matris elemanına hangi harf geleceği belirleniyor(harfin alfabedeki sırası).

                            if (k == 0) {
                                if (count_of_wowels < count_of_consonants) {
                                    random_number2 = rand.nextInt(8);
                                    count_of_wowels++;
                                } else {
                                    random_number2 = rand.nextInt(21) + 8;
                                    count_of_consonants++;
                                }

                                temporary_index_array[m] = random_number2;
                            }


                            imageViews[k][m].setImageResource((Integer) alphabet_unordered.get(temporary_index_array[m]).get("image_x")); // alphabet in temporary_index_array[m] indexindeki resmi
                            imageViews[k][m].setTag("not_black");
                        } else {

                            if (matrix[k - 1][m] == null) {

                                ice_check_array2 = new int[]{k - 1, m - 1, k - 1, m + 1, k, m, k - 2, m};

                                for (c = 0; c < 5; c += 2) {
                                    if (ice_check_array2[c] >= 0 && ice_check_array2[c] < 10 && ice_check_array2[c + 1] >= 0 && ice_check_array2[c + 1] < 8) {
                                        if (matrix[ice_check_array2[c]][ice_check_array2[c + 1]] != null) {
                                            if (Objects.requireNonNull(matrix[ice_check_array2[c]][ice_check_array2[c + 1]].get("type")).toString().equals("ice_original")) {
                                                ice_artificial2 = Boolean.TRUE;
                                                imageViews[k - 1][m].setImageResource((Integer) alphabet_unordered.get(temporary_index_array[m]).get("image_ice"));
                                                imageViews[k - 1][m].setTag("not_black");
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (c == 6) {
                                    ice_artificial2 = Boolean.FALSE;
                                    imageViews[k - 1][m].setImageResource((Integer) alphabet_unordered.get(temporary_index_array[m]).get("image_unselected"));
                                    imageViews[k - 1][m].setTag("not_black");
                                }

                                element2 = new LinkedHashMap<>();
                                element2.put("index_in_alphabet", temporary_index_array[m]);
                                element2.put("index_in_word", null);
                                if (ice_artificial2)
                                    element2.put("type", "ice_artificial");
                                else
                                    element2.put("type", "normal");
                                element2.put("count_of_clicks", 0);
                                matrix[k - 1][m] = element2;
                            }

                            if (k == 1) {
                                //puan veritabanına kaydedilecek
                                ct4.cancel();
                                Toast.makeText(MainActivity.this, "Oyun Bitti", Toast.LENGTH_LONG).show();

                                for (k = 0; k < 10; k++) {
                                    for (m = 0; m < 8; m++)
                                        imageViews[k][m].setEnabled(false);
                                }
                                move_ct.cancel();
                                game_ct.cancel();

                                App app=new App(new AppConfiguration.Builder(Appid).build());
                                //baska email girilecek
                                Credentials credentials=Credentials.emailPassword("email@gmail.com","123456");

                                app.loginAsync(credentials, new App.Callback<User>() {
                                    @Override
                                    public void onResult(App.Result<User> result) {
                                        if (result.isSuccess()) {
                                            Log.v("User", "Logged In succesfully");
                                            User user = app.currentUser();
                                            mongoClient = user.getMongoClient("mongodb-atlas");
                                            mongoDatabase = mongoClient.getDatabase("DemoProje");
                                            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("puanlar");

                                            String txt_p=txt_point.getText().toString();
                                            int s= Integer.valueOf(txt_p);

                                            mongoCollection.insertOne(new Document("userid", user.getId()).append("puan",s)).getAsync(result1 -> {
                                                if (result.isSuccess()) {
                                                    Log.v("Data", "Data inserted succesfully");
                                                } else {
                                                    Log.v("Data", "Error" + result.getError().toString());
                                                }
                                            });

                                        } else {
                                            Log.v("User", "Failed to Login");
                                        }
                                    }
                                });
                                break;
                            }
                        }
                        ice_artificial2 = Boolean.FALSE;
                    }

                    k++;
                }
            }


            @Override
            public void onFinish() {

            }
        };

        ct4.start();
        return ct4;
    }


    public void fill_in_the_empty_space(int b,int c){
        int d=0;
        imageViews[b][c].setImageResource(R.color.black);
        imageViews[b][c].setTag("black");
        matrix[b][c] = null;

        if (matrix[b - 1][c] != null) {
            imageViews[b][c].setImageDrawable(imageViews[b - 1][c].getDrawable());
            imageViews[b][c].setTag("not_black");
            matrix[b][c] = matrix[b - 1][c];
            matrix[b - 1][c] = null;
            imageViews[b - 1][c].setImageResource(R.color.black);
            imageViews[b][c].setTag("black");
            fill_in_the_empty_space(b-1,c);

            if(matrix[b][c]!=null) {
                if (matrix[b][c].get("index_in_word") != null)
                    fill_in_the_empty_space(b, c);
            }
        }

        else{
            if(c>=4) {
                for (d = 1; c - d >= 0; d++) {
                    if (matrix[b - 1][c - d] != null) {
                        imageViews[b][c].setImageDrawable(imageViews[b - 1][c - d].getDrawable());
                        imageViews[b][c].setTag("not_black");
                        matrix[b][c] = matrix[b - 1][c - d];
                        matrix[b - 1][c - d] = null;
                        imageViews[b - 1][c - d].setImageResource(R.color.black);
                        imageViews[b][c].setTag("black");
                        fill_in_the_empty_space(b - 1, c - d);

                        if(matrix[b][c]!=null) {
                            if (matrix[b][c].get("index_in_word") != null)
                                fill_in_the_empty_space(b, c);
                        }

                    }
                    else if (c + d <= 7){
                        if (matrix[b - 1][c + d] != null) {
                            imageViews[b][c].setImageDrawable(imageViews[b - 1][c + d].getDrawable());
                            imageViews[b][c].setTag("not_black");
                            matrix[b][c] = matrix[b - 1][c + d];
                            matrix[b - 1][c + d] = null;
                            imageViews[b - 1][c + d].setImageResource(R.color.black);
                            imageViews[b][c].setTag("black");
                            fill_in_the_empty_space(b - 1, c + d);

                            if(matrix[b][c]!=null) {
                                if (matrix[b][c].get("index_in_word") != null)
                                    fill_in_the_empty_space(b, c);
                            }

                        }
                    }

                }
            }


            else{
                for (d = 1; c + d <= 7; d++) {
                    if (matrix[b - 1][c + d] != null) {
                        imageViews[b][c].setImageDrawable(imageViews[b - 1][c + d].getDrawable());
                        imageViews[b][c].setTag("not_black");
                        matrix[b][c] = matrix[b - 1][c + d];
                        matrix[b - 1][c + d] = null;
                        imageViews[b - 1][c + d].setImageResource(R.color.black);
                        imageViews[b][c].setTag("black");
                        fill_in_the_empty_space(b - 1, c + d);

                        if(matrix[b][c]!=null) {
                            if (matrix[b][c].get("index_in_word") != null)
                                fill_in_the_empty_space(b, c);
                        }

                    }
                    else if (c - d >= 0){
                        if (matrix[b - 1][c - d] != null) {
                            imageViews[b][c].setImageDrawable(imageViews[b - 1][c - d].getDrawable());
                            imageViews[b][c].setTag("not_black");
                            matrix[b][c] = matrix[b - 1][c - d];
                            matrix[b - 1][c - d] = null;
                            imageViews[b - 1][c - d].setImageResource(R.color.black);
                            imageViews[b][c].setTag("black");
                            fill_in_the_empty_space(b - 1, c - d);

                            if(matrix[b][c]!=null) {
                                if (matrix[b][c].get("index_in_word") != null)
                                    fill_in_the_empty_space(b, c);
                            }

                        }
                    }
                }
            }


        }




    }


    public void fill_in_the_empty_space2(int b,int c){
        int f=0,g=0;

        //Orijinal buzsa etrafındaki yapaz buzlar normal olacak

        //ice_check_array3 = new int[]{b, c - 1, b, c + 1, b+1, c, b-1, c};
        ice_check_array3 = new int[]{0, -1, 0, 1, 1, 0, -1, 0};

        if (matrix[b][c].get("type").toString().equals("ice_original")==Boolean.TRUE) {
            for (f = 0; f < 5; f += 2) {
                if (b+ice_check_array3[f] >= 0 && b+ice_check_array3[f] < 10 && c+ice_check_array3[f + 1] >= 0 && c+ice_check_array3[f + 1] < 8) {
                    if (matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]] != null) {
                        if (Objects.requireNonNull(matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].get("type")).toString().equals("ice_artificial")) {

                            for(g = 0; g < 7; g += 2){
                                if (b+ice_check_array3[f]+ice_check_array3[g] >= 0 && b+ice_check_array3[f]+ice_check_array3[g] < 10 && c+ice_check_array3[f + 1]+ice_check_array3[g + 1] >= 0 && c+ice_check_array3[f + 1]+ice_check_array3[g + 1] < 8) {
                                    if(ice_check_array3[f]+ice_check_array3[g]!=0 && ice_check_array3[f + 1]+ice_check_array3[g + 1]!=0) {
                                        if (matrix[b + ice_check_array3[f] + ice_check_array3[g]][c + ice_check_array3[f + 1] + ice_check_array3[g + 1]] != null) {
                                            if (Objects.requireNonNull(matrix[b + ice_check_array3[f] + ice_check_array3[g]][c + ice_check_array3[f + 1] + ice_check_array3[g + 1]].get("type")).toString().equals("ice_original")) {
                                                break;
                                            }
                                        }
                                    }

                                }
                            }

                            if(g==8){
                                imageViews[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].get("index_in_alphabet")).get("image_unselected"));
                                imageViews[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].setTag("not_black");
                                matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].put("type","normal");
                            }



                        }
                    }
                }
            }
        }


        imageViews[b][c].setImageResource(R.color.black);
        imageViews[b][c].setTag("black");
        matrix[b][c] = null;

        if (matrix[b - 1][c] != null) {
            imageViews[b][c].setImageDrawable(imageViews[b - 1][c].getDrawable());
            imageViews[b][c].setTag("not_black");
            matrix[b][c] = matrix[b - 1][c];

            // eğer orijinal buzsa etrafındakiler yapay buz olacak
            //etrafında orijinal buz varsa kendisi yapay buz olacak
            //yapay buzsa etrafında orijinal yoksa normal olacak

            if (matrix[b][c].get("type").toString().equals("normal")==Boolean.TRUE) {
                for (f = 0; f < 5; f += 2) {
                    if (b+ice_check_array3[f] >= 0 && b+ice_check_array3[f] < 10 && c+ice_check_array3[f + 1] >= 0 && c+ice_check_array3[f + 1] < 8) {
                        if (matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]] != null) {
                            if (Objects.requireNonNull(matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].get("type")).toString().equals("ice_original")==Boolean.TRUE) {
                                imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice"));
                                matrix[b][c].put("type","ice_artificial");
                                imageViews[b][c].setTag("not_black");
                                break;
                            }
                        }
                    }
                }
            }
            else if(matrix[b][c].get("type").toString().equals("ice_original")==Boolean.TRUE){

                for (f = 0; f < 5; f += 2) {
                    if (b+ice_check_array3[f] >= 0 && b+ice_check_array3[f] < 10 && c+ice_check_array3[f + 1] >= 0 && c+ice_check_array3[f + 1] < 8) {
                        if (matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]] != null) {
                            if (Objects.requireNonNull(matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].get("type")).toString().equals("ice_original") == Boolean.FALSE) {
                                matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].put("type", "ice_artificial");
                                imageViews[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].get("index_in_alphabet")).get("image_ice"));
                                imageViews[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].setTag("not_black");
                            }
                        }
                    }
                }

            }


            else if(matrix[b][c].get("type").toString().equals("ice_artificial")==Boolean.TRUE){
                for(f=0;f<5;f+=2){
                    if (b+ice_check_array3[f] >= 0 && b+ice_check_array3[f] < 10 && c+ice_check_array3[f + 1] >= 0 && c+ice_check_array3[f + 1] < 8) {
                        if (matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]] != null) {
                            if (Objects.requireNonNull(matrix[b+ice_check_array3[f]][c+ice_check_array3[f + 1]].get("type")).toString().equals("ice_original")==Boolean.TRUE) {
                                break;
                            }
                        }
                    }
                }

                if(f==6){
                    imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_unselected"));
                    imageViews[b][c].setTag("not_black");
                    matrix[b][c].put("type","normal");
                }

            }


            //matrix[b - 1][c] = null;
            imageViews[b - 1][c].setImageResource(R.color.black);
            imageViews[b - 1][c].setTag("black");
            fill_in_the_empty_space2(b-1,c);

            if(matrix[b][c]!=null) {
                if (matrix[b][c].get("index_in_word") != null)
                    fill_in_the_empty_space2(b, c);
            }

        }



    }



    //####App id vb.####
    String Appid="application-0-cudew";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    //##############

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale trlocale= Locale.forLanguageTag("tr-TR");

        Realm.init(this);
        App app=new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials=Credentials.emailPassword("duvarcizey@gmail.com","123456");

        int a,i,j;

        for (i=0;i<10;i++){
            for (j=0;j<8;j++) {
                a=getResources().getIdentifier("imageView"+Integer.toString(i)+Integer.toString(j), "id", getPackageName());
                imageViews[i][j]=findViewById(a);
                imageViews[i][j].setImageResource(R.color.black);
                imageViews[i][j].setTag("black");
                imageViews[i][j].setEnabled(false);
            }
        }

        txt_chosen_word=findViewById(R.id.txt_word);
        // puan
        txt_point=findViewById(R.id.txt_point);
        btn_confirm=findViewById(R.id.btn_confirm);
        btn_remove=findViewById(R.id.btn_remove);
        // puan listesi
        btn_show_activity_point=findViewById(R.id.btn_show_activity_point);

        Alphabet alphabet=new Alphabet();
        alphabet_unordered=alphabet.getAlphabet_unordered();

        Intent previous_intent=getIntent();
        mode=previous_intent.getStringExtra("mode");

        start_ct=create_start_count_down_timer(5000,500);

        final int[] counter_of_character = {0};
        for (i=0;i<10;i++) {
            for (j=0;j<8;j++){
                int finalI = i;
                int finalJ = j;
                imageViews[i][j].setOnClickListener(new View.OnClickListener() {
                    int b,c;
                    @Override

                    public void onClick(View view) {

                        if(matrix[finalI][finalJ]!=null) {
                            matrix[finalI][finalJ].put("count_of_clicks", Integer.parseInt(matrix[finalI][finalJ].get("count_of_clicks").toString()) + 1);


                            //tıklanma sayısını kontrol et,iki olmuşsa silmek gerek
                            if (((Integer) matrix[finalI][finalJ].get("count_of_clicks") == 2) || (matrix[finalI][finalJ].get("type").toString().equals("normal")!=Boolean.TRUE && (Integer) matrix[finalI][finalJ].get("count_of_clicks") == 4)) {

                                if(matrix[finalI][finalJ].get("type").toString().equals("normal"))
                                    imageViews[finalI][finalJ].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[finalI][finalJ].get("index_in_alphabet")).get("image_unselected"));

                                else if(matrix[finalI][finalJ].get("type").toString().equals("ice_artificial"))
                                    imageViews[finalI][finalJ].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[finalI][finalJ].get("index_in_alphabet")).get("image_ice"));

                                else
                                    imageViews[finalI][finalJ].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[finalI][finalJ].get("index_in_alphabet")).get("image_ice2"));

                                imageViews[finalI][finalJ].setTag("not_black");

                                if((Integer) matrix[finalI][finalJ].get("count_of_clicks") == 2)
                                    matrix[finalI][finalJ].put("count_of_clicks", 0);

                                else if((Integer) matrix[finalI][finalJ].get("count_of_clicks") == 4)
                                    matrix[finalI][finalJ].put("count_of_clicks", 2);


                                txt_chosen_word.setText(txt_chosen_word.getText().toString().substring(0, (Integer) matrix[finalI][finalJ].get("index_in_word")) + txt_chosen_word.getText().toString().substring((Integer) matrix[finalI][finalJ].get("index_in_word") + 1));
                                chosen_word_index.remove((Integer) matrix[finalI][finalJ].get("index_in_word"));

                                for (b = 0; b < 10; b++) {
                                    for (c = 0; c < 8; c++) {
                                        if (matrix[b][c] != null) {
                                            if (matrix[b][c].get("index_in_word") != null) {
                                                if ((Integer) matrix[b][c].get("index_in_word") > (Integer) matrix[finalI][finalJ].get("index_in_word"))
                                                    matrix[b][c].put("index_in_word", (Integer) matrix[b][c].get("index_in_word") - 1);
                                            }
                                        }
                                    }
                                }
                                matrix[finalI][finalJ].put("index_in_word", null);
                                counter_of_character[0]--;
                            }

                            else if(((Integer) matrix[finalI][finalJ].get("count_of_clicks") == 1) || (matrix[finalI][finalJ].get("type").toString().equals("normal")!=Boolean.TRUE && (Integer) matrix[finalI][finalJ].get("count_of_clicks") == 3 )){
                                imageViews[finalI][finalJ].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[finalI][finalJ].get("index_in_alphabet")).get("image_selected"));
                                imageViews[finalI][finalJ].setTag("not_black");
                                matrix[finalI][finalJ].put("index_in_word", counter_of_character[0]);

                                counter_of_character[0]++;
                                txt_chosen_word.setText(txt_chosen_word.getText().toString() + alphabet_unordered.get(Integer.parseInt(matrix[finalI][finalJ].get("index_in_alphabet").toString())).get("character").toString());
                                chosen_word_index.add(Integer.parseInt(matrix[finalI][finalJ].get("index_in_alphabet").toString()));
                            }


                        }
                    }
                });

            }
        }


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt=txt_chosen_word.getText().toString().toLowerCase(trlocale);
                Log.v("küçük: ",txt);
                if (txt.length() >= 3){
                    String first=String.valueOf(txt.charAt(0));
                    Log.v("ilk harf",first);

                    app.loginAsync(credentials, new App.Callback<User>() {
                        @Override
                        public void onResult(App.Result<User> result) {
                            if(result.isSuccess()){
                                Log.v("User","Logged In succesfully");
                                User user= app.currentUser();
                                mongoClient=user.getMongoClient("mongodb-atlas");
                                mongoDatabase=mongoClient.getDatabase("DemoProje");
                                MongoCollection<Document> mongoCollection=mongoDatabase.getCollection(first);


                                Document queryFilter=new Document().append("kelime",txt);
                                mongoCollection.findOne(queryFilter).getAsync(task -> {
                                    if (task.isSuccess()) {
                                        Log.v("EXAMPLE", "successfully found a document: " +task.get());
                                        if(task.get()!=null){
                                            //kelime bulundu
                                            Log.v("Data:","Kelime bulundu");

                                            MongoCollection<Document> mongoCollection1=mongoDatabase.getCollection("kayitlar");


                                            for (int e = chosen_word_index.size() - 1; e >= 0; e--) {
                                                point += (Integer) alphabet_unordered.get(chosen_word_index.get(e)).get("point_of_character");
                                                if (chosen_word_index.get(e) < 8)
                                                    count_of_wowels--;
                                                else
                                                    count_of_consonants--;

                                            }

                                            txt_point.setText(Integer.toString(point));
                                            txt_chosen_word.setText("");
                                            counter_of_character[0] = 0;

                                            int b, c;
                                            // harfler yok olacak, bosluklar doldurulacak
                                            for (b = 9; b >= 0; b--) {
                                                for (c = 7; c >= 0; c--) {
                                                    if (matrix[b][c] != null) {
                                                        if (matrix[b][c].get("index_in_word") != null) {

                                                            // buz harflerin seçldiği ilk kelime ise harf yok olmuyor
                                                            if (matrix[b][c].get("type").toString().equals("normal") != Boolean.TRUE && (Integer) matrix[b][c].get("count_of_clicks") == 1) {
                                                                if (matrix[b][c].get("type").toString().equals("ice_artificial"))
                                                                    imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice"));
                                                                else
                                                                    imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice2"));

                                                                if (chosen_word_index.get((Integer) matrix[b][c].get("index_in_word")) < 8)
                                                                    count_of_wowels++;
                                                                else
                                                                    count_of_consonants++;

                                                                imageViews[b][c].setTag("not_black");
                                                                matrix[b][c].put("index_in_word", null);
                                                                matrix[b][c].put("count_of_clicks", 2);
                                                            }


                                                            //secili olan harfler yok olacak,bosluklar dolacak
                                                            //benzer şekilde diğer ihtimaller(üstünün boş olması, üstünün de seçili harf olması) vs. düşünülerek
                                                            //açılan boşluklar döngü şeklinde dolacak şekilde davam edilecek

                                                            else {
                                                                if (mode.equals("easy"))
                                                                    fill_in_the_empty_space(b, c);

                                                                else
                                                                    fill_in_the_empty_space2(b, c);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            chosen_word_index.clear();

                                        }
                                        else{
                                            Log.v("Data","Kelime bulunamadı");
                                            int b,c;
                                            count_of_wrong_word++;

                                            txt_chosen_word.setText("");
                                            chosen_word_index.clear();
                                            counter_of_character[0] = 0;

                                            //harflerin tıklama sayısı,index_in_word vs. ayarlanıyor
                                            for (b = 0; b < 10; b++) {
                                                for (c = 0; c < 8; c++) {
                                                    if (matrix[b][c] != null) {
                                                        if (matrix[b][c].get("index_in_word") != null) {
                                                            matrix[b][c].put("index_in_word", null);
                                                            matrix[b][c].put("count_of_clicks", 0);

                                                            if (matrix[b][c].get("type").toString().equals("normal"))
                                                                imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_unselected"));
                                                            else if (matrix[b][c].get("type").toString().equals("ice_artificial"))
                                                                imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice"));
                                                            else
                                                                imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice2"));

                                                            imageViews[b][c].setTag("not_black");
                                                        }
                                                    }
                                                }
                                            }


                                            if (count_of_wrong_word == 3) {
                                                // bir satır harf düsüyor

                                                if (point >= 400)
                                                    wrong_ct = create_wrong_count_down_timer(1500, 150);
                                                else if (point >= 300)
                                                    wrong_ct = create_wrong_count_down_timer(2000, 200);
                                                else if (point >= 200)
                                                    wrong_ct = create_wrong_count_down_timer(2500, 250);
                                                else if (point >= 100)
                                                    wrong_ct = create_wrong_count_down_timer(3000, 300);
                                                else
                                                    wrong_ct = create_wrong_count_down_timer(4000, 400);


                                                count_of_wrong_word = 0;
                                            }

                                        }
                                    } else {
                                        Log.e("EXAMPLE", "failed to find document with: ", task.getError());
                                    }
                                });


                            }
                            else
                            {
                                Log.v("User","Failed to Login");
                            }
                        }
                    });


                }
            }


        });


        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                int b,c;

                if(txt_chosen_word.getText().toString().equals("")!=Boolean.TRUE) {
                    txt_chosen_word.setText("");
                    chosen_word_index.clear();
                    counter_of_character[0] = 0;

                    for (b = 0; b < 10; b++) {
                        for (c = 0; c < 8; c++) {
                            if (matrix[b][c] != null) {
                                if (matrix[b][c].get("index_in_word") != null) {
                                    matrix[b][c].put("index_in_word", null);
                                    matrix[b][c].put("count_of_clicks", 0);
                                    if (matrix[b][c].get("type").toString().equals("normal"))
                                        imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_unselected"));
                                    else if (matrix[b][c].get("type").toString().equals("ice_artificial"))
                                        imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice"));
                                    else
                                        imageViews[b][c].setImageResource((Integer) alphabet_unordered.get((Integer) matrix[b][c].get("index_in_alphabet")).get("image_ice2"));

                                    imageViews[b][c].setTag("not_black");
                                }
                            }
                        }
                    }

                }
            }
        });

        btn_show_activity_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_Point=new Intent(MainActivity.this,Point.class);
                startActivity(i_Point);
            }
        });






    }

    @Override
    public void onBackPressed() {
        Intent i_startActivity=new Intent(MainActivity.this,StartActivity.class);
        finish();
        if(start_ct!=null)
            start_ct.cancel();
        if(move_ct!=null)
            move_ct.cancel();
        if(game_ct!=null)
            game_ct.cancel();
        if(ct!=null)
            ct.cancel();
        if(ct2!=null)
            ct2.cancel();
        if(ct3!=null)
            ct3.cancel();
        if(ct4!=null)
            ct4.cancel();

        startActivity(i_startActivity);
    }
}