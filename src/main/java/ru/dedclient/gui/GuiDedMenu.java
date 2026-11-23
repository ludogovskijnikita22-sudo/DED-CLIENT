package ru.dedclient.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import ru.dedclient.util.RenderUtil;

import java.awt.Color;
import java.io.IOException;

public class GuiDedMenu extends GuiScreen {

    private static final int PANEL_W = 300;
    private static final int PANEL_H = 210;
    private static final int RADIUS  = 8;

    private static final int ACCENT     = new Color(255, 60, 60).getRGB();
    private static final int PANEL_TOP  = new Color(25, 25, 32, 245).getRGB();
    private static final int PANEL_BOT  = new Color(12, 12, 18, 245).getRGB();
    private static final int HEADER_TOP = new Color(255, 60, 60).getRGB();
    private static final int HEADER_BOT = new Color(180, 30, 30).getRGB();
    private static final int TEXT       = 0xFFFFFF;
    private static final int TEXT_DIM   = 0xAAAAAA;

    private long openTime;

    @Override
    public void initGui() {
        openTime = System.currentTimeMillis();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        // Плавное появление (0..1 за 180 мс)
        float progress = Math.min(1f, (System.currentTimeMillis() - openTime) / 180f);
        float scale = 0.92f + 0.08f * progress;

        int cx = width / 2;
        int cy = height / 2;

        GlStateManager.pushMatrix();
        GlStateManager.translate(cx, cy, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-cx, -cy, 0);

        int x = cx - PANEL_W / 2;
        int y = cy - PANEL_H / 2;

        // Тень
        RenderUtil.drawShadow(x, y, PANEL_W, PANEL_H, 6);

        // Панель с градиентом
        RenderUtil.drawGradientRect(x, y, PANEL_W, PANEL_H, PANEL_TOP, PANEL_BOT);
        RenderUtil.drawRoundedRect(x, y, PANEL_W, PANEL_H, RADIUS, PANEL_TOP);

        // Шапка
        RenderUtil.drawGradientRect(x, y, PANEL_W, 34, HEADER_TOP, HEADER_BOT);
        fontRenderer.drawStringWithShadow("DED CLIENT", x + 14, y + 12, TEXT);
        fontRenderer.drawStringWithShadow("v0.1", x + PANEL_W - 34, y + 12, TEXT_DIM);

        // Пункты меню
        String[] items = { "Combat", "Movement", "Render", "Misc", "Settings" };
        int iy = y + 48;
        for (String item : items) {
            boolean hovered = mouseX >= x + 12 && mouseX <= x + PANEL_W - 12
                    && mouseY >= iy && mouseY <= iy + 20;
            if (hovered) {
                RenderUtil.drawRoundedRect(x + 12, iy, PANEL_W - 24, 20, 4,
                        new Color(255, 60, 60, 70).getRGB());
                RenderUtil.drawRoundedRect(x + 12, iy, 3, 20, 1, ACCENT);
            }
            fontRenderer.drawStringWithShadow(item, x + 22, iy + 6, hovered ? ACCENT : TEXT);
            iy += 24;
        }

        fontRenderer.drawStringWithShadow("Right Shift - открыть/закрыть", x + 14, y + PANEL_H - 18, TEXT_DIM);

        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
