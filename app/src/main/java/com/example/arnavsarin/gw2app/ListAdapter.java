package com.example.arnavsarin.gw2app;

/**
 * Created by Arnav Sarin on 1/3/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.arnavsarin.gw2app.databinding.RowItemBinding;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.example.arnavsarin.gw2app.R.id.parent;

public class ListAdapter extends BaseAdapter implements Filterable{
List mData;
    List mStringFilterList;
    ValueFilter valueFilter;
    private LayoutInflater inflater;

    public ListAdapter(List cancel_type){
        mData=cancel_type;
        mStringFilterList=cancel_type;
    }

@Override
    public int getCount(){
    return mData.size();
}

@Override
    public String getItem(int position){
    //added toString() might have to remove
    return mData.get(position).toString();
}

@Override
    public long getItemId(int position){
        return position;
    }

    //added final in front of position
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        //new information
        View view =convertView;
        if(view == null){
            inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item,null);
        }
        TextView listItemText = (TextView)view.findViewById(R.id.stringName);
        listItemText.setText(mData.get(position).toString());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        //Button addBtn = (Button)view.findViewById(R.id.add_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //mData.remove(position); //or some other task
                getFile(position, parent);
                notifyDataSetChanged();
            }
        });


        /*addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });*/

        /*if(inflater==null){
            inflater= (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }*/
        RowItemBinding rowItemBinding=DataBindingUtil.inflate(inflater,R.layout.row_item,parent,false);
        //added toString to this method
        rowItemBinding.stringName.setText(mData.get(position).toString());
        return rowItemBinding.getRoot();
    }

    @Override
    public Filter getFilter(){
        if(valueFilter==null){
            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            FilterResults results = new FilterResults();

            if(constraint !=null && constraint.length()>0){
                List filterList = new ArrayList();
                for (int i=0; i<mStringFilterList.size(); i++){
                    //edited the code to be tostring below instead of get(i).toUpperCase()
                    if((mStringFilterList.get(i).toString().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }else{
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults (CharSequence constraint, FilterResults results){
           //infinite
            mData= (List) results.values;
            notifyDataSetChanged();
        }
    }


    public void getFile(int position, final ViewGroup parent) {
        try {
            URL url = new URL("https://api.guildwars2.com/v2/commerce/listings/" + mData.get(position).toString());
            loadFile(url, parent);
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
    }
    public void loadFile(URL ur, final ViewGroup parent){
        //newly added setContentView needs to be unblocked
        FileDownloaderVersion2 f1=null;
        try {
            f1 = new FileDownloaderVersion2(ur);
            System.out.println("We made the FileDownloader");
            f1.execute(ur);
            Context context = parent.getContext();
            Intent intent = new Intent(context, SecondScreenMain.class);
            //intent.putExtra(ScreenMain.showDetailString,ScreenMain.showDetailString);
            intent.putExtra("Gson file",f1.getProduct());
            context.startActivity(intent);

            //setContentView(R.layout.graph_view);
        }catch(Exception e){
            System.out.println(e.toString());
        }
        while(!f1.completed){
            try{
                Thread.sleep(3000);
            }catch(Exception e){
                Log.d("This sleep method",e.toString());
            }
        }

        //GraphView graph = (GraphView)findViewById(R.id.graph);
    }



}
