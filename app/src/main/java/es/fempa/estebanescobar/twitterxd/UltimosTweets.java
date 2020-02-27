package es.fempa.estebanescobar.twitterxd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class UltimosTweets extends ListActivity {

    HilosXD h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserData.getInstance().getJSONtweets();
        h = new HilosXD(this);
        h.fetchTweets();
    }

    public void listTweets(){
        ArrayList<Tweet> tweetList = new ArrayList<>();
        JSONArray json = UserData.getInstance().getJSONtweets();
        for (int i = 0;i<json.length(); i++){
            tweetList.add(new Tweet());
            try {
                tweetList.get(tweetList.size()-1).setImage(json.getJSONObject(i).getJSONObject("user").getString("profile_image_url_https"));
                tweetList.get(tweetList.size()-1).setTweet(json.getJSONObject(i).getString("text"));
                tweetList.get(tweetList.size()-1).setUser("@"+json.getJSONObject(i).getJSONObject("user").getString("screen_name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        UserData.getInstance().setTweets(tweetList);
        final UltimosTweets u = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdapterList adapter = new AdapterList(u, android.R.layout.simple_list_item_1,UserData.getInstance().getTweets(), u);
                setListAdapter(adapter);
            }
        });

    }


}
