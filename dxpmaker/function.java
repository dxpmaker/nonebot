package com.dxpmaker;
import java.sql.SQLData;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
public class function{

    Random ran;
    SimpleDateFormat df;
    StarSign Sing;
    AutoBan ban;
    function(){
        this.ran = new Random();
        this.df = new SimpleDateFormat("yyyy-MM-dd");
        this.Sing = new StarSign();
        this.ban = new AutoBan();
    }

    /**
     *
     * @param QQID QQ号
     * @return QQMsg
     */
    public String Luck(long QQID){//不明错误
        return Sing.Luck(QQID);
    }

    /**
     * 随机禁言
     * @return 禁言时间长度/秒
     */
    public int RanTime(){
        return (this.ran.nextInt(30*24-1)+1)*(this.ran.nextInt(60*60-1)+1);
    }
    public String StarSign(String Msg){
        return Sing.MsgToStr(Msg);
    }
}

