package com.example.testingbehavidencesdk;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;


import androidx.navigation.ui.AppBarConfiguration;

import com.example.testingbehavidencesdk.databinding.ActivityMainBinding;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import behavidence.android.sdk.Auth.AnonymousAuth;
import behavidence.android.sdk.Auth.AuthClient;
import behavidence.android.sdk.BehavidenceSDK;
import behavidence.android.sdk.Networks.Responses.ApiCallBack;
import behavidence.android.sdk.SdkFunctions.Events.EventsClient;
import behavidence.android.sdk.SdkFunctions.Journals.JournalClient;
import behavidence.android.sdk.SdkFunctions.Researches.ResearchCodeClient;
import behavidence.android.sdk.SdkFunctions.Researches.ResearchQuestion;
import behavidence.android.sdk.SdkFunctions.Researches.ResearchQuestions;
import behavidence.android.sdk.SdkFunctions.SimilarityScores.ScoresClient;
import behavidence.android.sdk.SdkFunctions.SimilarityScores.SimilarityScore;


public class MainActivity extends AppCompatActivity {

    String SHARED_PREFS = "sharedPrefs";
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    AuthClient authClient;
    boolean firstTime = false;
    Button getSimilarity;
    Button getQuestions;
    TextView permissionStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSimilarity = (Button) findViewById(R.id.getSimilarity);
        getQuestions = (Button) findViewById(R.id.getRQ);
        permissionStatus = (TextView) findViewById(R.id.tvPermission);


        if(!BehavidenceSDK.isAccessPermissionEnabled(this)){
            Toast.makeText(MainActivity.this, "you need to grant us Usage data access permission ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            getSimilarity.setEnabled(false);
            getQuestions.setEnabled(false);

            return;
        }

        permissionStatus.setText("Permission Granted");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        BehavidenceSDK.refreshState(this).executeAsync(new ApiCallBack<Void>() {
            @Override
            public void success(@Nullable Void unused) {

            }

            @Override
            public void onFailure(@NonNull RuntimeException e) {

            }
        });






        BehavidenceSDK.init(this,"Put your api key that provided by Behavidence");
        authClient = AuthClient.getInstance(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getString("FirstTime","yes").equals("yes")) {
            firstTime = true;
            editor.putString("FirstTime","no");
            editor.commit();

            authClient.anonymousSignUp().executeAsync(new ApiCallBack<AnonymousAuth>() {
                @Override
                public void success(@Nullable AnonymousAuth anonymousAuth) {
                    if (anonymousAuth != null)
                        BehavidenceSDK.autoInitUpload(MainActivity.this, anonymousAuth);
                }

                @Override
                public void onFailure(@NonNull RuntimeException e) {

                }
            });
        }

        AnonymousAuth anonymousAuth = (AnonymousAuth) (authClient.getLocalAnonymousAuth());
        /*

         */


        ResearchCodeClient researchCodeClient = ResearchCodeClient.getInstance(this);
        researchCodeClient.getResearchQuestions("GAD212").executeAsync(new ApiCallBack<ResearchQuestions>() {
            @Override
            public void success(@Nullable ResearchQuestions researchQuestions) {
                List<ResearchQuestion> questions = researchQuestions.getResearchQuestions();

                for(int i = 0; i < questions.size(); i++) {
                    ResearchQuestion RQ = questions.get(i);

                }
            }

            @Override
            public void onFailure(@NonNull RuntimeException e) {

            }
        });

        JournalClient journalClient = JournalClient.getInstance(this);
        //journalClient.saveJournal(new Journal("I fell good", Calendar.getInstance().getTimeInMillis()));
        //Journal journal = journalClient.loadJournals().get(0);
        journalClient.uploadJournals().executeAsync(new ApiCallBack<Void>() {
            @Override
            public void success(@Nullable Void unused) {

            }

            @Override
            public void onFailure(@NonNull RuntimeException e) {


            }
        });
        EventsClient eventsClient = EventsClient.getInstance(this);

        getSimilarity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetSimilarity.class);
                startActivity(intent);
            }
        });

        getQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetRQuestions.class);
                startActivity(intent);
            }
        });


        ScoresClient scoresClient = ScoresClient.getInstance(this);
        scoresClient.getSimilarityScores().executeAsync(new ApiCallBack<List<SimilarityScore>>() {
            @Override
            public void success(@Nullable List<SimilarityScore> similarityScores) {
                String txt = "";
                for(int i=0; i < similarityScores.size(); i++) {
                    txt += "day "+(i+1) +"\n";
                    String Anxiety = "" + similarityScores.get(i).getAnxietyScore();
                    String ADHD = "" + similarityScores.get(i).getAdhdScore();
                    String Depression = "" + similarityScores.get(i).getDepressionScore();
                    String day = similarityScores.get(i).getScoresDate();
                    txt += "Anxiety " + Anxiety + "%\n";
                    txt += "ADHD " + ADHD + "%\n";
                    txt += "Depression " + Depression + "%\n";


                }
                //results.setText(txt);
            }


            @Override
            public void onFailure(@NonNull RuntimeException e) {

            }
        });



    }



}