package demo.ngo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import demo.ngo.R;

/**
 * Created by prathmesh zade on 02/26/2017.
 */

public class HelpAdapter extends RecyclerView.Adapter<NewsHolder> {

    ArrayList<HashMap<String,String>> list;
    Context context;
    String help;
    private clicklistener listener;

    public HelpAdapter(Context context, ArrayList<HashMap<String,String>> list) {
        this.context=context;
        this.list=list;
    }
    public void setData(ArrayList<HashMap<String, String>> list, String help)
    {
        this.list=list;
        notifyDataSetChanged();
        this.help=help;
    }
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout_news,null));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, final int position)
    {
        if(!list.isEmpty()) {
            final HashMap<String, String> hashMap = list.get(position);
            holder.head.setText(hashMap.get("name"));
            holder.desc.setText(hashMap.get("title"));
            holder.name.setText(hashMap.get("description"));
            if (help.equals("help"))
            {
                holder.button.setVisibility(View.VISIBLE);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.click(position,hashMap);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setListener(clicklistener listener) {
        this.listener = listener;
    }
    public interface clicklistener
    {
        public void click(int no, HashMap<String, String> hashMap);
    }
}
