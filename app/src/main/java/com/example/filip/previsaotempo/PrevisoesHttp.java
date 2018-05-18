package com.example.filip.previsaotempo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PrevisoesHttp {


    public static String URL;
    public static void setUrl(String city){
        URL = "https://api.hgbrasil.com/weather/?format=json&city_name="+city+"&key=4aaa9ac3";
    }


    private static HttpURLConnection connectar(String urlWebservice) {

        final int SEGUNDOS = 10000;

        try {
            java.net.URL url = new URL(urlWebservice);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10 * SEGUNDOS);
            conexao.setConnectTimeout(15 * SEGUNDOS);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return conexao;

        } catch (IOException e) {
            Log.d("ERRO", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hasConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }


    public static Previsao getPrevisoesFromJson(JSONObject json){
        String data;
        String descricao;
        String min;
        String max;
        Previsao previsaoAtual=null;

        try {
            data=json.getString("date");
            descricao=json.getString("description");
            min= json.getString("min");
            max= json.getString("max");
            previsaoAtual = new Previsao(data,descricao,min,max);
        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return previsaoAtual;
    }


    public static ArrayList<Previsao> readJson(JSONObject json) {

        ArrayList<Previsao> arrayList = new ArrayList<>();
        try {
            JSONObject results = json.getJSONObject("results");
            JSONArray jsonPrevisoes = results.getJSONArray("forecast");
            for (int i=0; i<7; i++) {
                JSONObject onePrevision = jsonPrevisoes.getJSONObject(i);
                arrayList.add(getPrevisoesFromJson(onePrevision));
            }

        } catch (JSONException e) {

            Log.d("Json", e.getMessage());
            e.printStackTrace();
        }
        return arrayList;

    }

    public static ArrayList<Previsao> loadPrevisoes() {
        try {
            HttpURLConnection connection = connectar(URL);
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(inputStream));
                ArrayList<Previsao>  currenciesList =readJson(json);
                return currenciesList;
            }

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
        }
        return null;
    }
    private static String bytesParaString(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        int byteslidos;
        try {
            while ((byteslidos = inputStream.read(buffer)) != -1) {
                bufferzao.write(buffer, 0, byteslidos);

            }
            return new String(bufferzao.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
