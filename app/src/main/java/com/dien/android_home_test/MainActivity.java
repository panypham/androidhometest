package com.dien.android_home_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.dien.android_home_test.adapter.AdapterItemList;
import com.dien.android_home_test.config.PermissionRequest;
import com.dien.android_home_test.model.Item;
import com.dien.android_home_test.utils.ParseContent;

import java.net.MalformedURLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvListItem;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionRequest permissionRequest = new PermissionRequest(this);
            permissionRequest.checkInternet();
        }
        onRefreshingLayout();
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this, "Swipe down to show the data!", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        rcvListItem = findViewById(R.id.rcvListItem);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }


    private void onRefreshingLayout() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void initData() {
        try {
            ParseContent parseContent = new ParseContent(this, swipeRefresh);
            parseContent.parseJson();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void initAdapter(List<Item> items) {
        AdapterItemList adapterItemList = new AdapterItemList(this, items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvListItem.setLayoutManager(layoutManager);
        rcvListItem.setAdapter(adapterItemList);
        adapterItemList.notifyDataSetChanged();
    }
}
