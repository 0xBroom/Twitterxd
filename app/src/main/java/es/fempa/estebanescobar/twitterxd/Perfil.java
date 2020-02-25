package es.fempa.estebanescobar.twitterxd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.net.URL;

public class Perfil extends AppCompatActivity {

    ImageView profilePic;
    TextView at;
    Button btnLogout;
    HilosXD h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        h = new HilosXD(this);
        profilePic = findViewById(R.id.profilePic);
        at = findViewById(R.id.arroba);
        btnLogout = findViewById(R.id.btnLogout);
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
}
