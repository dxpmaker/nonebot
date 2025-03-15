package com.dxpmaker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoBan {
    private String reg1;
    private String reg2;
    private String reg3;
    AutoBan(){
        reg1 = "[一两二三四五六七八九十个半\\d]+?[秒分时小周月smhd]";
        reg2 = "";
        reg3 = "";
    }
    public int StrToIntTime(String Msg){
        ArrayList<String> TimeList = regular(reg1,Msg);
        return  10;
    }
    private ArrayList<String> regular(String str, String regstr) {
        ArrayList<String> datelist = new ArrayList<String>();
        Matcher put = Pattern.compile(regstr).matcher(str);
        for(int i = 0;put.find(i);i = put.start()+put.group(0).length()) {
            datelist.add(put.group(0));
        }
        return datelist;
    }
}
