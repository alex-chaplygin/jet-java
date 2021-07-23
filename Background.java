import java.awt.image.BufferedImage;
import game.GameObject;
import game.GameEngine;
import graphics.GraphicsEngine;
import graphics.Canvas;

public class Background extends GameObject {
    // число повторяющихся изображений фона
    final int NUM_CANVASES = 4;
    // массив холстов фона
    Canvas[] canvases;
    
    /**
     * создает новый объект фона
     * @param img - изображение части фона
     * @param world - ссылка на мир
     */
    public Background(BufferedImage img, GameEngine world) {
	super(null, world);
	canvases = new Canvas[NUM_CANVASES];
	for (int i = 0; i < NUM_CANVASES; i++)
	    canvases[i] = GraphicsEngine.addCanvas(new Canvas(img, 0, i * img.getHeight()));
	deleted = false;
    }

    /**
     * циклическое движение фона
     */
    @Override
    public void update() {
	super.update();
	for (int i = 0; i < NUM_CANVASES; i++) {
	    canvases[i].y += speed_y;
	    if (canvases[i].y > world.height)
	    	canvases[i].y = - canvases[i].height + 1;
	}
    }
}
