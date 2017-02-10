package com.htlconline.sm.classmate.Batch.Data;

/**
 * Created by Anurag on 15-01-2017.
 */

public class BatchFilterData {
    String name;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    public BatchFilterData(String name, int value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    public void setValue(int value)
    {
        this.value=value;
    }


}
