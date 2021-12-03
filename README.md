# BehavidenceSDK-DemoAPP
 
This is an example of how to integrate  the Behavidence SDK inside any app. 
This app receives the mental health similarity scores. (It also has the capacity to receive the research questions using Behavidence SDK? ask Girish)
This is a link for the documentation of the Behavidence SDK :
https://docs.google.com/document/d/18sgC18lHhfTTBh13MtIzLEd_UW_CVg-4v12DJX43xTU/edit

# steps:

## First download the BehavidenceSDK and implement it in the build.gradle. To do so add the line:

implementation files('/your Directory/BehavidenceSdk.aar')

# 1)
ask for user permession "PACKAGE_USAGE_STATS"
# 2) 
initialize the SDK by calling BehavidenceSDK.init - passing the API secret provided by Behavidence team ( via email? G/ASMAR?). 

BehavidenceSDK.init(this,"api secret");
# 3)
declare instance of AuthClient:

authClient = AuthClient.getInstance(this);

## for the first signup anonymously by calling authClient.anonymousSignUp().execute then inside the success callback call autoUploadInit to upload the phone data automatically :

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
            
 AND THATS IT!!
 
 
 # Getting similarity scores 
 
 To get the similarity scores you need to declare instance of ScoresClient and then call getSimilarityScores function :
 
        ScoresClient scoresClient = ScoresClient.getInstance(this);
        scoresClient.getSimilarityScores().executeAsync(new ApiCallBack<List<SimilarityScore>>() {
            @Override
            public void success(@Nullable List<SimilarityScore> similarityScores) {
                //there you can use the similarityScores object  
            }
            @Override
            public void onFailure(@NonNull RuntimeException e) {

            }
        });
 
 
 # Getting Research Questions :
 
 to get the research questions you need to declare instance of ResearchCodeClient and then call getResearchQuestions function :
 
        ResearchCodeClient researchCodeClient = ResearchCodeClient.getInstance(this);
        researchCodeClient.getResearchQuestions("ResearchCode").executeAsync(new ApiCallBack<ResearchQuestions>() {
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
 
 

