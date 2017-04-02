package demo.ngo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import demo.ngo.dialog.Dialog1;


public class MainActivity extends AppCompatActivity implements View.OnClickListener ,Dialog1.LoginListener {

    Button sign_in,gs;
    FirebaseUser fuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        TextView text = (TextView) findViewById(R.id.textView);
        Typeface tf = Typeface.createFromAsset(getAssets(), "colonna.ttf");
        text.setTypeface(tf);
        sign_in= (Button) findViewById(R.id.sign_in);
        gs= (Button) findViewById(R.id.GS);
        gs.setOnClickListener(this);
        sign_in.setOnClickListener(this);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
          finish();
            startActivity(new Intent(this,TabActivity.class));
        }
    }
    //Testing from android git update

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in:
                Dialog1 dialog1=new Dialog1(this,"login",this);
                dialog1.show();
                break;
            case R.id.GS:
                Dialog1 dialog2=new Dialog1(this,"signup",this);
                dialog2.show();
                break;
        }
    }
    @Override
    public void login(String type) {
        switch (type)
        {
            case "login":
                fuser= FirebaseAuth.getInstance().getCurrentUser();
                if(fuser.isEmailVerified())
                { finish();
                    startActivity(new Intent(this,TabActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(),"First Verify The Email",Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().signOut();
                }
                break;
        }
    }
}