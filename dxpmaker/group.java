package com.dxpmaker;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;

/**
 * 该部分仅提供群聊操作
 */
public class group extends SimpleListenerHost {
    function fun;
    group(){
        fun = new function();
    }
    @EventHandler
    private ListeningStatus onEvent(GroupMessageEvent event){
        String Msg = event.getMessage().contentToString();
        if (Msg.equals("随机禁言")||Msg.equals("申请随机禁言")){
            //event.getSender().nudge().sendTo(event.getGroup());
            if(event.getPermission().toString().equals("MEMBER"))event.getGroup().get(event.getSender().getId()).mute(this.fun.RanTime());//如果发起者为群员则触发
        }else if(Msg.equals("ATme")){
            At a = new At(event.getSender().getId());
            event.getGroup().sendMessage(a);
        }else if(Msg.equals("demo")){
            event.getGroup().sendMessage("fun.Luck()");
        }else if(Msg.matches("申请禁言[\\u4e00-\\u9fa5|\\d|a-z]+")){
            //fun.();
        }
//        }else if (str.equals("测一测")){
//
//        }

        return ListeningStatus.LISTENING;
    }
}
