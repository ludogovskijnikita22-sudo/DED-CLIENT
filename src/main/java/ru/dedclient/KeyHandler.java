package ru.dedclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import ru.dedclient.gui.GuiDedMenu;

public class KeyHandler {

    public static final KeyBinding OPEN_MENU =
            new KeyBinding("DED Client Menu", Keyboard.KEY_RSHIFT, "DED Client");

    public void init() {
        ClientRegistry.registerKeyBinding(OPEN_MENU);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (OPEN_MENU.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiDedMenu());
        }
    }
}
