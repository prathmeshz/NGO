package demo.ngo.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import demo.ngo.adapter.EventAdapter;
import demo.ngo.adapter.HelpAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class F4 extends Fragment implements View.OnClickListener,HelpAdapter.clicklistener {

    RecyclerView recyclerView;
    ArrayList<HashMap<String,String>> list = null;
    HelpAdapter newsAdapter;
    ExpandableLayout expandableLayout;
    EditText host,title,description;
    long count;
    FirebaseDatabase database;
    DatabaseReference counterRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f4, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView= (RecyclerView) getActivity().findViewById(R.id.help_list);
        Button b= (Button) getActivity().findViewById(R.id.button5);
        expandableLayout= (ExpandableLayout) getActivity().findViewById(R.id.elayout_help);
        b.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        counterRef = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/help-count");

        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = (Long) dataSnapshot.getValue();
                Log.d("aaaaaa","changeee"+count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        host= (EditText) getActivity().findViewById(R.id.help_name);

        title= (EditText) getActivity().findViewById(R.id.help_title);

        description= (EditText) getActivity().findViewById(R.id.help_desc);


        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference newsRef=database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/helps");
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                newsAdapter=new HelpAdapter(getContext(),list);
                newsAdapter.setListener(F4.this);
                recyclerView.setAdapter(newsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                ArrayList<HashMap<String,String>> list1= (ArrayList<HashMap<String, String>>) dataSnapshot.getValue();

                for (int i = (list1.size() - 1); i > 0; i--) {
                    HashMap<String, String> hashMap = list1.get(i);
                    list.add(hashMap);
                }
                newsAdapter.setData(list,"help");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
                if (expandableLayout.isExpanded())
                {
                    if (!TextUtils.isEmpty(host.getText().toString())&&!TextUtils.isEmpty(title.getText().toString())&&!TextUtils.isEmpty(description.getText().toString())) {

                        final DatabaseReference newsRef = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/helps");
                        newsRef.child("" + count).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        newsRef.child("" + count).child("name").setValue(host.getText().toString());
                        newsRef.child("" + count).child("title").setValue(title.getText().toString());
                        newsRef.child("" + count).child("description").setValue(description.getText().toString());
                        counterRef.setValue(count + 1);

                        host.setText("");
                        title.setText("");
                        description.setText("");
                        expandableLayout.collapse(true);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Enter Value",Toast.LENGTH_LONG).show();
                        expandableLayout.collapse(true);
                    }
                }
                else {
                    expandableLayout.expand(true);
                }}


    }

    @Override
    public void click(int no, final HashMap<String, String> hashMap)
    {
        final DatabaseReference newsRef = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users/"+hashMap.get("uid"));
        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final HashMap<String,String> map= (HashMap<String, String>) dataSnapshot.getValue();
                AlertDialog dialog=new AlertDialog.Builder(getContext())
                        .setTitle("Respond")
                        .setMessage(map.get("name")+"\n"+map.get("mobileno")+"\n"+map.get("email"))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                newsRef.child(hashMap.get("uid")).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + ""+map.get("email")));
                                intent.putExtra(Intent.EXTRA_SUBJECT, "HELP");
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


