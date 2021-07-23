package graphics;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * графический движок, основанный на списке холстов
 * объекты игры будут сохранять ссылку на холст и управлять позицией
 * можно добавлять и удалять холсты по порядку отрисовки
 */
public class GraphicsEngine {
    // список всех холстов по порядку отрисовки
    static LinkedList<Canvas> canvases = new LinkedList<Canvas>();

    /**
     * добавляем холст в конец списка
     */
    public static Canvas addCanvas(Canvas c) {
	if (c != null)
	    canvases.add(c);
	return c;
    }

    /**
     * удаляем холст
     */
    public static void removeCanvas(Canvas c) {
	if (c != null)
	    canvases.remove(c);
    }

    /**
     * удаляем все
     */
    public static void clear() {
	canvases.clear();
    }
    
    /**
     * рисуем все по порядку
     */
    public static void draw(Graphics g) {
	for (Canvas c : canvases)
	    c.draw(g);
    }
}
