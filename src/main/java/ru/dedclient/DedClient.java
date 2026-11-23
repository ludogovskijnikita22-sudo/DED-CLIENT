package ru.dedclient;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "dedclient", name = "DED CLIENT", version = "1.0.0",
     acceptedMinecraftVersions = "[1.12.2]", clientSideOnly = true)
public class DedClient {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            new ClientProxy().init(event);
        }
    }
}
