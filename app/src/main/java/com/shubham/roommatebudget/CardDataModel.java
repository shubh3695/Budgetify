package com.shubham.roommatebudget;

/**
 * Created by admin on 7/9/2016.
 */
public class CardDataModel
{
    String item;
    String cost;
    String time;
    public CardDataModel() {
    }
    public CardDataModel(String item,String cost,String time )
    {
        this.item=item;
        this.cost=cost;
        this.time=time;
    }
    public String getCost() {
        return cost;
    }

    public String getItem() {
        return item;
    }

    public String getTime() {
        return time;
    }
}

