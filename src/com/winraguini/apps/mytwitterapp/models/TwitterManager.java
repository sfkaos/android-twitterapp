package com.winraguini.apps.mytwitterapp.models;

public class TwitterManager {
    private static TwitterManager mInstance = null;
 
    private String screenName;
    private String profileImageUrl;
    
    
    private TwitterManager(){
    }
 
    public static TwitterManager getInstance(){
        if(mInstance == null)
        {
            mInstance = new TwitterManager();
        }
        return mInstance;
    }
 
    public String getScreenName(){
        return this.screenName;
    }
 
    public void setScreenName(String value){
        screenName = value;
    }
    
    public String getProfileImageUrl(){
        return this.profileImageUrl;
    }
 
    public void setProfileImageUrl(String value){
        profileImageUrl = value;
    }
}