package com.example.khalil.models;

import androidx.lifecycle.MutableLiveData;

import com.example.khalil.ui.Category;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsApiDataSource {
    private static final String apiKey = "c3166900696849579d68cad8b4acce11";
    private static final String url = "https://newsapi.org/v2/top-headlines?country=il";

    //async
    public void getNews(Category category, MutableLiveData<List<Article>> callback, MutableLiveData<Exception> excCallback) {
        OkHttpClient client = new OkHttpClient ();
        String cat = category.getValue ().isEmpty () ? "" : "&category=" + category.getValue ();
        Request request = new Request.Builder ().url (url + "&apiKey=" + apiKey + cat).build ();
        client.newCall (request).enqueue (new Callback () {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace ();
                excCallback.postValue (e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                //parsing the Json into Article Objects
                String jsonString = response.body ().string ();
                List<Article> articleList = new ArrayList<> ();
                try {
                    JSONObject rootObject = new JSONObject (jsonString);
                    JSONArray articles = rootObject.getJSONArray ("articles");

                    for (int i = 0; i < articles.length (); i++) {
                        JSONObject articleObject = articles.getJSONObject (i);

                        String author = articleObject.getString ("author");
                        String title = articleObject.getString ("title");
                        String description = articleObject.getString ("description");
                        String url = articleObject.getString ("url");
                        String urlToImage = articleObject.getString ("urlToImage");

                        articleList.add (new Article (author, title, description, url, urlToImage));
                    }


                    //live data -> postValue
                    callback.postValue (articleList);
                } catch (JSONException e) {
                    e.printStackTrace ();
                    excCallback.postValue (e);
                }
            }
        });
    }
}
