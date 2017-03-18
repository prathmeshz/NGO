package demo.ngo.profiletab;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import demo.ngo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class user extends Fragment implements View.OnClickListener {

    EditText gname,gmobile,gaddress,gmail,gabout;
    Button button;
    DatabaseReference user;
    FirebaseDatabase database;
    String ngo="f";
    public user() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gname= (EditText) getActivity().findViewById(R.id.gname);
        gmobile= (EditText)getActivity(). findViewById(R.id.gmobile);
        gaddress= (EditText) getActivity().findViewById(R.id.gaddress);
        gmail= (EditText) getActivity().findViewById(R.id.gmail);
        gabout= (EditText)getActivity(). findViewById(R.id.gabout);
        button= (Button) getActivity().findViewById(R.id.next_user);
        button.setOnClickListener(this);
        database=FirebaseDatabase.getInstance();
        final String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users");
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,String> map= (HashMap<String, String>) dataSnapshot.getValue();
                Set<String> key=map.keySet();

                if(key.contains(userid)){
                    user.child(userid).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HashMap<String,String> map1= (HashMap<String, String>) dataSnapshot.getValue();
                            gname.setText(map1.get("name"));
                            gmobile.setText(map1.get("mobileno"));
                            gaddress.setText(map1.get("address"));
                            final FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()) {
                                gmail.setText(map1.get("email"));
                            }else {
                                gmail.setText(map1.get("email"));
                                gmail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getContext(),"Verification mail has been sent to your email id, Please verify before login ",Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                            }


                            gabout.setText(map1.get("about"));
                            ngo=map1.get("ngo");
                            Log.d("value",map1.get("about")+""+ngo);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        new ProfileSetup(getContext()).execute(gname.getText().toString(),gmobile.getText().toString(),gaddress.getText().toString(),gmail.getText().toString(),gabout.getText().toString(),ngo);
    }

    class ProfileSetup extends AsyncTask<String,Void,Void>
    {
        Context context;
        ProgressDialog progressDialog;

        public ProfileSetup(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setTitle("Creating Profile");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(final String... params) {

                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            DatabaseReference userRef=database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users");
                            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(params[0]);
                            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mobileno").setValue(params[1]);
                            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").setValue(params[2]);
                            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(params[3]);
                            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("about").setValue(params[4]);
                            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("help").setValue("no");
            userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ngo").setValue(params[5]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

        }
    }
}
