package com.meghaagarwal.firebasetrial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private EditText mEmailET;
    private EditText mPassET;
    //private ListView lv;
    //private ArrayList<String> mUserName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* To display multiple values in a listview
        myRef = FirebaseDatabase.getInstance().getReference("Users");
           lv = (ListView) findViewById(R.id.newListView);
           final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mUserName);
           lv.setAdapter(arrayAdapter);
           myRef.addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                   String value = snapshot.getValue(String.class);
                   mUserName.add(value);
                   arrayAdapter.notifyDataSetChanged();
               }

               @Override
               public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               }

               @Override
               public void onChildRemoved(@NonNull DataSnapshot snapshot) {

               }

               @Override
               public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
*/

          /*To retrieve data from multiple fields
          myRef.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  Map<Long, String> map = (Map<Long, String>) snapshot.getValue();
                  String name = map.get("Name");
                  String contact = String.valueOf(map.get("Contact"));
                  Log.d("Name", name);
                  Log.d("Contact", contact);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          }); */

        /*To retreive data from one field
          myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                TextView tv = (TextView) findViewById(R.id.tv);
                tv.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
  */

        //Authentication of an user to read/write data from database
        mEmailET = (EditText) findViewById(R.id.userEmailET);
        mPassET = (EditText) findViewById(R.id.userPassET);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    Intent intent = new Intent(MainActivity.this, NewActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    public void loginButtonClicked(View view)
    {
        String emailValue = mEmailET.getText().toString();
        String passValue = mPassET.getText().toString();

        if(TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(passValue))
        {
            Toast.makeText(this, "Enter both values", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(emailValue, passValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

/*
    public void buttonClicked(View view)
    {
        EditText eName = (EditText) findViewById(R.id.message);
        EditText eContact = (EditText) findViewById(R.id.contact);
        String name = eName.getText().toString();

        /*To add to one child
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.child("Names").push().setValue(name);
        */

        //Adding multiple values
/*        myRef = FirebaseDatabase.getInstance().getReference("Users").child(name);

        myRef.child("Name").setValue(eName.getText().toString());
        myRef.child("Contact").setValue(eContact.getText().toString());
        //myRef.setValue(message);

    }
*/
}
