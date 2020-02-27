package es.fempa.estebanescobar.twitterxd;

public class Tweet {

    private String image;
    private String user;
    private String tweet;

    public Tweet(){
        image = "";
        user = "";
        tweet = "";
    }

    public Tweet(String image, String user, String tweet) {
        this.image = image;
        this.user = user;
        this.tweet = tweet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
