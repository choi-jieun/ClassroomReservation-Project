package frontend;

import java.awt.*;
import java.io.InputStream;
import java.io.IOException;

public class FontUtil {
    public static Font getPsFont(float size) {
        try {
            InputStream is = FontUtil.class.getResourceAsStream("/font/ps.ttf");
            if (is == null) {
                throw new IOException("ps.ttf 파일을 찾을 수 없습니다.");
            }
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }
}
