package com.example.bawa.testapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private RecyclerView rView;
    private DatabaseReference db;
    private ArrayList<Blog> bl;
    private BlogViewHolder adapter;
    private Button btn;
    static Blog s;
    private String str;

    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;
        Intent greet = getIntent();
        str=greet.getStringExtra("Greet");

        db = FirebaseDatabase.getInstance().getReference("ParkingSlots");

        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_login);
        db.keepSynced(true);
        rView = (RecyclerView) findViewById(R.id.recView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog,BlogViewHolder>(Blog.class,R.layout.park_row,BlogViewHolder.class,db){
            protected void populateViewHolder(BlogViewHolder viewHolder,Blog model,int position){
                viewHolder.setID(model.getSlotID());
                viewHolder.setStatus(model.getStatus());
                 viewHolder.setImg(getApplicationContext(),model.getImage());
               // Toast.makeText(getApplicationContext(),"Hrere",Toast.LENGTH_LONG).show();

            }

        };
        rView.setAdapter(firebaseRecyclerAdapter);


    }



    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
//        Context mContext;
//
//        public Context getmContext() {
//            return mContext;
//        }

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               Intent i= new Intent(mContext,LoginPage.class);
             // i.putExtra("ID",".");
               mContext.startActivity(i);
              // Login.startActivity(i);

                Log.d("Clcicked","I am clicked");


                }
            });
        }


        public void setID(String id){
            TextView post_id= (TextView)mView.findViewById(R.id.slotID);
            post_id.setText(id);
        }
        public void setStatus(String st){
            TextView post_st= (TextView)mView.findViewById(R.id.statusView);
            post_st.setText(st);
        }
        public void setImg(Context ctx, String img){

            ImageView post_img= (ImageView) mView.findViewById(R.id.imgView);
            Picasso.with(ctx).load(img).into(post_img);

        }

    }
    // final EditText name = (EditText) findViewById(R.id.testUser);
       // final EditText slot = (EditText) findViewById(R.id.slotNumber);
        //final Button btn= (Button) findViewById(R.id.submitButton);
        //final TextView data=(TextView) findViewById(R.id.textView);
        //Firebase.setAndroidContext(this);
      /*  mRef= new Firebase("https://testapp-b3c38.firebaseio.com/Name");

        mRef1= new Firebase("https://testapp-b3c38.firebaseio.com/Users");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str= name.getText().toString();
                String str1 = slot.getText().toString();
                Firebase child = mRef1.child(str);
                child.setValue(str1);
               // child.setValue("5");


            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String res= dataSnapshot.getValue(String.class);
                data.setText(res);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/







}
