package com.example.android.flashcards;

public class Words {

    private String mWord;
    private String mDefinition;
    private String mSetId;
    private String mId;

    public Words(String vId, String vSetId, String vWords, String vDefinition){
        mWord = vWords;
        mDefinition = vDefinition;
        mSetId = vSetId;
        mId = vId;
    }

    public String getSetId(){
        return mSetId;
    }

    public String getWord(){
        return mWord;
    }

    public String getDefinition(){
        return mDefinition;
    }

    public String getId(){
        return mId;
    }

    public void editWord(String word, String def){
        mWord = word;
        mDefinition = def;
    }
}
