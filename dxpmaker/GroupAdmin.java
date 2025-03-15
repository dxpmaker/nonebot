package com.dxpmaker;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

public final class GroupAdmin extends JavaPlugin {
    public static final GroupAdmin INSTANCE = new GroupAdmin();

    private GroupAdmin() {
        super(new JvmPluginDescriptionBuilder("com.dxpmaker.group-admin", "0.1.0")
                .name("group-admin")
                .author("dxpmaker")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin loaded!");
        GlobalEventChannel.INSTANCE.registerListenerHost(new group());
        GlobalEventChannel.INSTANCE.registerListenerHost(new all());
        GlobalEventChannel.INSTANCE.registerListenerHost(new nudge());

    }
}