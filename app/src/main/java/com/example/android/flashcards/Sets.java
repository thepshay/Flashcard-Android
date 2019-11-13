package com.example.android.flashcards;

public class Sets {

    private String mName;
    private String mDes;
    private String mId;

    Sets(String vId, String vName, String vDes){
        mId = vId;
        mName = vName;
        mDes = vDes;
    }

    public String getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public String getDes(){
        return mDes;
    }

}
