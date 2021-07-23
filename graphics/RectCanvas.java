package graphics;
import java.awt.Graphics;
import java.awt.Color;

/**
 * холст с прямоугольником
 */
public class RectCanvas extends Canvas {
    // цвет
    public Color color;
    
    /**
     * создает прямоугольник
     * @param c - цвет
     * @param x - координата x
     * @param y - координата y
     * @param width - ширина
     * @param height - высота
     */
    public RectCanvas(Color c, int x, int y, int width, int height) {
	super(null, x, y);
	color = c;
	this.width = width;
	this.height = height;
    }

    /**
     * отрисовка
     */
    @Override
    public void draw(Graphics g)
    {
	g.setColor(color);
	g.fillRect(x, y, width, height);
    }
}
