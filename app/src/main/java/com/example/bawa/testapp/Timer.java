package com.example.bawa.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private TextView getTm,pl;
    private CountDownTimer mCountDownTimer;
    private long tm;
    private String str,str1,ky,st,test;
    private DatabaseReference db;
    private Button leave;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mContext= this;
        Intent intE=getIntent();
        String s= intE.getStringExtra("Value");
        str=intE.getStringExtra("Plate");
        str1=intE.getStringExtra("SlotId");
        st=intE.getStringExtra("Key");
        leave=(Button)findViewById(R.id.leave);
         tm= Long.valueOf(s);
         tm=tm*60*1000;
         pl=(TextView)findViewById(R.id.plateToast);
         pl.setText("Slot " + str1+" reserved to Plate# "+str);
         db=FirebaseDatabase.getInstance().getReference("ParkingSlots");
         db.child(st).child("Status").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 test=dataSnapshot.getValue().toString();
                 if(test.equals("Empty")) {
                     //Toast.makeText(getApplicationContext(),test,Toast.LENGTH_LONG).show();}
                     Intent intd = new Intent(mContext, Login.class);
                     intd.putExtra("Greet","Thanks for parking with us, Have a great trip!");
                     mContext.startActivity(intd);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


        startTimer();
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseDatabase.getInstance().getReference("ParkingSlots");
                db
                        .orderByChild("Plate Number").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                            ky = childSnapshot.getKey();


                        db.child(ky).child("Plate Number").setValue("null");
                        db.child(ky).child("Status").setValue("Empty");
                        Intent i= new Intent(mContext,Login.class);
                        Log.d("xj",ky);
                        mContext.startActivity(i);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


       /* db.child(ky).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                st=dataSnapshot.child("Status").getValue().toString();
                if(st.equals("Empty")){
                    Intent ins= new Intent(mContext,Login.class);
                    mContext.startActivity(ins);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }




    private void startTimer(){
        mCountDownTimer= new CountDownTimer(tm,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tm=millisUntilFinished;
                updateText();

            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void updateText(){
        int minutes= (int)(tm/1000)/60;
        int seconds= (int)(tm/1000)%60;
        String timeLeft= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        getTm=(TextView)findViewById(R.id.textView7) ;;
        getTm.setText(timeLeft);
    }
}
