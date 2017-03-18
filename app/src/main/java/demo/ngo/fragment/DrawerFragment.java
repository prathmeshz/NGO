package demo.ngo.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import demo.ngo.Aboutus;
import demo.ngo.MainActivity;
import demo.ngo.R;
import demo.ngo.profile;


public class DrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView listView;
    String[] values = new String[] {"Profile","FAQ","Write Us","AboutUs","SignOut"};

    ArrayAdapter<String> arrayAdapter;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawer;
    TextView tv;
    de.hdodenhof.circleimageview.CircleImageView imageView2;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        listView= (ListView) getActivity().findViewById(R.id.drawer_list);
        arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1, values);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        imageView= (ImageView) getActivity().findViewById(R.id.imageView3);
        imageView2= (CircleImageView) getActivity().findViewById(R.id.imageView);
        //Picasso.with(getContext()).load(R.drawable.header).fit().into(imageView);

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference islandRef = storage.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_photos");
        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
            Picasso.with(getContext()).load(uri).fit().into(imageView2);
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle any errors
        }
    });
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference userRef=database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,String> map= (HashMap<String, String>) dataSnapshot.getValue();
                tv= (TextView) getActivity().findViewById(R.id.header_name);
                tv.setText(map.get("name")+"\n"+map.get("email"));
                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference islandRef = storage.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_photos");
                islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).fit().into(imageView2);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar)
    {
        drawer=drawerLayout;
        drawerToggle=new ActionBarDrawerToggle(getActivity(),drawer,toolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);

            }
        };

        drawer.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
        drawer.setDrawerListener(drawerToggle);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        switch (position)
        {
            case 0:
                startActivity(new Intent(getActivity(),profile.class));
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:
                startActivity(new Intent(getActivity(),Aboutus.class));
                break;
            case 4:
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(),MainActivity.class));
                break;
        }
    }
}
