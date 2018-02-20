package com.example.arnavsarin.gw2app;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Arnav Sarin on 12/31/2017.
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader extends AsyncTask<URL,Integer, Long> {
    private URL [] ur;
    private ArrayList <TradingPost> m1;
    public boolean completed;

    //new value added for arrayList in Screen Main
    private URL urp;



    public FileDownloader(URL temp){
        urp=temp;
        m1=new ArrayList<TradingPost>();
        completed=false;
    }

    public FileDownloader(URL [] url){
        ur=url;
        System.out.println("ur= " + ur.toString());
        System.out.println("url= " + url.toString());
        m1=new ArrayList<TradingPost>();
        completed=false;

    }


    protected Long doInBackground(URL... urls){
        System.out.println("In DOINBACK");
        long totalSize = 0;
        try {
                System.out.println("before.....read");
            JSONArray foundJson = readJsonFromUrl(urls[0].toString());
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
    static JSONArray readJsonFromUrl(String url) throws Exception {
        JSONArray json = new JSONArray();
        Log.d("joke",url);
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText;
            jsonText = readAll(rd);
            json = new JSONArray(jsonText);
            return json;
        } catch (IOException e) {
            Log.d("apple",e.toString());
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
        Integer[] data = gson.fromJson(response,Integer[].class);
        for(int i=0; i<data.length; i++) {
            m1.add(new TradingPost(data[i]));
        }
        completed=true;
    }

    public ArrayList<TradingPost> getTradingPost(){
        return m1;
    }


}
