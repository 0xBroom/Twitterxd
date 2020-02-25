package es.fempa.estebanescobar.twitterxd;

import org.json.JSONObject;

public class UserData {

    private JSONObject data;
    private static UserData instance = null;
    private boolean logged;

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
}
