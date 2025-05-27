import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.RGBImageFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;

public class ImageLoader {
	public Image loadImage(String name) {
		return new ImageIcon(getClass().getResource(name)).getImage();
	}
	public Image loadImage(String name, Color color) {
		Image image = new ImageIcon(getClass().getResource(name)).getImage();
		RGBImageFilter filter = new RGBImageFilter() {
			final int markerRGB = color.getRGB() | 0xFF000000;

			public int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					return 0x00FFFFFF;
				} else {
					return rgb;
				}
			}
		};
		return java.awt.Toolkit.getDefaultToolkit().createImage(
			new FilteredImageSource(image.getSource(), filter)
		);	
	}
	public BufferedImage loadBufferedImage(String name, Color color) {
		Image image = new ImageIcon(getClass().getResource(name)).getImage();
		RGBImageFilter filter = new RGBImageFilter() {
			final int markerRGB = color.getRGB() | 0xFF000000;

			public int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					return 0x00FFFFFF;
				} else {
					return rgb;
				}
			}
		};
		Image filteredImage = java.awt.Toolkit.getDefaultToolkit().createImage(
			    new FilteredImageSource(image.getSource(), filter)
			);

			ImageIcon icon = new ImageIcon(filteredImage);
			int width = icon.getIconWidth();
			int height = icon.getIconHeight();

			BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = buffered.createGraphics();
			g2.drawImage(filteredImage, 0, 0, null);
			g2.dispose();

			return buffered;
	}
}
