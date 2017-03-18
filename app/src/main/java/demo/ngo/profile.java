package demo.ngo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import demo.ngo.profiletab.ProfileTabAdapter;


public class profile extends AppCompatActivity implements  View.OnClickListener {

    private int PICK_IMAGE_REQUEST = 1;
    CheckBox ngo;
    LinearLayout ngo_profile;
    ImageView imageView;
    TabLayout tableLayout;
    ViewPager viewPager;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView= (ImageView) findViewById(R.id.profile_image);
        imageView.setOnClickListener(this);
        viewPager= (ViewPager) findViewById(R.id.profile_tab);
        tableLayout= (TabLayout) findViewById(R.id.tab_profile);
        tableLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new ProfileTabAdapter(getSupportFragmentManager()));
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.profile_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
                StorageReference riversRef = firebaseStorage.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profile_photos");
                riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Photo Uploaded",Toast.LENGTH_LONG).show();


                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}