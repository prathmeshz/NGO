package demo.ngo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashMap;

import demo.ngo.R;
import demo.ngo.adapter.NewsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class F1 extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    ArrayList<HashMap<String,String>> list = null;
    NewsAdapter newsAdapter;
    ExpandableLayout expandableLayout;
    EditText news_name,news_headline,news_description,news_source;
    long count;
    FirebaseDatabase database;
    DatabaseReference counterRef,user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView= (RecyclerView) getActivity().findViewById(R.id.news);
        Button b= (Button) getActivity().findViewById(R.id.button2);
        expandableLayout= (ExpandableLayout) getActivity().findViewById(R.id.el);
       // user = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users");
        /*user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 HashMap<String,String> map = (HashMap<String, String>) dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue();

                if(map.get("ngo").equals("f")){
                    //invisible tab
                    expandableLayout.setVisibility(View.INVISIBLE);
                                   }
                else if(map.get("ngo").equals("t")){
                    expandableLayout.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getContext()," Some thing went wrong, Issue has been recorded , and will be solved within 2 hours if not Contact Support ",Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext()," Some thing went wrong, Issue has been recorded , and will be solved within 2 hours if not Contact Support ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        b.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        counterRef = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/news_count");

        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = (Long) dataSnapshot.getValue();
                Log.d("aaaaaa","change"+count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        news_name= (EditText) getActivity().findViewById(R.id.news_name);
        news_headline= (EditText) getActivity().findViewById(R.id.news_headline);
        news_description= (EditText) getActivity().findViewById(R.id.news_desc);
        news_source= (EditText) getActivity().findViewById(R.id.news_src);
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference newsRef=database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/news");
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                newsAdapter=new NewsAdapter(getContext(),list);
                recyclerView.setAdapter(newsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ArrayList<HashMap<String,String>> list1= (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();

                    for (int i = (list1.size() - 1); i > 0; i--) {
                        HashMap<String, String> hashMap = list1.get(i);
                        list.add(hashMap);
                    }
                    newsAdapter.setData(list);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
            if (expandableLayout.isExpanded()) {
                if (!TextUtils.isEmpty(news_name.getText().toString()) && !TextUtils.isEmpty(news_headline.getText().toString()) && !TextUtils.isEmpty(news_description.getText().toString()) && !TextUtils.isEmpty(news_source.getText().toString())) {

                    final DatabaseReference newsRef = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/news");
                    newsRef.child("" + count).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    newsRef.child("" + count).child("name").setValue(news_name.getText().toString());
                    newsRef.child("" + count).child("headline").setValue(news_headline.getText().toString());
                    newsRef.child("" + count).child("description").setValue(news_description.getText().toString());
                    newsRef.child("" + count).child("source").setValue(news_source.getText().toString());
                    counterRef.setValue(count + 1);
                    news_name.setText("");
                    news_headline.setText("");
                    news_source.setText("");
                    news_description.setText("");

                    expandableLayout.collapse(true);
                } else {
                    Toast.makeText(getContext(), "Enter Value", Toast.LENGTH_LONG).show();
                    expandableLayout.collapse(true);
                }
            } else {
                expandableLayout.expand(true);
            }
                break;
        }



    }
}
