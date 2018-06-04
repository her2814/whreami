package com.example.a1.whereami;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class StationParsing extends AsyncTask<Void,Void,Void>{
    String API_KEY = "nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
    Station station;
    String station_name;

    StationParsing(String station_name){
        this.station_name = station_name;
    }

    public void getResponse(){
        String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busStop?bstopnm=" + station_name + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D&numOfRows=100&pageNo=1";
        try {
            URL url = new URL(strurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                StringBuffer requset_result = new StringBuffer();
                String line;

                while((line = br.readLine())!=null) {
                    requset_result.append(line);
                }

                Log.e("urlresponse : ", String.valueOf(requset_result));
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getResponse();

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
