package digger.cmept.forum.module.impl.Movement;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.packet.EventSendPacket;
import digger.cmept.forum.event.events.impl.player.EventUpdate;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.Helper;

import net.minecraft.network.play.client.CPacketPlayer;

public class NoSlowDown extends Module {
    public static NumberSetting percentage = new NumberSetting("Percentage", 100, 0, 100, 1, () -> true, NumberSetting.NumberType.PERCENTAGE);
    private final ListSetting noSlowMode = new ListSetting("NoSlow Mode", "Matrix", () -> true, "Vanilla", "Matrix");
    public int usingTicks;

    public NoSlowDown() {
        super("NoSlowDown", "Позволяет есть на ходу", ModuleCategory.Movement);
        addSettings(noSlowMode, percentage);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket eventSendPacket) {
        CPacketPlayer packet = (CPacketPlayer) eventSendPacket.getPacket();
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        usingTicks = Helper.mc.player.isUsingItem() ? ++usingTicks : 0;
        if (!this.isEnabled() || !Helper.mc.player.isUsingItem()) {
            return;
        }
        if (noSlowMode.currentMode.equals("Matrix")) {
            if (Helper.mc.player.isUsingItem()) {
                if (Helper.mc.player.onGround && !Helper.mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (Helper.mc.player.ticksExisted % 2 == 0) {
                        Helper.mc.player.motionX *= 0.35;
                        Helper.mc.player.motionZ *= 0.35;
                    }
                } else if (Helper.mc.player.fallDistance > 0.2) {
                    Helper.mc.player.motionX *= 0.9100000262260437;
                    Helper.mc.player.motionZ *= 0.9100000262260437;
                }
            }
        }
    }
}