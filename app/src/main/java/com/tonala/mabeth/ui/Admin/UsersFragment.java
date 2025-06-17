package com.tonala.mabeth.ui.Admin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tonala.mabeth.Inicio.errorActivity;
import com.tonala.mabeth.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static Object[] array;
    public static int sel;

    private List<String> mLista = new ArrayList<>();
    private List<String> mid = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;

    private DatabaseReference mDatabase;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(getActivity(), errorActivity.class));
            getActivity().finish();
        } else {



            mDatabase = FirebaseDatabase.getInstance().getReference();


            mListView = (ListView) view.findViewById(R.id.lista1);
            mListView.setOnItemClickListener(this);

            mDatabase.child("Usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        mLista.clear();
                        mid.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String correoList = ds.child("Email").getValue().toString();
                            String id = ds.getKey();
                            mLista.add(correoList);
                            mid.add(id);
                        }
                        array = mid.toArray();

                        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mLista);
                        mListView.setAdapter(mAdapter);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sel = position;
        Intent intent = new Intent(getActivity(), UserDataActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
}