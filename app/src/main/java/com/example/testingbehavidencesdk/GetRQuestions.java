package com.example.testingbehavidencesdk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import behavidence.android.sdk.Networks.Responses.ApiResponse;
import behavidence.android.sdk.SdkFunctions.Researches.Models.ResearchQuestion;
import behavidence.android.sdk.SdkFunctions.Researches.Models.ResearchQuestions;
import behavidence.android.sdk.SdkFunctions.Researches.ResearchCodeClient;

public class GetRQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        ResearchCodeClient researchCodeClient = ResearchCodeClient.getInstance(this);
        researchCodeClient.getResearchQuestions("GAD212").executeAsync(new ApiResponse<ResearchQuestions>() {
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
    }
}