package demo.ngo.dialog;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import demo.ngo.R;
import demo.ngo.TabActivity;


public class Dialog1 extends Dialog implements View.OnClickListener {
    Context context;
    String type;
    TextInputLayout TLUserName, TLPassword,TLCPassword;
    EditText usrName, Pwd,CPwd;
    Button submitButton;
    FirebaseAuth firebaseAuth;
    LoginListener loginListener;
    ProgressDialog progressDialog;
    public Dialog1(Context context,String type,LoginListener loginListener)
    {
        super(context);
        this.context=context;
        this.type=type;
        this.loginListener=loginListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog1);

        TLUserName = (TextInputLayout) findViewById(R.id.TNAME);
        TLPassword = (TextInputLayout) findViewById(R.id.TPASS);
        submitButton = (Button) findViewById(R.id.LOGIN);

        usrName = (EditText) findViewById(R.id.UNAME);
        Pwd = (EditText) findViewById(R.id.PASSWORD);
        if(type.equals("signup")) {
            CPwd = (EditText) findViewById(R.id.CPASS);
            TLCPassword =(TextInputLayout)findViewById(R.id.TCPASS);
            TLCPassword.setVisibility(View.VISIBLE);

        }
        else
        {
            CPwd = (EditText) findViewById(R.id.CPASS);
            TLCPassword =(TextInputLayout)findViewById(R.id.TCPASS);
            TLCPassword.setVisibility(View.GONE);
        }
        submitButton.setOnClickListener(this);

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
    }

    @Override
    public void onClick(View v) {

        if (type.equals("signup"))
        {
            if (Pwd.getText().toString().equals(CPwd.getText().toString())) {
                TLPassword.setError("");
                TLCPassword.setError("");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(usrName.getText().toString(),Pwd.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                        firebaseAuth.getCurrentUser().sendEmailVerification();
                            DatabaseReference databaseReference=database.getReferenceFromUrl("https://ngo-test-6b3e0.firebaseio.com/users");
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("name").setValue("name");
                            Toast.makeText(getContext(), "Registered Please Login ", Toast.LENGTH_LONG).show();
                            Dialog1.this.dismiss();
                            progressDialog.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "ERROR" + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
               /* FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();

                user.sendEmailVerification();*//*.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),"Verification mail has been sent to your email id, Please verify before login ",Toast.LENGTH_LONG).show();
                    }
                });*/
            }
            else
            {
                TLPassword.setError("Password Doesn't Match");
                TLCPassword.setError("Password Doesn't Match");
            }
        }
        else
        {

            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(usrName.getText().toString(), Pwd.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getContext(),"Successful Login",Toast.LENGTH_LONG).show();
                                loginListener.login(type);
                                progressDialog.dismiss();
                            }
                            else {
                                Toast.makeText(getContext(),"Error Login",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }

    public interface LoginListener {
        void login(String type);
    }
}

