package com.example.chatred.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chatred.HomeActivity;
import com.example.chatred.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText email,password,username,phone;
    TextView logint;
    Button Bregister;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        email =findViewById(R.id.remail);
        password=findViewById(R.id.rpassword);
        username=findViewById(R.id.rusername);
        phone=findViewById(R.id.rphone);
        Bregister =findViewById(R.id.bttnregister);
        logint=findViewById(R.id.tLogin);
        auth=FirebaseAuth.getInstance();

        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Txt_username =username.getText().toString();
                String Txt_email =email.getText().toString();
                String Txt_password=password.getText().toString();
                String Txt_phone=phone.getText().toString();
                if (TextUtils.isEmpty(Txt_email)||TextUtils.isEmpty(Txt_phone)||TextUtils.isEmpty(Txt_password)||TextUtils.isEmpty(Txt_username)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if (Txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this,"password Must be at least 6 character ",Toast.LENGTH_LONG).show();
                }
                else {
                    register(Txt_email,Txt_password,Txt_username,Txt_phone);
                }
            }
        });

        logint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
    private void register(final String email, String password, final String username, final String phone){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=auth.getCurrentUser();
                            assert user != null;
                            String userid =user.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String,String> hashMap =new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("imageUrl","default");
                            hashMap.put("phone",phone);
                            hashMap.put("email",email);
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent =new Intent(RegisterActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"you can't register with this email and password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
