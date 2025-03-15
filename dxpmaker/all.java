package com.dxpmaker;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 该部分提供公共操作
 */
public class all extends SimpleListenerHost {
    function fun;
    all(){
        fun = new function();
    }
    @EventHandler
    private ListeningStatus onEvent(MessageEvent event){

        String Msg = event.getMessage().contentToString();
        if (Msg.equals("欧非鉴定")){
            event.getSubject().sendMessage(fun.Luck(event.getSender().getId()));
        }/*else if(Msg.equals("戳戳我")){//陌生人不可用
            event.getSender().nudge().sendTo(event.getSender());
        }*/else if(Msg.matches(".+?座(今日)?运势$")){
           event.getSubject().sendMessage(fun.StarSign(Msg));
        }
        return ListeningStatus.LISTENING;
    }
}
