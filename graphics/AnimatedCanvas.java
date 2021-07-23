package graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimatedCanvas extends Canvas {
    // число кадров анимации
    int numFrames;
    // текущий кадр
    public int frame;
    // ширина кадра
    int frameWidth;
    
    /**
     * загружает изображение из ресурса
     * @param img - изображение
     * @param x - координата x холста
     * @param y - координата y холста
     * @param num - количество кадров анимации
     */
    public AnimatedCanvas(BufferedImage img, int x, int y, int num) {
	super(img, x, y);
	numFrames = num;
	frameWidth = width / numFrames;
	width = frameWidth;
    }

    /**
     * отрисовка текущего кадра
     */
    @Override
    public void draw(Graphics g)
    {
	if (flip)
	    g.drawImage(img, x, y + height, x + width, y, frameWidth * frame, 0, frameWidth * (frame + 1), height, null);
	else
	    g.drawImage(img, x, y, x + width, y + height, frameWidth * frame, 0, frameWidth * (frame + 1), height, null);
    }
    
}
