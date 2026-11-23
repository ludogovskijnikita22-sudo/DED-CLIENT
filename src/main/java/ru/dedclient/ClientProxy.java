package ru.dedclient;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import ru.dedclient.gui.Watermark;

public class ClientProxy {

    public void init(FMLInitializationEvent event) {
        KeyHandler keyHandler = new KeyHandler();
        keyHandler.init();
        MinecraftForge.EVENT_BUS.register(keyHandler);
        MinecraftForge.EVENT_BUS.register(new Watermark());
    }
}
