package com.example.arnavsarin.gw2app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Arnav Sarin on 12/28/2017.
 */

public class Product implements Serializable {
    public int id;
    public ArrayList<BuyVal> buys;
    public ArrayList<Sell> sells;

    Product (int i1, ArrayList<BuyVal> b1, ArrayList<Sell> s1){
        id=i1;
        for(int i=0;i<b1.size();i++){
            buys.add(b1.get(i));
        }
        for(int j=0;j<s1.size();j++){
            sells.add(s1.get(j));
        }
    }

    void setId(int i1){id=i1;}
    int getId (){return id;}
    void setBuys(ArrayList<BuyVal> b1){for(int i=0;i<b1.size();i++){buys.add(b1.get(i));}}
    ArrayList<BuyVal> getBuys(){return buys;}
    void setSells(ArrayList<Sell> s1){for(int i=0;i<s1.size();i++){sells.add(s1.get(i));}}
    ArrayList<Sell> getSells(){return sells;}

}
