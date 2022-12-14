package com.example.babybuy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.vertical_colored_curved_line);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.RED);

        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.profile_image, "Baby clothing with extra soft love", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.image01, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.image02, "Baby food with brain boosters", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.bed, "Baby Mosquito net + sleeping pad with bed", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.profile_image, "Baby clothing with extra soft love", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.image01, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.image02, "Baby food with brain boosters", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.bed, "Baby Mosquito net + sleeping pad with bed", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.pregnant_mom, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.profile_image, "Baby clothing with extra soft love", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.image01, "Baby Mosquito net + sleeping pad", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.image02, "Baby food with brain boosters", R.drawable.add_red_circle_for_category));
        itemList.add(new CheckListModalClass(R.drawable.bed, "Baby Mosquito net + sleeping pad with bed", R.drawable.add_red_circle_for_category));

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