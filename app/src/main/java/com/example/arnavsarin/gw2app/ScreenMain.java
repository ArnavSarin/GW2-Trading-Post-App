package com.example.arnavsarin.gw2app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ListAdapter;

import com.example.arnavsarin.gw2app.databinding.ActivityScreenMainBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//added implements AdapterView.OnItemClickListener
//need to add Activity
public class ScreenMain extends AppCompatActivity{ //implements AdapterView.OnItemClickListener {

    static public String showDetailString= "";

    public ArrayList<TradingPost> getAllValues(){
        ArrayList<TradingPost> m2= new ArrayList<TradingPost>();
        try {
            URL ur = new URL("https://api.guildwars2.com/v2/commerce/listings");

           m2= loadFile(ur);
        }catch(MalformedURLException w) {
             w.printStackTrace();
        }catch (Exception e){
            Log.d("Exception", e.toString());
        }
        return m2;
    }


    public ArrayList<TradingPost> loadFile(URL url){
        FileDownloader f1 = new FileDownloader(url);
        try {
            f1.execute(url);
        }catch(Exception e){
            Log.d("Exception", e.toString());
        }
        while(!f1.completed){
            try{
                Thread.sleep(3000);
            }catch(Exception e){
                Log.d("This sleep method",e.toString());
            }
        }
        return f1.getTradingPost();
    }



    ActivityScreenMainBinding activityMainBinding;
    com.example.arnavsarin.gw2app.ListAdapter adapter;
    List arrayList= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_main);


        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_screen_main);


        ArrayList<TradingPost> m3= new ArrayList<TradingPost>();
        m3=getAllValues();
        for(int i=0;i<m3.size();i++){
            arrayList.add(m3.get(i).getValues().toString());
        }


        for(int j=0;j<arrayList.size();j++){
            Log.d("happy",""+arrayList.get(j));
        }

        adapter= new com.example.arnavsarin.gw2app.ListAdapter(arrayList);

        //new code
        ListView lView = (ListView)findViewById(R.id.list_view);
        //old code
        activityMainBinding.listView.setAdapter(adapter);

        activityMainBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
public boolean onQueryTextSubmit(String query){
    return false;
}

@Override
public boolean onQueryTextChange(String newText){
    adapter.getFilter().filter(newText);
    return false;
}


});

        activityMainBinding.search.setActivated(true);
        activityMainBinding.search.setQueryHint("Type your keyword here");
        activityMainBinding.search.onActionViewExpanded();
        activityMainBinding.search.setIconified(false);
        activityMainBinding.search.clearFocus();


}






    }


