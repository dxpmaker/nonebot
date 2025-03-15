package com.dxpmaker;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.NudgeEvent;

public class nudge extends SimpleListenerHost {
    @EventHandler
    private ListeningStatus onEvent(NudgeEvent event) {
        //nudge.getSubject().sendMessage(nudge.getTarget().getId()+" "+nudge.getBot().getId()+" "+nudge.getFrom().getId());
        if (event.getTarget().getId() == event.getBot().getId() && event.getTarget().getId() != event.getFrom().getId()){
            event.getFrom().nudge().sendTo(event.getSubject());
        }
        return ListeningStatus.LISTENING;
    }
}
