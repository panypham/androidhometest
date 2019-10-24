package com.dien.android_home_test.utils;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dien.android_home_test.MainActivity;
import com.dien.android_home_test.model.Item;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ParseContent {

    private MainActivity activity;
    private SwipeRefreshLayout swipeRefresh;
    private static final int json = 1;

    public ParseContent(MainActivity activity, SwipeRefreshLayout swipeRefresh) {
        this.activity = activity;
        this.swipeRefresh = swipeRefresh;
    }

    public void parseJson() throws MalformedURLException {
        final URL url = new URL("https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json");
        if (!Utils.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "Connect Fail", Toast.LENGTH_SHORT).show();
        } else {
            Utils.showSimpleProgressDialog(activity);
            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... voids) {
                    StringBuilder response = new StringBuilder();

                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String item = "";
                        while (item != null) {
                            item = bufferedReader.readLine();
                            response.append(item);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response.toString();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        switch (ParseContent.json) {
                            case json:
                                Utils.removeSimpleProgressDialog();
                                getAll(s);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }
    }

    private void getAll(String response) throws JSONException {
        List<Item> itemList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        Item item;
        for (int i = 0; i < jsonArray.length(); i++) {
            item = new Item(jsonArray.get(i).toString());
            itemList.add(item);
        }
        activity.initAdapter(itemList);
        swipeRefresh.setRefreshing(false);
    }
}
