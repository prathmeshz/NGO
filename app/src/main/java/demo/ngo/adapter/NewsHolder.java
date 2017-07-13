package demo.ngo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import demo.ngo.R;

/**
 * Created by prathmesh zade on 02/26/2017.
 */

public class NewsHolder extends RecyclerView.ViewHolder
{
    TextView head,desc,name;
    Button button;
    public NewsHolder(View itemView)
    {
        super(itemView);
        head= (TextView) itemView.findViewById(R.id.new_news_head);
        desc= (TextView) itemView.findViewById(R.id.desc);
        name= (TextView) itemView.findViewById(R.id.new_news_name);
        //button= (Button) itemView.findViewById(R.id.button4);

    }
}
