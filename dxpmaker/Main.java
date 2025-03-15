package com.dxpmaker;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;

public class Main extends SimpleListenerHost {
    @EventHandler
    private ListeningStatus onEvent(FriendMessageEvent event){
        String str = event.getMessage().contentToString();
        if (str.equals("demo")){
            event.getSender().sendMessage("OK");
        }
        return ListeningStatus.LISTENING;
    }



}
