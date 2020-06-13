package com.example.rexx.megabiteserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rexx.megabiteserver.Common.Common;
import com.example.rexx.megabiteserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    MaterialEditText phone, password;
    Button btnSignIn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phone = (MaterialEditText)findViewById(R.id.phoneIn);
        password = (MaterialEditText)findViewById(R.id.passwordIn);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        //Init firebase...
        firebaseDatabase = FirebaseDatabase.getInstance();
        table_user = firebaseDatabase.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ){
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Empty Field!!!" , Toast.LENGTH_SHORT).show();
                        }
                        //check if user doesn't exist
                        else if (dataSnapshot.child(phone.getText().toString()).exists()) {

                            //Get User Info...
                            mDialog.dismiss();

                            User user = dataSnapshot.child(phone.getText().toString()).getValue(User.class);
                            user.setPhone(phone.getText().toString()); // set Phone

                            if(Boolean.parseBoolean(user.getIsStaff())) {
                                if (user.getPassword().equals(password.getText().toString())) {
                                    //Toast.makeText(SignIn.this, "Sign In Successful !", Toast.LENGTH_LONG).show();
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "Incorrect Password !!!", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(SignIn.this,"Please Login With a Staff Account",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User Doesn't Exist!!!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
