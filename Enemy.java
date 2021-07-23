import java.awt.Color;
import game.GameObject;
import game.GameEngine;
import graphics.GraphicsEngine;
import graphics.Canvas;

/**
 * класс вражеского самолета
 */
public class Enemy extends GameObject {
    // число кадров между выстрелами
    protected int fireCount;
    // счетчик кадров между выстрелами
    int fireTime;
    // убитый
    public boolean killed;
    
    /**
     * создает новый объект врага
     * @param c - изображение 
     * @param world - ссылка на мир
     */
    public Enemy(Canvas c, GameEngine world) {
	super(c, world);
	fireTime = 0;
	killed = false;
	fireCount = 15;
    }

    /**
     * убираем объект если вышел за пределы экрана, стреляем
     */
    @Override
    public void update() {
	super.update();
	if (y > world.height) {
	    GraphicsEngine.removeCanvas(canvas);
	    deleted = true;
	}
	fireTime++;
	if (fireTime == fireCount) {
	    fireTime = 0;
	    world.addObject(new Bullet(Color.yellow, x + canvas.width / 2, y + canvas.height + 1, 1, world));
	}
    }    

    /**
     * удаляем себя при столкновении с пулей
     */    
    @Override
    public void onCollision(GameObject o) {
	if (o instanceof Bullet) {
	    world.removeObject(this);
	    killed = true;
	}
    }
}  
