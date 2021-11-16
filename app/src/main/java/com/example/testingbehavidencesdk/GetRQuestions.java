package com.example.testingbehavidencesdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import behavidence.android.sdk.Networks.Responses.ApiResponse;
import behavidence.android.sdk.SdkFunctions.Researches.Models.ResearchQuestion;
import behavidence.android.sdk.SdkFunctions.Researches.Models.ResearchQuestions;
import behavidence.android.sdk.SdkFunctions.Researches.ResearchCodeClient;

public class GetRQuestions extends AppCompatActivity {
    String txt = "";
    TextView results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        results = (TextView) findViewById(R.id.textview_second);
        ResearchCodeClient researchCodeClient = ResearchCodeClient.getInstance(this);
        researchCodeClient.getResearchQuestions("GAD212").executeAsync(new ApiResponse<ResearchQuestions>() {
            @Override
            public void success(@Nullable ResearchQuestions researchQuestions) {
                List<ResearchQuestion> questions = researchQuestions.getResearchQuestions();



                for(int i=0; i < questions.size(); i++) {
                    ResearchQuestion RQ = questions.get(i);
                    String question = "" + RQ.getQuestion();
                    String options = "" + RQ.getOptions().length+"";
                    txt += "Question "+i+" \n" + question + "\n";
                    txt += "Options " + options + "\n\n";


                }
                GetRQuestions.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("UI thread", "I am the UI thread");
                        results.setText(txt);
                    }
                });
            }

            @Override
            public void onFailure(@NonNull RuntimeException e) {

            }
        });
    }
}