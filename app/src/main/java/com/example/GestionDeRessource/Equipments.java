package com.example.GestionDeRessource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Equipments extends AppCompatActivity {

    ListView listbumisson;
    int img = R.drawable.trec;
    private ArrayList<Equipment> rec = new ArrayList<>();
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult_equipment);
        init();
    }

    private void init() {
        listbumisson = (ListView) findViewById(R.id.listEq);
        session = new SessionManager(Equipments.this);


        getDataUser();
    }

    private void getDataUser() {


        Toast.makeText(getApplicationContext(),"ggggggggggg",Toast.LENGTH_LONG).show();
        Query query = FirebaseDatabase.getInstance().getReference("Equipment");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        String reference =issue.child("reference").getValue().toString();
                        String name=issue.child("name").getValue().toString();
                        String quantity=issue.child("quantity").getValue().toString();
                        Equipment recl = new Equipment(reference,name,Integer.parseInt(quantity));
                        rec.add(recl);
                    }
                    Toast.makeText(getApplicationContext(),rec.size()+"",Toast.LENGTH_LONG).show();
                    Testadapter myadapter = new Testadapter(getApplicationContext().getApplicationContext(),rec,img);
                    listbumisson.setAdapter(myadapter);

                    listbumisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            /*Equipment tt = rec.get(i);
                            Intent intent = new Intent(Equipments.this,ReclamationDetail.class);
                            intent.putExtra("rec", tt);
                            startActivity(intent);*/
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    class Testadapter extends BaseAdapter {

        Context context;
        ArrayList<Equipment> tests;
        int rimg;

        Testadapter(Context c,ArrayList<Equipment> tests,int img)
        {
            this.context=c;
            this.tests=tests;
            this.rimg=img;
        }

        @Override
        public int getCount() {
            return tests.size();
        }

        @Override
        public Equipment getItem(int i) {
            return tests.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.listimage);
            TextView txt = row.findViewById(R.id.txt1);
            TextView txt2 = row.findViewById(R.id.txt2);

            images.setImageResource(rimg);
            txt.setText("Title: "+tests.get(position).getName()+"");
            txt2.setText("Time: "+tests.get(position).getQuantity());

            return row;
        }
    }
}