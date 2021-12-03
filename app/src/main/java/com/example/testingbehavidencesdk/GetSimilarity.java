package com.example.testingbehavidencesdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import behavidence.android.sdk.SdkFunctions.SimilarityScores.ScoresClient;
import behavidence.android.sdk.SdkFunctions.SimilarityScores.SimilarityScore;
import behavidence.android.sdk.Networks.Responses.ApiCallBack;

public class GetSimilarity extends AppCompatActivity {


    TextView results;
    String Myresult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
        results = (TextView) findViewById(R.id.similarityResults);
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
                Myresult = txt;
                GetSimilarity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");
                        results.setText(Myresult);
                    }
                });

            }


            @Override
            public void onFailure(@NonNull RuntimeException e) {

            }
        });
    }
}