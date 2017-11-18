package com.mycompany.myapp.web.rest.util;

/**
 * Created by Administrator on 2017/11/16.
 */
public class SearchConditionUtil {
    public static String GetAddConditon(String search){
        if(search==null || search.isEmpty()){
            search=" deleted==0";
        }
        else{
            search+=";deleted==0";
        }
        return search;
    }
}
