package com.zybooks.module5application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    List<Item> item_list = new ArrayList<>();
    private TextView itemName1;
    private TextView itemName2;
    private TextView itemCount1;
    private TextView itemCount2;
    public ItemDao itemDao;
    public boolean reset;
    public String smsText;



    public FragmentList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentList newInstance() {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        View view = inflater.inflate(R.layout.fragment_list, container, false);
        reset = false;

        ItemDatabase itemDatabase = Room.databaseBuilder(getContext(), ItemDatabase.class, "items2.db").
                allowMainThreadQueries().build();
        itemDao = itemDatabase.itemDao();




        Bundle args = getArguments();
        item_list = (List<Item>) itemDao.getItems();


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        TableLayout prices = (TableLayout) getView().findViewById(R.id.table);
        prices.setStretchAllColumns(true);
        prices.bringToFront();
        for (int i = 0; i < item_list.size(); i++) {
            TableRow tr = new TableRow(view.getContext());
            TextView c1 = new TextView(view.getContext());
            c1.setText(item_list.get(i).getItemName());
            TextView c2 = new TextView(view.getContext());
            c2.setText(String.valueOf(item_list.get(i).getItemCount()));
            Button b1 = new Button(view.getContext());
            b1.setText("Delete");
            b1.setEnabled(true);
            int finalI = i;
            int finalI1 = i;
            TextView empty = new TextView(view.getContext());
            tr.addView(b1);
            tr.addView(empty);
            tr.addView(c1);
            tr.addView(c2);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //delete item of matchin button and then reload fragment to show new list.
                    itemDao.deleteItem(item_list.get(finalI1));
                    Fragment newFrag = FragmentList.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_list, newFrag).commit();
                }
            });


            prices.addView(tr);


           // SharedPreferences sharedPref = this.getActivity().getSharedPreferences("myprefs",Context.MODE_PRIVATE);
           // smsText = sharedPref.getString("MMS","no");

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean smsText = sharedPref.getBoolean("MMS", false);

            if((Integer.parseInt(c2.getText().toString()) <= 10) && (smsText) ){
                c2.setTextColor(Color.rgb(200,0,0));
                alertFrag dialog = new alertFrag();
                dialog.show(getFragmentManager(), "warningDialog");

            }
        }



        }


/*

//XML for this code is stored in list_inventory_items.xml
        itemName1 = (TextView) getView().findViewById(R.id.itemName1);
        itemName1.setText(item_list.get(0).getItemName());

        itemName2 = (TextView) getView().findViewById(R.id.itemName2);
        itemName2.setText(item_list.get(1).getItemName());

       itemCount1 = (TextView) getView().findViewById(R.id.itemCount1);
       itemCount1.setText(String.valueOf(item_list.get(0).getItemCount()));

        itemCount2 = (TextView) getView().findViewById(R.id.itemCount2);
        itemCount2.setText(String.valueOf(item_list.get(1).getItemCount()));

 */


    }





















