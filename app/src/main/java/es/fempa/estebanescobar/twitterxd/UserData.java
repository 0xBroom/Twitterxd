package es.fempa.estebanescobar.twitterxd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserData {

    private JSONObject data;
    private JSONArray JSONtweets;
    private static UserData instance = null;
    private boolean logged;
    private ArrayList<Tweet> tweets;


    private UserData(){
        data = null;
        logged = false;
    }

    protected static UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }

    protected JSONObject getData() {
        return data;
    }

    protected void setData(JSONObject data) {
        this.data = data;
    }

    protected boolean isLogged() {
        return logged;
    }

    protected void setLogged(boolean logged) {
        this.logged = logged;
    }

    public JSONArray getJSONtweets() {
        return JSONtweets;
    }

    public void setJSONtweets(JSONArray JSONtweets) {
        this.JSONtweets = JSONtweets;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }
}
