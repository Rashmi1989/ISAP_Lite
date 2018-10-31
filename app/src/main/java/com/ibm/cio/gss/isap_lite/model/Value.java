package com.ibm.cio.gss.isap_lite.model;

import android.support.annotation.NonNull;

/*
Class Name : "Value"
Description :"Model object to hold the Client name and Id and and with section info ."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

public class Value implements Comparable {
    private String name;
    private String section;
    private boolean isSectionHeader;
    private int id;
    public Value(String name, String section,int ID)
    {
        this.id=ID;
        this.name = name;
        this.section = section;
        isSectionHeader = false;
    }

    public String getName()
    {
        return name;
    }
    public int getId()
    {
        return id;
    }

    public String getSection()
    {
        return section;
    }

    public void setToSectionHeader()
    {
        isSectionHeader = true;
    }

    public boolean isSectionHeader()
    {
        return isSectionHeader;
    }


    public int compareTo(Value other) {
        return this.section.compareTo(other.section);
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
