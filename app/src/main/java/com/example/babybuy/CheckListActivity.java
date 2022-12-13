package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.babybuy.Adapters.CheckListAdapter;
import com.example.babybuy.Modal.CheckListModalClass;

import java.util.ArrayList;
import java.util.List;

public class CheckListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<CheckListModalClass> itemList;
    CheckListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        initData();
        initRecyclerView();

    }

    private void initData() {
        itemList = new ArrayList<>();

        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.checkListRecyclerViewID);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CheckListAdapter(itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}