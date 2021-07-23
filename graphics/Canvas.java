package graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Изображение вместе с размерами и положением на экране
 */
public class Canvas {
    // позиция холста на экране
    public int x;
    public int y;
    // ширина изображения
    public int width;
    // высота изображения
    public int height;
    // переворачивать изображение по вертикали
    public boolean flip;
    // изображение холста
    public BufferedImage img;
    
    /**
     * загружает изображение из ресурса
     * @param img - изображение
     * @param x - координата x холста
     * @param y - координата y холста
     */
    public Canvas(BufferedImage img, int x, int y) {
	this.x = x;
	this.y = y;
	this.img = img;
	if (img != null) {
	    this.width = img.getWidth();
	    this.height = img.getHeight();
	}
    }

    /**
     * отрисовка изображения
     */
    public void draw(Graphics g)
    {
	if (flip)
	    g.drawImage(img, x, y + height, width, -height, null);
	else
	    g.drawImage(img, x, y, width, height, null);
    }
}
