package com.example.bawa.testapp;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
  private EditText ch,tm,pl;
   private DatabaseReference dbm;
   Context ctn;
   Button btn;
String str,str1,timer,plate,st;
   Blog b;

     private void getIncomingIntent(){
         if(getIntent().hasExtra("ID")){
             Log.d("abx", "Found ");
         }
     }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ch = (EditText)findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.button2);
        ctn=this;
       // ch.setText("");
        ch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                str="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    str=str+s;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tm=(EditText)findViewById(R.id.editText3);

        tm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                timer="";

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0)
                    timer+=s;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        pl=(EditText)findViewById(R.id.editText4);
        pl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                plate="";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0)
                    plate+=s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tm.setText(str);
                //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

                dbm=FirebaseDatabase.getInstance().getReference("ParkingSlots");
                if(str.equals("1")||str.equals("2")||str.equals("3")||str.equals("4")) {

                    dbm
                            .orderByChild("slotID")
                            .equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                                str1 = childSnapshot.getKey();
                            //Log.d("fg",str1);

                            dbm = FirebaseDatabase.getInstance().getReference("ParkingSlots");

                            st=dataSnapshot.child(str1).child("Status").getValue().toString();
                            Log.d("bjv",st);
                            if(st.equals("Empty")){



                            dbm.child(str1).child("Status").setValue("Full");
                            dbm.child(str1).child("Led").setValue("1");
                            dbm.child(str1).child("Plate Number").setValue(plate);

                            Intent i = new Intent(ctn, Timer.class);
                            i.putExtra("Value", timer);
                            i.putExtra("Plate", plate);
                            i.putExtra("SlotId",str);
                            i.putExtra("Key",str1);
                            ctn.startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Slot Already taken,Please select a different one",Toast.LENGTH_LONG).show();

                            }


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(),"Please Enter a valid SlotID",Toast.LENGTH_LONG).show();


            }
        });

    }



}

