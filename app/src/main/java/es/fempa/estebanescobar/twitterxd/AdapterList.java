package es.fempa.estebanescobar.twitterxd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterList extends ArrayAdapter<Tweet> {

    private UltimosTweets u;

    public AdapterList (Context context, int resource, ArrayList<Tweet> objects, UltimosTweets u){
        super(context, resource, objects);
        this.u = u;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.tweet_list, parent, false);
        }

        TextView user = (TextView)convertView.findViewById(R.id.user);
        TextView text = (TextView)convertView.findViewById(R.id.text);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);

        Tweet t = UserData.getInstance().getTweets().get(position);
        user.setText(t.getUser());
        text.setText(t.getTweet());
        HilosXD h = new HilosXD(u);
        h.getImg(t.getImage(), img, "");
        return convertView;

    }

}
