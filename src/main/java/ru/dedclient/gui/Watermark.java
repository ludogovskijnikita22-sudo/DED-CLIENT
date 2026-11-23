package ru.dedclient.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.dedclient.util.RenderUtil;

import java.awt.Color;

public class Watermark {

    private static final String TEXT = "DED CLIENT";
    private static final int ACCENT = new Color(255, 60, 60).getRGB();
    private static final int BG_TOP = new Color(25, 25, 32, 220).getRGB();
    private static final int BG_BOT = new Color(12, 12, 18, 220).getRGB();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRenderer;
        ScaledResolution sr = new ScaledResolution(mc);

        String fps = " | " + Minecraft.getDebugFPS() + " fps";
        int w = fr.getStringWidth(TEXT + fps) + 26;
        int h = fr.FONT_HEIGHT + 10;
        int x = 10;
        int y = 10;

        RenderUtil.drawShadow(x, y, w, h, 4);
        RenderUtil.drawGradientRect(x, y, w, h, BG_TOP, BG_BOT);
        RenderUtil.drawRoundedRect(x, y, w, h, 4, BG_TOP);
        RenderUtil.drawRoundedRect(x, y, 3, h, 1, ACCENT);

        fr.drawStringWithShadow(TEXT, x + 12, y + 5, 0xFFFFFF);
        fr.drawStringWithShadow(fps, x + 12 + fr.getStringWidth(TEXT), y + 5,
                new Color(180, 180, 180).getRGB());
    }
}
