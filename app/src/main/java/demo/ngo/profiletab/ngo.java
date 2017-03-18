package demo.ngo.profiletab;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import demo.ngo.R;

public class ngo extends Fragment implements View.OnClickListener {


    EditText nname,nmobile,nmail,naddress,nsd,nweb,bname,baccno,bifsc;
    Button button;
    DatabaseReference ngo,user;
    FirebaseDatabase database;
    public ngo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ngo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nname= (EditText) getActivity().findViewById(R.id.nname);
        nmobile= (EditText) getActivity().findViewById(R.id.nmobile);
        nmail= (EditText)getActivity(). findViewById(R.id.nmail);
        naddress= (EditText) getActivity().findViewById(R.id.naddress);
        nsd= (EditText) getActivity().findViewById(R.id.nsd);
        nweb= (EditText) getActivity().findViewById(R.id.nweb);
        bname= (EditText) getActivity().findViewById(R.id.bname);
        baccno= (EditText) getActivity().findViewById(R.id.baccno);
        bifsc= (EditText) getActivity().findViewById(R.id.bifsc);
        button= (Button) getActivity().findViewById(R.id.next_ngo);
        button.setOnClickListener(this);
         database=FirebaseDatabase.getInstance();
        final String ngoid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        ngo = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/NGOS");
        user = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ngo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,String> map= (HashMap<String, String>) dataSnapshot.getValue();
                if(map.containsKey(ngoid)){
                ngo.child(ngoid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,String> map1= (HashMap<String, String>) dataSnapshot.getValue();
                        nname.setText(map1.get("name"));
                        bname.setText(map1.get("bname"));
                        baccno.setText(map1.get("acc"));
                        bifsc.setText(map1.get("ifsc"));
                        naddress.setText(map1.get("address"));
                        nmail.setText(map1.get("email"));
                        nmobile.setText(map1.get("mobile"));
                        nweb.setText(map1.get("website"));
                        nsd.setText(map1.get("desc"));
                        user.child("ngo").setValue("t");

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
        new ProfileSetup(getContext()).execute(nname.getText().toString(),nmobile.getText().toString(),nmail.getText().toString(),naddress.getText().toString(),nsd.getText().toString(),nweb.getText().toString(),bname.getText().toString(),baccno.getText().toString(),bifsc.getText().toString());
    }

    class ProfileSetup extends AsyncTask<String,Void,Void>
    {
        Context context;
        ProgressDialog progressDialog;
        private boolean regNgo=false;

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

                                DatabaseReference ngoRef = database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/NGOS");

                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(params[0]);
                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mobile").setValue(params[1]);
                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(params[2]);
                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").setValue(params[3]);

            ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("desc").setValue(params[4]);
            ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("website").setValue(params[5]);

                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("bname").setValue(params[6]);
                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("acc").setValue(params[7]);
                                ngoRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ifsc").setValue(params[8]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }
}
