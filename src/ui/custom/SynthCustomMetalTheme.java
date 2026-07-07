/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.custom;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import ui.UIProperties;

/**
 *
 * @author Edward Jenkins
 */
public class SynthCustomMetalTheme extends MetalTheme {
    
    // instance variables
    private final ColorUIResource primary1;
    private final ColorUIResource primary2;
    private final ColorUIResource primary3;
    private final ColorUIResource secondary1;
    private final ColorUIResource secondary2;
    private final ColorUIResource secondary3;
    private final FontUIResource controlTextFont;
    private final FontUIResource systemTextFont;
    private final FontUIResource userTextFont;
    private final FontUIResource windowTitleFont;
    private final FontUIResource menuTextFont;
    private final FontUIResource subTextFont;
    
    public SynthCustomMetalTheme() {
        primary1 = new ColorUIResource(UIProperties.getHighlightColour());
        primary2 = new ColorUIResource(UIProperties.getWashedColour());
        primary3 = new ColorUIResource(UIProperties.getHeavilyWashedColour());
        secondary1 = new ColorUIResource(UIProperties.getShadowColour());
        secondary2 = new ColorUIResource(UIProperties.getWashedShadowColour());
        secondary3 = new ColorUIResource(UIProperties.getBackgroundColour());
        OceanTheme oceanTheme = new OceanTheme();
        controlTextFont = oceanTheme.getControlTextFont();
        systemTextFont = oceanTheme.getSystemTextFont();
        userTextFont = oceanTheme.getUserTextFont();
        windowTitleFont = oceanTheme.getWindowTitleFont();
        menuTextFont = oceanTheme.getMenuTextFont();
        subTextFont = oceanTheme.getSubTextFont();
    }

    @Override
    public String getName() {
        return "Synth Tracker Metal theme";
    }

    @Override
    protected ColorUIResource getPrimary1() {
        return primary1;
    }

    @Override
    protected ColorUIResource getPrimary2() {
        return primary2;
    }

    @Override
    protected ColorUIResource getPrimary3() {
        return primary3;
    }

    @Override
    protected ColorUIResource getSecondary1() {
        return secondary1;
    }

    @Override
    protected ColorUIResource getSecondary2() {
        return secondary2;
    }

    @Override
    protected ColorUIResource getSecondary3() {
        return secondary3;
    }

    @Override
    public FontUIResource getControlTextFont() {
        return controlTextFont;
    }

    @Override
    public FontUIResource getSystemTextFont() {
        return systemTextFont;
    }

    @Override
    public FontUIResource getUserTextFont() {
        return userTextFont;
    }

    @Override
    public FontUIResource getWindowTitleFont() {
        return windowTitleFont;
    }

    @Override
    public FontUIResource getMenuTextFont() {
        return menuTextFont;
    }

    @Override
    public FontUIResource getSubTextFont() {
        return subTextFont;
    }
}
