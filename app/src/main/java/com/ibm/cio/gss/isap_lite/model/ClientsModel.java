package com.ibm.cio.gss.isap_lite.model;

/*
Class Name : "ClientsModel"
Description :"Model object to hold initial client information with section."
Author      :"Kabuli Behera"
Date of Creation :"March 05 2018"
@Copyright :IBM India Pvt Ltd.
Reviewer Name :  ""
Reviwer comments :""
Review Date :""

*/

public class ClientsModel {
    private Value[] value;

    private String section;

    public Value[] getValue ()
    {
        return value;
    }

    public void setValue (Value[] value)
    {
        this.value = value;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [value = "+value+", section = "+section+"]";
    }
}
