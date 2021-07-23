package game;

import graphics.Canvas;
import game.GameEngine;

/**
 * Игровой объект
 */
public class GameObject {
    // позиция
    public int x;
    public int y;
    // вектор скорости
    public int speed_x;
    public int speed_y;
    // холст
    public Canvas canvas;
    // пометка на удаление
    public boolean deleted;
    // ссылка на игровой мир
    protected GameEngine world;

    /**
     * создает новый объект
     * @param canvas - холст
     * @param world - ссылка на мир
     */
    public GameObject(Canvas canvas, GameEngine world) {
	this.canvas = canvas;
	if (canvas != null) {
	    this.x = canvas.x;
	    this.y = canvas.y;
	}
	speed_x = 0;
	speed_y = 0;
	deleted = false;
	this.world = world;
    }

    /**
     * движение объекта, вызывается каждый кадр
     */
    public void update() {
	x += speed_x;
	y += speed_y;
	if (canvas != null) {
	    canvas.x = x;
	    canvas.y = y;
	}
    }

    /**
     * обработка столкновения - абстрактная функция, перекрывается у потомков
     * @param obj - с кем столкнулся
     */
    public void onCollision(GameObject obj) {
    }
}
