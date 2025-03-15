package com.dxpmaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 星座类操作
 */
class StarSign extends Url{
    /**
     * 测试行切记删除
     * @param args null
     */
    String[] UrlList;//数据Url地址
    String[] StarSingDate; //输出文本
    String[] out;//输出数据格式
    String[] StarSing;
    SimpleDateFormat dateform;
    String DateNew;
    Random ran;
    long time;
    StarSign(){
        UrlList = new String[]{
                "https://www.xzw.com/fortune/aries/",
                "https://www.xzw.com/fortune/taurus/",
                "https://www.xzw.com/fortune/gemini/",
                "https://www.xzw.com/fortune/cancer/",
                "https://www.xzw.com/fortune/leo/",
                "https://www.xzw.com/fortune/virgo/",
                "https://www.xzw.com/fortune/libra/",
                "https://www.xzw.com/fortune/scorpio/",
                "https://www.xzw.com/fortune/sagittarius/",
                "https://www.xzw.com/fortune/capricorn/",
                "https://www.xzw.com/fortune/aquarius/",
                "https://www.xzw.com/fortune/pisces/"
        };
        out = new String[] {
                "综合运势：",
                "爱情运势：",
                "事业学业：",
                "财富运势：",
                "健康指数：",
                "商谈指数：",
                "幸运颜色：",
                "幸运数字：",
                "速配星座：",
                "短评："
        };
        StarSing = new String[] {
                "白羊座",
                "金牛座",
                "双子座",
                "巨蟹座",
                "狮子座",
                "处女座",
                "天秤座",
                "天蝎座",
                "射手座",
                "摩羯座",
                "水瓶座",
                "双鱼座"
        };
        StarSingDate = new String[12];
        dateform = new SimpleDateFormat("yyyy-MM-dd");
        DateNew = dateform.format(new Date());
        ran = new Random();
        time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(this.dateform.format(new Date())+" 00:00:00", new ParsePosition(0)).getTime()/1000;
        NewStarSign();//启动后执行一次

    }
    private void NewTime(){
        synchronized(this){//进程锁更新时不允许其他访问
            if(!DateNew.equals(dateform.format(new Date()))) {//时间对不上时开启更新
                DateNew = dateform.format(new Date());
                time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(this.dateform.format(new Date())+" 00:00:00", new ParsePosition(0)).getTime()/1000;
                NewStarSign();
            }else {
                return ;
            }
        }
    }
    public String Luck (long QQID){
        if(!DateNew.equals(dateform.format(new Date()))) {//时间对不上时开启更新
            NewTime();
        }
        ran.setSeed(time +QQID);
        return ("今日运气指数为"+ran.nextInt(1000)+" 悲剧指数为"+ran.nextInt(10000)/10);
    }

    /**
     * 输入消息并返回其是否正确的星座数据
     * @param Msg QQ消息
     * @return 星座数据或者错误消息
     */
    public String MsgToStr(String Msg) {
        if(!DateNew.equals(dateform.format(new Date()))) {//时间对不上时开启更新
            NewTime();
        }
        for(int i=0 ;i<12;i++) {
            if(Msg.matches(StarSing[i]+"(今日)?运势"))return Msg + "\n" + StarSingDate[i];
        }
        return "没有这个星座哦";
    }
    /**
     * 输入中文星座返回对应的数据地址
     * @param str 星座文本
     * @return 数据地址 -1为不存在
     */
    public int StringToInt(String str) {
        for(int i = 0;i<12;i++) {
            if(str.equals(StarSing[i]))return i;
        }
        return -1;
    }
    /**
     * 更新所有星座数据
     */
    public void NewStarSign(){
        String str[] = new String[UrlList.length];
        for (int i=0;i<UrlList.length;i++){
            for (int j=0;j<3;j++){
                try {
                    str[i] = GetNewStarSign(i);
                    StarSingDate[i] = str[i] ;
                    break;
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (str[i]==null)throw new RuntimeException();
        }
    }
    /**
     * 更新指定的星座数据
     * @param str 星座
     */
    public void NewStarSign(String str){
        int i = StringToInt(str);
        try {
            StarSingDate[i] = GetNewStarSign(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String GetNewStarSign(int i) throws IOException{
        return LuckUrlToStr(get(UrlList[i]));
    }


    /**
     * 输入网页流返回可输出的字符串
     **/
    private String LuckUrlToStr(String str) {
        ArrayList<String> datelist = regular(str,"<li( class=\"desc\")?><label>[\\s\\S]+?</li>");
        StringBuilder PutStr = new StringBuilder();
        for(int i = 0;i<4;i++) {
            PutStr.append(out[i]+ToXin(Tonum(datelist.get(i))/16)+"\n");
        }
        for(int i = 4;i<datelist.size()-1;i++) {
            PutStr.append(out[i]+regular(datelist.get(i),"(?<=</label>)[\\s\\S]+?(?=</li>)").get(0)+"\n");
        }
        PutStr.append(out[out.length-1]+regular(datelist.get(datelist.size()-1),"(?<=</label>).+?(?=</li>)").get(0));//????服务器上不明不可用
        //System.out.println(PutStr.toString());
        return PutStr.toString();
    }
    /**
     * 正则 文本切除
     * @param str 要处理的文本
     * @param regstr 正则表达式
     * @return 匹配子项列表
     */
    private  ArrayList<String> regular(String str,String regstr) {
        ArrayList<String> datelist = new ArrayList<String>();
        Pattern rang = Pattern.compile(regstr);
        Matcher put = rang.matcher(str);
        for(int i = 0;put.find(i);i = put.start()+1) {
            datelist.add(put.group(0));
        }
        return datelist;
    }
    private String ToXin(int n) {
        StringBuilder PutStr = new StringBuilder();
        for(int i = 0; i<n;i++) {
            PutStr.append("★");
        }
        for(int i = 0; i<5-n;i++) {
            PutStr.append("☆");
        }
        return PutStr.toString();
    }
    private int Tonum(String str) {
        return Integer.parseInt(regular(str,"\\d+").get(0));
    }
}
class Url {
    /**
     * @param url 访问地址
     * @return StringBuffer 网页数据流
     * @throws IOException 访问失败
     */
    protected String get(String url) throws IOException {
        URL Url = new URL(url);
        HttpURLConnection con = (HttpURLConnection) Url.openConnection();
        con.setRequestMethod("GET");
        if(con.getResponseCode() != 200)return "";
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content.toString();
    }
}

