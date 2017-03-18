package demo.ngo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import demo.ngo.R;

/**
 * Created by prathmesh zade on 02/26/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<NewsHolder> {

    ArrayList<HashMap<String,String>> list;
    Context context;

    //private clicklistener listener;

    public EventAdapter(Context context, ArrayList<HashMap<String,String>> list) {
        this.context=context;
        this.list=list;
    }
    public void setData(ArrayList<HashMap<String,String>> list)
    {
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.custom_layout_news,null));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position)
    {
        if(!list.isEmpty()) {
            HashMap<String, String> hashMap = list.get(position);
            holder.head.setText(hashMap.get("host"));
            holder.desc.setText(hashMap.get("title"));
            holder.name.setText(hashMap.get("description"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
