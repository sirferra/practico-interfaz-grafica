package ui.GUI.Constants;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Colors {
    private static final Map<String, Color> morningGlory = new HashMap<>();

    static {
        morningGlory.put("50", new Color(0xeffcfb));
        morningGlory.put("100", new Color(0xd6f7f6));
        morningGlory.put("200", new Color(0xa2ebea));
        morningGlory.put("300", new Color(0x7ee2e1));
        morningGlory.put("400", new Color(0x42ccce));
        morningGlory.put("500", new Color(0x26b0b4));
        morningGlory.put("600", new Color(0x228f98));
        morningGlory.put("700", new Color(0x22747c));
        morningGlory.put("800", new Color(0x245e66));
        morningGlory.put("900", new Color(0x224f57));
        morningGlory.put("950", new Color(0x11343b));
    }

    public static Color getMorningGlory(String shade) {
        return morningGlory.getOrDefault(shade, Color.BLACK); // Devuelve negro si no existe el tono
    }
}
