import game.GameObject;
import game.GameEngine;
import graphics.GraphicsEngine;
import graphics.Canvas;

/**
 * класс босса
 */
public class Boss extends Enemy {
    // горизонтальная скорость
    final int BOSS_SPEED = 6;
    // скорость стрельбы
    final int BOSS_FIRE = 15;
    // здоровье
    final int BOSS_HP = 60;
    // повреждения
    int hp;
    
    /**
     * создает босса
     * @param c - изображение 
     * @param world - ссылка на мир
     */
    public Boss(Canvas c, GameEngine world) {
	super(c, world);
	fireCount = BOSS_FIRE;
	hp = BOSS_HP;
	speed_x = BOSS_SPEED;
    }

    /**
     * движение босса влево-вправо и начальный выезд
     */
    @Override
    public void update() {
	super.update();
	if (x + canvas.width / 2 > world.width)
	    speed_x = -BOSS_SPEED;
	if (x + canvas.width / 2 < 0)
	    speed_x = BOSS_SPEED;
	if (y > 0)
	    speed_y = 0;
    }
    
    /**
     * при попадании уменьшаем hp
     * удаляем себя если hp == 0
     */    
    @Override
    public void onCollision(GameObject o) {
	if (o instanceof Bullet) {
	    hp--;
	    if (hp == 0) {
		world.removeObject(this);
		killed = true;
	    }
	}
    }
}
