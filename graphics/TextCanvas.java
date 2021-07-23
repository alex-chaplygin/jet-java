package graphics;
import java.awt.Graphics;
import java.awt.Color;

/**
 * холст с текстом
 */
public class TextCanvas extends Canvas {
    // цвет
    public Color color;
    // текст
    public String text;
    
    /**
     * создает текст
     * @param c - цвет
     * @param text - строка текста
     * @param x - координата x
     * @param y - координата y
     */
    public TextCanvas(Color c, String text, int x, int y) {
	super(null, x, y);
	color = c;
	this.text = text;
    }

    /**
     * отрисовка
     */
    @Override
    public void draw(Graphics g)
    {
	g.setColor(color);
	g.drawString(text, x, y);
    }
}
