package utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Util class responsible for importing the
 * game's font from the media's package.
 *
 * @version 1.0.1
 */
public class FontImporter {
    /**
     * Loads the custom Mario {@link Font}
     * from the ttf file.
     *
     * @return The custom Mario {@link Font}.
     */
    public static Font loadFont(int size) {
        try {
            InputStream fontSrc = FontImporter.class.getResourceAsStream("/media/font/mario-font.ttf");
            assert fontSrc != null; // Makes sure that the loaded stream is not null
            return Font.createFont(Font.TRUETYPE_FONT, fontSrc).deriveFont((float) size);
        } catch (IOException | FontFormatException ignored) {}

        return null;
    }
}
