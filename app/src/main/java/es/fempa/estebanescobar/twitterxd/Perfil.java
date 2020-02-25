package es.fempa.estebanescobar.twitterxd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.net.URL;

public class Perfil extends AppCompatActivity {

    ImageView profilePic;
    TextView at;
    EditText tweetET;
    Button btnLogout;
    Button btnPost;
    HilosXD h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        h = new HilosXD(this);
        profilePic = findViewById(R.id.profilePic);
        at = findViewById(R.id.arroba);
        btnLogout = findViewById(R.id.btnLogout);
        tweetET = findViewById(R.id.tweetET);
        btnPost = findViewById(R.id.btnPost);
        loadInfo();
    }

    private void loadInfo(){
        try {
            at.setText("@"+UserData.getInstance().getData().getString("screen_name"));
            h.getImg(UserData.getInstance().getData().getString("profile_image_url_https"));
            Log.d("json", UserData.getInstance().getData().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickLogout(View v){
        finish();
    }

    public void onClickTweets(View v){
        h.fetchTweets();
        Intent intent = new Intent(Perfil.this, UltimosTweets.class);
        startActivity(intent);
    }

    public void onClickPost(View v){
        if(!tweetET.getText().toString().equals("")){
            h.sendTweet(tweetET.getText().toString());
        }

    }
}
