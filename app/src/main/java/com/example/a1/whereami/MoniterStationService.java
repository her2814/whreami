package com.example.a1.whereami;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MoniterStationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String lineid = intent.getStringExtra("lineid");
        final String carno = intent.getStringExtra("carno");
        final StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final int STEP_NONE = 0;
                final int STEP_BSTOPNAME = 1;
                final int STEP_CARNO = 2;
                int step = STEP_NONE;

                String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busInfoRoute?lineid=" + lineid + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
                Log.e("라인아이디값 : ",lineid);
                try {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String bsstopname = null;
                    String station_carno = null;
                    String nextStation = null;
                    String nowstationTemp = startDestinationVO.getStartStation();

                    boolean isFindNowStation = false;

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        XmlPullParser xmlPullParser = Xml.newPullParser();
                        xmlPullParser.setInput(conn.getInputStream(), "UTF-8");
                        int eventType = xmlPullParser.getEventType();

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_DOCUMENT) {
                                Log.i("xml시작", "모니터링의 시작");

                            } else if (eventType == XmlPullParser.START_TAG) {
                                String startTag = xmlPullParser.getName();
                                if (startTag.equals("bstopnm")) {
                                    step = STEP_BSTOPNAME;
                                } else if (startTag.equals("carNo")) {
                                    step = STEP_CARNO;
                                }
                            } else if (eventType == XmlPullParser.TEXT) {
                                String text = xmlPullParser.getText();
                                if (step == STEP_BSTOPNAME) {
                                    Log.i("bstopname : ", text);
                                    bsstopname = text;
                                    if(isFindNowStation){
                                        nextStation = text;
                                        startDestinationVO.setNextStation(nextStation);
                                    }
                                    step = STEP_NONE;
                                } else if (step == STEP_CARNO) {
                                    Log.i("bstopcarno : ", text);
                                    station_carno = text;
                                    if(carno.equals(station_carno.substring(3))){
                                        startDestinationVO.setStartStation(bsstopname);
                                        isFindNowStation = true;
                                    }
                                    step = STEP_NONE;
                                }
                            }
                            eventType = xmlPullParser.next();
                        }
                    }

                    if(!nowstationTemp.equals(bsstopname)){
                        sendBroadcast(new Intent("changeStation"));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };

        asyncTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

}
