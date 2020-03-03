package es.fempa.estebanescobar.twitterxd;
/*
service = new ServiceBuilder("cyFGlnXAnIP5J2fQt9BaX8d4U")
        .apiSecret("fwsqS43pRiju4OQPM4ewOJf6vZrIdr5MYngerhKxGdICxmFTjX")
        .callback("test://")
        .build(TwitterApi.instance());

 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HilosXD {
    private Login l;
    private static OAuth10aService service;
    private static OAuth1RequestToken requestToken;
    private static OAuth1AccessToken accessToken;
    private Perfil p;
    private UltimosTweets u;
    private ImageView iv;

    public HilosXD(Login l){
        this.l = l;
    }
    public HilosXD(Perfil p){this.p = p;}
    public HilosXD(UltimosTweets u){this.u = u;}


    public void getauth(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                service = new ServiceBuilder("api_key") //Creamos servicio
                        .apiSecret("secret_api_key")
                        .callback("test://")
                        .build(TwitterApi.instance());
                try {
                    requestToken = service.getRequestToken(); //Pedimos requestoken
                    String authUrl = service.getAuthorizationUrl(requestToken); //Conseguimos URL
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)); //Cambiamos al intent
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

    public void authenticate(Intent intent){
        final Intent i = intent;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Uri uri = i.getData(); //Sacamos lo que nos devuelve
                    if(uri.getQueryParameter("denied") == null){
                        accessToken = service.getAccessToken(requestToken, uri.getQueryParameter("oauth_verifier")); //Sacamos el access token
                        final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json"); //Verificamos el usuario
                        service.signRequest(accessToken, request); //firmamos el request
                        final Response response = service.execute(request); //ejecutamos
                        Log.d("prueba", "RESPONSE: "+response);
                        if (response.getMessage().equals("OK")){ //
                            try {
                                JSONObject json = new JSONObject(response.getBody());
                                UserData.getInstance().setData(json);
                                UserData.getInstance().setLogged(true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            l.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(l, Perfil.class);
                                    l.startActivity(intent);
                                }
                            });
                        }else{
                            l.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(l, "Error al autenticar cuenta. Porfavor intenta de nuevo", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }else{
                        l.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(l, "Autenticación con twitter denegada", Toast.LENGTH_LONG).show();
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

    public void getImg(String imgUrl, ImageView imageView, String context){
        this.iv = imageView;
        imgUrl = imgUrl.replace("_normal", "");
        final String url = imgUrl;
        Thread t;
        if(context.equals("perfil")){
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL newurl = new URL(url);
                        final Bitmap  mIcon_val = BitmapFactory.decodeStream(newurl.openStream());
                        p.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageBitmap(mIcon_val);
                            }
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL newurl = new URL(url);
                        final Bitmap  mIcon_val = BitmapFactory.decodeStream(newurl.openStream());
                        u.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv.setImageBitmap(mIcon_val);
                            }
                        });
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        t.start();
    }

    public void fetchTweets(){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url ="https://api.twitter.com/1.1/statuses/home_timeline.json?count=10";
                final OAuthRequest request = new OAuthRequest(Verb.GET, url);
                Log.d("respuesta", "ID: "+url);
                service.signRequest(accessToken, request);
                try {
                    final Response response = service.execute(request);
                    Log.d("respuesta", response.getBody());

                    try {
                        UserData.getInstance().setJSONtweets(new JSONArray(response.getBody()));

                        u.listTweets();
                    } catch (JSONException e) {
                        Log.d("jsond", e.toString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

     public void sendTweet(String a){
        a = a.replace(" ", "+");
        final String tweet = a;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String url ="";
                url = "https://api.twitter.com/1.1/statuses/update.json?status="+tweet;
                final OAuthRequest request = new OAuthRequest(Verb.POST, url);
                Log.d("respuesta", "ID: "+url);
                service.signRequest(accessToken, request);
                try {
                    final Response response = service.execute(request);
                    Log.d("respuesta", response.getBody());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                p.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(p, "Tweet publicado", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        t.start();
     }
}
