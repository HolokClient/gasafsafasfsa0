package digger.cmept.forum.module.impl.Render;

import digger.cmept.forum.event.EventTarget;
import digger.cmept.forum.event.events.impl.render.EventFogColor;
import digger.cmept.forum.module.Module;
import digger.cmept.forum.module.ModuleCategory;
import digger.cmept.forum.ui.settings.impl.ColorSetting;
import digger.cmept.forum.ui.settings.impl.ListSetting;
import digger.cmept.forum.ui.settings.impl.NumberSetting;
import digger.cmept.forum.utils.render.ClientHelper;
import digger.cmept.forum.utils.render.ColorUtils;

import java.awt.*;

public class FogColor extends Module {
    public ListSetting colorMode = new ListSetting("Fog Color", "Rainbow", () -> true, "Rainbow", "Client", "Custom");
    public static NumberSetting distance;
    public ColorSetting customColor;

    public FogColor() {
        super("FogColor", "Изменяет цвет тумана", ModuleCategory.Render);
        distance = new NumberSetting("Fog Distance", 0.7f, 0.1f, 4.0f, 0.01f, () -> true);
        customColor = new ColorSetting("Custom Fog", new Color(0xFFFFFF).getRGB(), () -> colorMode.currentMode.equals("Custom"));
        addSettings(colorMode, distance, customColor);
    }

    @EventTarget
    public void onFogColor(EventFogColor event) {
        String colorModeValue = colorMode.getOptions();
        if (colorModeValue.equalsIgnoreCase("Rainbow")) {
            Color color = ColorUtils.rainbow(1, 1.0f, 1.0f);
            event.setRed(color.getRed());
            event.setGreen(color.getGreen());
            event.setBlue(color.getBlue());
        } else if (colorModeValue.equalsIgnoreCase("Client")) {
            Color clientColor = ClientHelper.getClientColor();
            event.setRed(clientColor.getRed());
            event.setGreen(clientColor.getGreen());
            event.setBlue(clientColor.getBlue());
        } else if (colorModeValue.equalsIgnoreCase("Custom")) {
            Color customColorValue = new Color(customColor.getColorValue());
            event.setRed(customColorValue.getRed());
            event.setGreen(customColorValue.getGreen());
            event.setBlue(customColorValue.getBlue());
        }
    }
}
