import java.awt.Color;
import game.GameObject;
import game.GameEngine;
import graphics.GraphicsEngine;
import graphics.RectCanvas;

/**
 * класс пули
 */
public class Bullet extends GameObject{
    // ширина пули
    static final int WIDTH = 5;
    // высота пули
    static final int HEIGHT = 10;
    // скорость пули
    final int BUL_SPEED = 6;    

    /**
     * создает новую пулю
     * @param c - цвет
     * @param x - координата x
     * @param y - координата y
     * @param dir - направление полета 1 - вниз, -1 - вверх
     */
    public Bullet(Color c, int x, int y, int dir, GameEngine world) {
	super(GraphicsEngine.addCanvas(new RectCanvas(c, x, y, Bullet.WIDTH, Bullet.HEIGHT)), world);
	speed_y = BUL_SPEED * dir;
    }

    /**
     * удаляем пулю, если вылетела за пределы экрана
     */    
    @Override
    public void update() {
	super.update();
	if (y < 0 || y > world.height)
	    world.removeObject(this);
    }

    /**
     * удаляем пулю при столкновении с объектом, кроме самой пули
     */    
    @Override
    public void onCollision(GameObject o) {
	if (!(o instanceof Bullet))
	    world.removeObject(this);
    }
}
