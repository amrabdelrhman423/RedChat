package com.example.chatred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatred.auth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {


    EditText edit_email;
    Button btn_reset;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_email=findViewById(R.id.send_email);
        btn_reset=findViewById(R.id.btn_reset);
        auth=FirebaseAuth.getInstance();
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edit_email.getText().toString();
                if (email.equals("")){
                    Toast.makeText(ResetPasswordActivity.this, "All fileds are required !", Toast.LENGTH_SHORT).show();
                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPasswordActivity.this, "Check you email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            }else {
                                String error=task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this, error, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
