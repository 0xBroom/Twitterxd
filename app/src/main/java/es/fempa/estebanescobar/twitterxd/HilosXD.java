package es.fempa.estebanescobar.twitterxd;

import android.content.Intent;
import android.net.Uri;

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

public class HilosXD {
    private Login l;
    private OAuth10aService service;
    private OAuth1RequestToken requestToken;
    private String authUrl;

    public HilosXD(Login l){
        this.l = l;
    }

    public void getauth(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                service = new ServiceBuilder("cyFGlnXAnIP5J2fQt9BaX8d4U")
                        .apiSecret("fwsqS43pRiju4OQPM4ewOJf6vZrIdr5MYngerhKxGdICxmFTjX")
                        .build(TwitterApi.instance());
                try {
                    requestToken = service.getRequestToken();
                    String authUrl = service.getAuthorizationUrl(requestToken);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
                    l.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void getData(Intent intent){
        final Intent i = intent;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri uri = i.getData();
                    if(uri != null && uri.toString().startsWith(l.CALLBACK_URL)){
                        final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, uri.getQueryParameter("oauth_verifier"));
                        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
                        service.signRequest(accessToken, request);
                        final Response response = service.execute(request);
                        l.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    l.testTV.setText(response.getBody());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
       t.start();
    }
}
