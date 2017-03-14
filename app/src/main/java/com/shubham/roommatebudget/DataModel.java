package com.shubham.roommatebudget;

/**
 * Created by admin on 7/9/2016.
 */
public class DataModel
{
    int id;
    String name;
    String item;
    String cost;
    public DataModel() {
    }
    public DataModel(int id,String name,String item,String cost)
    {
        this.id=id;
        this.name=name;
        this.item=item;
        this.cost=cost;

    }
    public DataModel(String name,String item,String cost)
    {
        this.name=name;
        this.item=item;
        this.cost=cost;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getItem() {
        return item;
    }
    public int getId() {
        return id;
    }
    public String getCost() {
        return cost;
    }
    public String getName() {
        return name;
    }
}
