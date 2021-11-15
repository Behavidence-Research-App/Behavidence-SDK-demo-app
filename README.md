# TestingBehavidenceSDK
 
This is an example of how to implement a simple app using the Behavidence SDK.
This app calculates the similarity scores, receives the research questions using Behavidence SDK.

# steps:

## First of all you need to download the BehavidenceSDK and add implementation of it in the build.gradle so you will need to add that line:

implementation files('/your Directory/BehavidenceSdk.aar')

# 1)
ask for user permession "PACKAGE_USAGE_STATS"
# 2) 
initialize the SDK by calling BehavidenceSDK.init - passing the API secret provided by Behavidence team. 

BehavidenceSDK.init(this,"api secret");
# 3)
declare instance of AuthClient:

authClient = AuthClient.getInstance(this);

## if it is the first time then signup anonymously by calling authClient.anonymousSignUp().execute then inside the success callback call autoUploadInit to upload the phone data automatically :

            authClient.anonymousSignUp().executeAsync(new ApiResponse<AnonymousAuth>() {
                @Override
                public void success(@Nullable AnonymousAuth anonymousAuth) {
                    if (anonymousAuth != null)
                        BehavidenceSDK.autoUploadInit(MainActivity.this, anonymousAuth);
                }
                @Override
                public void onFailure(@NonNull RuntimeException e) {

                }
            });
            
 AND THATS IT !
 
 
 # Getting similarity scores 
 
 To get the similarity you need to declare instance of ScoresClient and then call getSimilarityScores function :
 
        ScoresClient scoresClient = ScoresClient.getInstance(this);
        scoresClient.getSimilarityScores().executeAsync(new ApiResponse<List<SimilarityScore>>() {
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
        researchCodeClient.getResearchQuestions("ResearchCode").executeAsync(new ApiResponse<ResearchQuestions>() {
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
 
 
