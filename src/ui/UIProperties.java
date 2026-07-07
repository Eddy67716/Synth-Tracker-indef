/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.UIManager;
import settings.format.UiSettingsData;
import static ui.controllers.MainController.SETTINGS_DATA;

/**
 *
 * @author Edward Jenkins
 */
public class UIProperties {

    public static final Dimension VALUE_SPINNER_SIZE = new Dimension(45, 20);
    public static final Dimension DECIMAL_SPINNER_SIZE = new Dimension(60, 20);
    public static final Dimension C5_SPINNER_SIZE = new Dimension(80, 20);
    public static final Dimension DETAILS_FIELD_SIZE = new Dimension(198, 20);
    public static final Dimension LARGE_FIELD_SIZE = new Dimension(150, 20);
    public static final Dimension MEDIUM_TALL_FIELD_SIZE
            = new Dimension(120, 30);
    public static final Dimension MEDIUM_FIELD_SIZE = new Dimension(120, 20);
    public static final Dimension SMALLER_FIELD_SIZE = new Dimension(90, 20);
    public static final Dimension SMALL_LABEL_SIZE = new Dimension(100, 22);
    public static final Dimension MEDIUM_LABEL_SIZE = new Dimension(120, 22);
    public static final Dimension LARGE_LABEL_SIZE = new Dimension(150, 22);
    public static final Dimension NOTE_COMBO_BOX_SIZE = new Dimension(80, 25);
    public static final Insets DEF_INSETS = new Insets(5, 5, 0, 0);
    public static final Insets SINGLE_COLUMN_INSETS = new Insets(5, 5, 0, 5);
    public static final Insets CHECKBOX_INSETS = new Insets(5, 8, 0, 0);
    public static final Font DEF_FONT = new Font("Default font", 0, 12);
    public static final Font BOLD_FONT = new Font("Bold font", 1, 12);
    public static final Font ITALIC_FONT = new Font("Italic font", 2, 12);
    public static final String[] SAMPLE_NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "A#", "B_"};
    public static final Color DEF_THUMB_COLOUR = new Color(128, 128, 128);
    public static final Color TOOL_TIP_COLOUR = new Color(255, 233, 152);

    // static variables
    private static Color forgroundColour;
    private static Color backgroundColour;
    private static Color tableBackgroundColour;
    private static Color textColour;
    private static Color highlightColour;
    private static Color washedColour;
    private static Color heavilyWashedColour;
    private static Color extremelyWashedColour;
    private static Color shadowColour;
    private static Color darkShadowColour;
    private static Color washedShadowColour;

    public static Color getForgroundColour() {
        return forgroundColour;
    }

    public static Color getBackgroundColour() {
        return backgroundColour;
    }

    public static Color getTableBackgroundColour() {
        return tableBackgroundColour;
    }

    public static Color getTextColour() {
        return textColour;
    }

    public static Color getHighlightColour() {
        return highlightColour;
    }

    public static Color getWashedColour() {
        return washedColour;
    }

    public static Color getHeavilyWashedColour() {
        return heavilyWashedColour;
    }

    public static Color getExtremelyWashedColour() {
        return extremelyWashedColour;
    }

    public static Color getShadowColour() {
        return shadowColour;
    }

    public static Color getDarkShadowColour() {
        return darkShadowColour;
    }

    public static Color getWashedShadowColour() {
        return washedShadowColour;
    }
    
    

    public static void setUIProperties() {

        UiSettingsData uiData = SETTINGS_DATA.getUiSettingsData();

        // setup colours from settings
        forgroundColour = uiData.getMainColour();

        backgroundColour = new Color((forgroundColour.getRed() * 7 >>> 3) 
                + (0x80 >>> 3),
                (forgroundColour.getGreen() * 7 >>> 3) 
                + (0x80 >>> 3),
                (forgroundColour.getBlue() * 7 >>> 3) 
                + (0x80 >>> 3));

        tableBackgroundColour = forgroundColour;

        textColour = uiData.getTextColour();

        highlightColour = uiData.getHighlightColour();

        washedColour = averageColours(highlightColour, forgroundColour);

        heavilyWashedColour = averageColours(washedColour, forgroundColour);

        extremelyWashedColour = averageColours(heavilyWashedColour, 
                forgroundColour);
        
        shadowColour = highlightColour.darker();
        
        darkShadowColour = shadowColour.darker();
        
        washedShadowColour = averageColours(washedColour, forgroundColour);

        // set default UI colours
        // backgrounds
        UIManager.put("MenuBar.background", backgroundColour);
        UIManager.put("Panel.background", backgroundColour);
        UIManager.put("ToolBar.background", backgroundColour);
        UIManager.put("Label.background", backgroundColour);
        UIManager.put("Panel.background", backgroundColour);
        UIManager.put("Slider.background", backgroundColour);
        UIManager.put("RadioButton.background", backgroundColour);
        UIManager.put("CheckBox.background", backgroundColour);
        UIManager.put("ComboBox.background", backgroundColour);
        UIManager.put("TabbedPane.background", backgroundColour);
        UIManager.put("ToolTip.background", TOOL_TIP_COLOUR);
        UIManager.put("OptionPane.background", backgroundColour);
        UIManager.put("Button.background", heavilyWashedColour);
        UIManager.put("ToggleButton.background", heavilyWashedColour);
        UIManager.put("ScrollBar.background", backgroundColour);

        // foregrounds
        UIManager.put("MenuBar.foreground", textColour);
        UIManager.put("Panel.foreground", textColour);
        UIManager.put("ToolBar.foreground", textColour);
        UIManager.put("Label.foreground", textColour);
        UIManager.put("TextField.foreground", textColour);
        UIManager.put("Panel.foreground", textColour);
        UIManager.put("Slider.foreground", heavilyWashedColour);
        UIManager.put("RadioButton.foreground", textColour);
        UIManager.put("CheckBox.foreground", textColour);
        UIManager.put("ComboBox.foreground", textColour);
        UIManager.put("TabbedPane.foreground", textColour);
        UIManager.put("ToolTip.foreground", textColour);
        UIManager.put("OptionPane.foreground", textColour);
        UIManager.put("Button.foreground", textColour);
        UIManager.put("ToggleButton.foreground", textColour);
        UIManager.put("ScrollBar.foreground", textColour);

        // texts
        // focus
        UIManager.put("ToolBar.highlight", heavilyWashedColour);
        UIManager.put("Slider.hilight", heavilyWashedColour);
        UIManager.put("Slider.focus", heavilyWashedColour);
        UIManager.put("Slider.tickColor", heavilyWashedColour);
        UIManager.put("Button.hilight", heavilyWashedColour);
        UIManager.put("TabbedPane.focus", washedColour);

        // shadow
        UIManager.put("ToolBar.shadow", shadowColour);
        UIManager.put("ToolBar.darkShadow", darkShadowColour);
        UIManager.put("Slider.shadow", shadowColour);
        UIManager.put("Slider.darkShadow", darkShadowColour);
        UIManager.put("RadioButton.shadow", darkShadowColour);
        UIManager.put("RadioButton.darkShadow", darkShadowColour);
        UIManager.put("Button.shadow", shadowColour);
        UIManager.put("Button.darkShadow", shadowColour);
        UIManager.put("TabbedPane.shadow", shadowColour);
        UIManager.put("TabbedPane.darkShadow", darkShadowColour);
        UIManager.put("ToggleButton.shadow", darkShadowColour);
        UIManager.put("ToggleButton.darkShadow", darkShadowColour);

        // selected
        UIManager.put("MenuBar.selectionBackground", heavilyWashedColour);
        UIManager.put("MenuItem.selectionBackground", heavilyWashedColour);
        UIManager.put("RadioButton.select", washedColour);
        UIManager.put("CheckBox.select", washedColour);
        UIManager.put("ComboBox.selectionBackground", heavilyWashedColour);
        UIManager.put("List.selectionBackground", heavilyWashedColour);
        UIManager.put("Button.select", washedColour);
        UIManager.put("ToggleButton.select", washedColour);
        UIManager.put("TabbedPane.selected", heavilyWashedColour);
        UIManager.put("Slider.trackHighlight", washedColour);
        UIManager.put("Slider.thumbHighlight", heavilyWashedColour);
        UIManager.put("ScrollBar.trackHighlight", backgroundColour);
        UIManager.put("ScrollBar.thumbHighlight", backgroundColour);

        // borders
        UIManager.put("TabbedPane.tabRunOverlay", heavilyWashedColour);

        // other
        UIManager.put("Slider.altTrackColor", washedColour);
        UIManager.put("Slider.track", washedColour);
        UIManager.put("Slider.thumb", DEF_THUMB_COLOUR);
        UIManager.put("ScrollBar.track", backgroundColour);
        UIManager.put("ScrollBar.thumb", DEF_THUMB_COLOUR);

        // set component UI
        UIManager.put("SliderUI", "ui.custom.SynthSliderUI");
    }

    public static Color averageColours(Color x, Color y) {
        return new Color(Math.min((x.getRed() + y.getRed()) / 2, 255),
                Math.min((x.getGreen() + y.getGreen()) / 2, 255),
                Math.min((x.getBlue() + y.getBlue()) / 2, 255));
    }
}
