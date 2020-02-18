package es.fempa.estebanescobar.twitterxd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private OAuth10aService service;
    private OAuth1RequestToken requestToken;
    private final String CALLBACK_URL = "twitterxd-oauth-twitter://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View v){
        service = new ServiceBuilder("cyFGlnXAnIP5J2fQt9BaX8d4U")
                .apiSecret("fwsqS43pRiju4OQPM4ewOJf6vZrIdr5MYngerhKxGdICxmFTjX")
                .callback(CALLBACK_URL)
                .build(TwitterApi.instance());

        try {
            requestToken = service.getRequestToken();
            String authUrl = service.getAuthorizationUrl(requestToken);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        try {
            Uri uri = intent.getData();
            if(uri != null && uri.toString().startsWith(CALLBACK_URL)){
                final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, uri.getQueryParameter("oauth_verifier"));
                final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
                service.signRequest(accessToken, request);
                final Response response = service.execute(request);
                System.out.println(response.getBody());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
