package com.example.a1.whereami;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StationParsing extends AsyncTask<ArrayList<Station>,Void,Void>{
    String API_KEY = "nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
    Station station;
    String station_name;
    RecyclerView.Adapter adapter;

    StationParsing(String station_name, RecyclerView.Adapter adapter){
        this.station_name = station_name;
        this.adapter = adapter;
    }

    private String getResponse(){
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

                return requset_result.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void searchStation(ArrayList<Station> stations){
        final int STEP_NONE = 0;
        final int STEP_BSTOPARSNO = 1;
        final int STEP_BSTOPNM = 2;
        final int STEP_STOPTYPE= 3;
        int step = STEP_NONE;

        ArrayList<Station> stationList = stations;

        String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busStop?bstopnm=" + station_name + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D&numOfRows=100&pageNo=1";
        try {
            URL url = new URL(strurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int bstoparsno = 0;
            String bstopNm = null;
            String stoptype = null;

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setInput(conn.getInputStream(),"UTF-8");
                int eventType = xmlPullParser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_DOCUMENT){
                        Log.i("xml시작","xml 파싱의 시작");
                    }
                    else if(eventType == XmlPullParser.START_TAG){
                        String startTag = xmlPullParser.getName();

                        if(startTag.equals("bstopArsno")){
                            step = STEP_BSTOPARSNO;
                        } else if(startTag.equals("bstopNm")){
                            step = STEP_BSTOPNM;
                        } else if(startTag.equals("stoptype")){
                            step = STEP_STOPTYPE;
                        }
                    }
                    else if(eventType == XmlPullParser.TEXT){
                        String text = xmlPullParser.getText();
                        if(step == STEP_BSTOPARSNO){
                            Log.i("bstoparsno : ", text);
                            bstoparsno = Integer.parseInt(text);
                            step=STEP_NONE;
                        }
                        else if(step == STEP_BSTOPNM){
                            Log.i("bstopNm : ", text);
                            bstopNm = text;
                            step=STEP_NONE;
                        }
                        else if(step == STEP_STOPTYPE){
                            Log.i("stoptype : ", text);
                            stoptype = text;
                            step=STEP_NONE;
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG){
                        String endTag = xmlPullParser.getName();
                        if(endTag.equals("item")){
                            Log.i("dataset : ", bstoparsno + "," + bstopNm + "," + stoptype);
                            stationList.add(new Station(bstoparsno,0,bstopNm,stoptype));
                        }
                    }
                    eventType = xmlPullParser.next();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(ArrayList<Station>... arrays) {
        searchStation(arrays[0]);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
