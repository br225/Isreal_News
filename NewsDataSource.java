package com.example.khalil.data.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NewsDataSource {

    public static String address = "https://newsapi.org/v2/top-headlines?country=il&apiKey=c3166900696849579d68cad8b4acce11";

    public static void getMovies() {

        new Thread(() -> {
            try {
                URL url = new URL(address);

                URLConnection con = url.openConnection();
//binary input stream to String stream: Decorator Design pattern
//no libraries:
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//String concat in a loop:
                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString();
                System.out.println(json);
            } catch (MalformedURLException e) {
                System.out.println("  כתובת לא תקינה");
            } catch (IOException e) {
                System.out.println(" אין גישה לשרת ");
            }
        }).start();

    }
}
