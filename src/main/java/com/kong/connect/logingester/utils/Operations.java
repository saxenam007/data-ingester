package com.kong.connect.logingester.utils;

public enum Operations {

    CREATE ("c"),
    UPDATE("u"),
    DELETE("d"),
    READ("r"),
    TRUNCATE("t"),
    MESSAGE("m");


    private final String value ;
    Operations (String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
