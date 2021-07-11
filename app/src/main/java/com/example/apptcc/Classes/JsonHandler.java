package com.example.apptcc.Classes;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JsonHandler {
    public static String getJson(String textoUrl) {
        InputStream inputStream = null;
        String textoJson = "";
        try {
            URL url = new URL(textoUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                ;
                textoJson += linha;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("Error Network", e.toString());
        }
        return textoJson;
    }
}