package ru.dedclient.util;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtil {

    /** Прямоугольник со скруглёнными углами (radius в пикселях). */
    public static void drawRoundedRect(float x, float y, float w, float h, float radius, int color) {
        float a = (color >> 24 & 255) / 255f;
        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(r, g, b, a);

        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i <= 90; i++) {
            double angle = Math.toRadians(i);
            GL11.glVertex2d(x + radius + Math.sin(angle) * radius,
                            y + radius + Math.cos(angle) * radius);
        }
        GL11.glEnd();
        // Прямые части (верх/низ/лево/право) — упрощённо через 4 прямоугольника
        Gui.drawRect((int)(x + radius), (int) y, (int)(x + w - radius), (int)(y + h), color);
        Gui.drawRect((int) x, (int)(y + radius), (int)(x + radius), (int)(y + h - radius), color);
        Gui.drawRect((int)(x + w - radius), (int)(y + radius), (int)(x + w), (int)(y + h - radius), color);
        // Углы
        drawCorner(x + radius, y + radius, radius, 180, 270, r, g, b, a);
        drawCorner(x + w - radius, y + radius, radius, 270, 360, r, g, b, a);
        drawCorner(x + w - radius, y + h - radius, radius, 0, 90, r, g, b, a);
        drawCorner(x + radius, y + h - radius, radius, 90, 180, r, g, b, a);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static void drawCorner(float cx, float cy, float r, int start, int end, float red, float green, float blue, float alpha) {
        GlStateManager.color(red, green, blue, alpha);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2d(cx, cy);
        for (int i = start; i <= end; i++) {
            double a = Math.toRadians(i);
            GL11.glVertex2d(cx + Math.cos(a) * r, cy + Math.sin(a) * r);
        }
        GL11.glEnd();
    }

    /** Вертикальный градиент внутри прямоугольника. */
    public static void drawGradientRect(int x, int y, int w, int h, int topColor, int bottomColor) {
        float a1 = (topColor >> 24 & 255) / 255f;
        float r1 = (topColor >> 16 & 255) / 255f;
        float g1 = (topColor >> 8 & 255) / 255f;
        float b1 = (topColor & 255) / 255f;
        float a2 = (bottomColor >> 24 & 255) / 255f;
        float r2 = (bottomColor >> 16 & 255) / 255f;
        float g2 = (bottomColor >> 8 & 255) / 255f;
        float b2 = (bottomColor & 255) / 255f;

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4f(r1, g1, b1, a1); GL11.glVertex2f(x, y);
        GL11.glColor4f(r2, g2, b2, a2); GL11.glVertex2f(x, y + h);
        GL11.glColor4f(r2, g2, b2, a2); GL11.glVertex2f(x + w, y + h);
        GL11.glColor4f(r1, g1, b1, a1); GL11.glVertex2f(x + w, y);
        GL11.glEnd();

        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    /** Полупрозрачная тень под панелью. */
    public static void drawShadow(int x, int y, int w, int h, int spread) {
        for (int i = spread; i > 0; i--) {
            int alpha = (int)(35 * (1f - (float) i / spread));
            Gui.drawRect(x - i, y - i, x + w + i, y + h + i,
                    (alpha << 24) | 0x000000);
        }
    }
}
