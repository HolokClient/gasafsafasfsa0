package digger.cmept.forum.module.impl.Render;

import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.impl.Combat.KillAura;
import digger.cmept.forum.forum;
import digger.cmept.forum.drag.comp.impl.DragTargetHUD;
import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.render.EventRender2D;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.module.impl.Util.NameProtect;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.utils.Helper;
import digger.cmept.forum.utils.math.RectHelper;
import digger.cmept.forum.utils.other.Particles;
import digger.cmept.forum.utils.render.ClientHelper;
import digger.cmept.forum.utils.render.RenderUtils;
import digger.cmept.forum.utils.render.RoundedUtil;
import digger.cmept.forum.utils.math.AnimationHelper;
import digger.cmept.forum.utils.math.MathematicHelper;
import digger.cmept.forum.utils.math.TimerHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class TargetHUD extends Module {
    private double scale = 0;
    private static EntityLivingBase curTarget = null;

    public static TimerHelper thudTimer = new TimerHelper();
    private float healthBarWidth;
    private ArrayList<Particles> particles = new ArrayList<>();
    public static ListSetting targetHudMode = new ListSetting("TargetHUD Mode", "Minced", () -> true, "Minced", "Celestial");

    public TargetHUD() {
        super("TargetHUD", ModuleCategory.Render);
        addSettings(targetHudMode);
    }
    @EventTarget
    public void onRender2D(EventRender2D e) {
        if (targetHudMode.currentMode.equals("Minced")) {
            DragTargetHUD dth = (DragTargetHUD) forum.instance.draggableHUD.getDraggableComponentByClass(DragTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(130);
            dth.setHeight(42 - 5);
            if (KillAura.target == null) {
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    curTarget = mc.player;
                    scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * forum.deltaTime()));
                } else {
                    scale = AnimationHelper.animation((float) scale, (float) 0, (float) (6 * forum.deltaTime()));
                }
            } else {
                curTarget = KillAura.target;
                scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * forum.deltaTime()));
            }
            if (curTarget != null) {
                try {
                    String bps = "" + calculateBPS();
                    GlStateManager.pushMatrix();
                    GlStateManager.resetColor();
                    GL11.glTranslated(x + 36, y + 26, 0);
                    GL11.glScaled(scale, scale, 0);
                    GL11.glTranslated(-(x + 36), -(y + 26), 0);
                    double healthWid = (curTarget.getHealth() / curTarget.getMaxHealth() * 88);
                    healthWid = MathHelper.clamp(healthWid, 0.0D, 88);
                    healthBarWidth = AnimationHelper.animation(healthBarWidth, (float) healthWid, (float) (10 * forum.deltaTime()));
                    String health = "" + MathematicHelper.round(curTarget.getHealth(), 1);
                    String distance = "" + MathematicHelper.round(mc.player.getDistanceToEntity(curTarget), 1);

                    RenderUtils.drawRect4(x, y, x + dth.getWidth() - 30, y + dth.getHeight(), new Color(0, 0, 0, 140).getRGB());
                    RenderUtils.drawSmoothRect(x + 5, y + 30, x + 5 + healthBarWidth, y + 29 + 2,  new Color(46, 250, 0).getRGB());
                    RectHelper.drawSmoothRectBetter(x + 5, y + 33,  88, 1,  new Color(0, 91, 255).getRGB());

                    mc.rubik_16.drawString(forum.instance.featureManager.getFeature(NameProtect.class).isEnabled() && NameProtect.otherName.getCurrentValue() ? "Protected" : curTarget.getName(), x + 32, y + 5, -1);
                    mc.rubik_14.drawString("Health: " + health, x + 32, y + 14, -1);
                    mc.rubik_14.drawString("Distance: " + distance, x + 32, y + 21, -1);

                    healthBarWidth = AnimationHelper.getAnimationState((float) healthBarWidth, (float) healthWid, (float) (10 * forum.deltaTime()));
                    for (NetworkPlayerInfo targetHead : mc.player.connection.getPlayerInfoMap()) {
                        try {
                            if (mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) == curTarget && curTarget instanceof EntityPlayer) {
                                mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                                float hurtPercent = getHurtPercent(curTarget);
                                GL11.glPushMatrix();
                                GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
                                Gui.drawScaledCustomSizeModalRect((int) x + 4, y + 2, 8.0f, 8.0f, 8, 8, 25, 25, 64.0f, 64.0f);
                                GL11.glPopMatrix();
                                GlStateManager.bindTexture(0);
                            }
                        } catch (Exception exception) {
                        }
                    }

                } catch (Exception exception) {
                } finally {
                    GlStateManager.popMatrix();
                }


            }
        } else if (targetHudMode.currentMode.equals("Celestial")) {
            DragTargetHUD dth = (DragTargetHUD) forum.instance.draggableHUD.getDraggableComponentByClass(DragTargetHUD.class);
            float x = dth.getX();
            float y = dth.getY();
            dth.setWidth(130);
            dth.setHeight(42 - 5);
            if (KillAura.target == null) {
                if (mc.player != null && mc.currentScreen instanceof GuiChat) {
                    curTarget = mc.player;
                    scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * forum.deltaTime()));
                } else {
                    scale = AnimationHelper.animation((float) scale, (float) 0, (float) (6 * forum.deltaTime()));
                }
            } else {
                curTarget = KillAura.target;
                scale = AnimationHelper.animation((float) scale, (float) 1, (float) (6 * forum.deltaTime()));
            }
            if (curTarget != null) {
                try {
                    GlStateManager.pushMatrix();
                    GlStateManager.resetColor();
                    GL11.glTranslated(x + 36, y + 26, 0);
                    GL11.glScaled(scale, scale, 0);
                    GL11.glTranslated(-(x + 36), -(y + 26), 0);
                    double healthWid = (curTarget.getHealth() / curTarget.getMaxHealth() * 80);
                    healthWid = MathHelper.clamp(healthWid, 0.0D, 80);
                    healthBarWidth = AnimationHelper.animation(healthBarWidth, (float) healthWid, (float) (5 * forum.deltaTime()));
                    String health = "" + MathematicHelper.round(curTarget.getHealth(), 1);
                    RoundedUtil.drawRound(x, y, 125, 35, 1, new Color(0, 0, 0, 220));
                    RenderUtils.drawGradientSideways(x + 38, y + 15, x + 38 +  healthBarWidth, y + 15 + 8.3f,  ClientHelper.getClientColor(15, 15, 20).getRGB(), new Color(255,255,255).getRGB());
                    mc.mntsb_15.drawStringWithShadow(curTarget.getHealth() >= 3 ? health : "", x + 67, y + 17, new Color(255, 255, 255).getRGB());
                    mc.mntsb_17.drawString(forum.instance.featureManager.getFeature(NameProtect.class).isEnabled() && NameProtect.otherName.getCurrentValue() ? "Protected" : curTarget.getName(), x + 40, y + 5, new Color(255, 255, 255, 255).getRGB());
                    healthBarWidth = AnimationHelper.animation((float) healthBarWidth, (float) healthWid, (float) (10 * forum.deltaTime()));
                    for (NetworkPlayerInfo targetHead : mc.player.connection.getPlayerInfoMap()) {
                        try {
                            if (mc.world.getPlayerEntityByUUID(targetHead.getGameProfile().getId()) == curTarget && curTarget instanceof EntityPlayer) {
                                mc.getTextureManager().bindTexture(targetHead.getLocationSkin());
                                float hurtPercent = getHurtPercent(curTarget);
                                GL11.glPushMatrix();
                                GL11.glColor4f(1, 1 - hurtPercent, 1 - hurtPercent, 1);
                                Gui.drawScaledCustomSizeModalRect((int) x, y, 8.0f, 8.0f, 8, 8, 35, 35, 64.0f, 64.0f);
                                GL11.glPopMatrix();
                                GlStateManager.bindTexture(0);
                            }
                        } catch (Exception exception) {
                        }
                    }

                } catch (Exception exception) {
                } finally {
                    GlStateManager.popMatrix();
                }

            }
        }
    }
    public static float getRenderHurtTime(EntityLivingBase hurt) {
        return (float) hurt.hurtTime - (hurt.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f);
    }

    public static float getHurtPercent(EntityLivingBase hurt) {
        return getRenderHurtTime(hurt) / (float) 10;
    }
    private double calculateBPS() {
        double bps = (Math.hypot(Helper.mc.player.posX - Helper.mc.player.prevPosX, Helper.mc.player.posZ - Helper.mc.player.prevPosZ) * Helper.mc.timer.timerSpeed) * 20;
        return Math.round(bps * 100.0) / 100.0;
    }
    @Override
    public void onEnable() {
        if (this.mc.gameSettings.ofFastRender) {
            this.mc.gameSettings.ofFastRender = false;
        }
        super.onEnable();
    }
}
