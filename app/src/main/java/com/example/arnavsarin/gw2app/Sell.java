package com.example.arnavsarin.gw2app;

/**
 * Created by Arnav Sarin on 12/28/2017.
 */

public class Sell {

    int listings;
    int unit_price;
    int quantity;

    Sell(int l1, int u1, int q1){
        listings=l1;
        unit_price=u1;
        quantity=q1;
    }
    void setListings(int list){listings=list;}
    int getListings(){return listings;}
    void setUnit_price(int unit){unit_price=unit;}
    int getUnit_price(){return unit_price;}
    void setQuantity(int q1){quantity=q1;}
    int getQuantity(){return quantity;}
}
