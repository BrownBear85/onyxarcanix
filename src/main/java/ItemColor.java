import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ItemColor {
    public static Color averageColor(BufferedImage image) throws IOException {
        int rData = 0, bData = 0, gData = 0;
        int nonTransparentPixels = 0;
        Color[][] pixels = new Color[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x, y));
                if (pixel.getRGB() != 0) {
                    rData += pixel.getRed();
                    gData += pixel.getGreen();
                    bData += pixel.getBlue();
                    nonTransparentPixels++;
                }
            }
        }
        return new Color(rData / nonTransparentPixels, gData / nonTransparentPixels, bData / nonTransparentPixels);
    }

    public static void main(String[] args) {
        Vector3f color = new Vector3f(0.2F, 0.5F, 0.8F);
        System.out.println(color);
        int rgb = color.hashCode();
        System.out.println(rgb);
        color = new Vector3f(Vec3.fromRGB24(rgb));
        System.out.println(color);
    }
}
