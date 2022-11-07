package com.example.cultureapp;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication singleton;

    private List<Location> myLocations;

    public List<Location> getMyLocations(){
        return myLocations;
    }

    public static double[][] Dots =
            {       {56.589439, 21.365699},
                    {56.589819, 21.365421},
                    {56.589766, 21.364285},
                    {56.590231, 21.364298},
                    {56.590315, 21.364214},
                    {56.590339, 21.364012},
                    {56.590676, 21.362907},
                    {56.588078, 21.360945},
                    {56.586782, 21.369563},
                    {56.588461, 21.368869},
                    {56.588700, 21.368721},
                    {56.588941, 21.369435},
                    {56.591285, 21.374586},
                    {56.590786, 21.368347},
                    {56.586551, 21.369762}
            } ;

    public MyApplication getInstance(){
        return singleton;
    }
    public void onCreate(){
        super.onCreate();
        singleton = this;
        myLocations = new ArrayList<>();
    }
}
