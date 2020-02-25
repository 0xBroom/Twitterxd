package es.fempa.estebanescobar.twitterxd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {


    public final String CALLBACK_URL = "twitterxd-oauth-twitter://callback";
    public TextView testTV;
    private HilosXD h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        testTV = findViewById(R.id.testTV);
        h = new HilosXD(this);
    }

    public void onClickLogin(View v){
        h.getauth();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        h.authenticate(intent);
    }
}
