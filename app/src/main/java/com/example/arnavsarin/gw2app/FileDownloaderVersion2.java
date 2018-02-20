package com.example.arnavsarin.gw2app;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Arnav Sarin on 1/12/2018.
 */

public class FileDownloaderVersion2 extends AsyncTask<URL,Integer, Long> {
    private URL [] ur;
    private Product m1;
    public boolean completed;

    //new value added for arrayList in Screen Main
    private URL urp;



    public FileDownloaderVersion2(URL temp){
        urp=temp;
        completed=false;
    }

    public FileDownloaderVersion2(URL [] url){
        ur=url;
        System.out.println("ur= " + ur.toString());
        System.out.println("url= " + url.toString());
        completed=false;

    }


    protected Long doInBackground(URL... urls){
        System.out.println("In DOINBACK");
        long totalSize = 0;
        try {
            System.out.println("before.....read");
            JSONObject foundJson = readJsonFromUrl(urls[0].toString());
            parseJSON(foundJson.toString());
            System.out.println("after.....read");
            System.out.println("URL has been downloaded");

        }catch(Exception e){
            System.out.println(e.toString());
        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }



    /**
     * Method is used to parse the URL. It also gets the entire JSON file to save it into a JSON object
     *
     * @return the JSON obejct that is returned to the getJSONInformation method
     * @throws Exception SOURCE: http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
     */
    static JSONObject readJsonFromUrl(String url) throws Exception {
        JSONObject json = new JSONObject();
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText;
            jsonText = readAll(rd);
            json = new JSONObject(jsonText);
            return json;
        } catch (IOException e) {
            //comment to prevent errors
        }
        return json;
    }

    /**
     * Helper method for readJSONFromURL
     *
     * @param rd reader object passed in
     * @return string of url contents
     * @throws IOException SOURCE: http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
     */
    private static String readAll(Reader rd) throws IOException {
        //creates a string of the response from the API
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    /**
     * This method Uses the GSON library to parse the String response and it uses it to store the JSON object
     * @param response - It is passed from the getJSONInformation method. It is a string representation of the JSON
     */

    //changed private to public
    public void parseJSON(String response) {
            //parses the Gson and sets up the initial status variables based on the passed Gson
            Gson gson = new Gson();
        Product data = gson.fromJson(response,Product.class);
        //Log.d("This is my data", data.results.get(0).getTitle());
        if(m1.getBuys()==null && m1.getSells()==null){
            m1=data;
        }else if(m1.getBuys()==null){
            m1.getBuys().addAll(data.getBuys());
        }else if(m1.getSells()==null){
            m1.getSells().addAll(data.getSells());
    }else{
            m1.getBuys().addAll(data.getBuys());
            m1.getSells().addAll(data.getSells());
        }

        //problematic if there is ever an id named 0
        if(m1.getId()==0){
            m1.setId(data.getId());
        }

        completed=true;
    }

    public Product getProduct(){
        return m1;
    }



}
