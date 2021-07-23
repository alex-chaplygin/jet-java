package game;
import graphics.Canvas;
import graphics.GraphicsEngine;

/**
 * главный класс игры, содержит все объекты игрового мира
 * обеспечивает движение объектов и проверку столкновений
 */
public class GameEngine {
    // максимальное число объектов
    final int MAX_OBJECTS = 100;
    // ширина игрового мира
    public int width;
    // высота игрового мира
    public int height;
    // список всех игровых объектов
    protected GameObject[] objects;
    
    /**
     * инициализация списка
     */
    public GameEngine() {
	objects = new GameObject[MAX_OBJECTS];
    }

    /**
     * добавление объекта через холст
     */
    public GameObject addObject(Canvas c) {
	GameObject o = new GameObject(c, this);
	return addObject(o);
    }

    /**
     * добавление уже созданного объекта
     */
    public GameObject addObject(GameObject o) {
	for (int i = 0; i < MAX_OBJECTS; i++)
	    if (objects[i] == null) {
		objects[i] = o;
		break;
	    }
	return o;
    }

    /**
     * удаление объекта
     */
    public GameObject removeObject(GameObject o) {
	GraphicsEngine.removeCanvas(o.canvas);
	o.deleted = true;
	return o;
    }

    /**
     * удаление всех объектов и графики
     */
    public void clear() {
	GraphicsEngine.clear();
	for (int i = 0; i < MAX_OBJECTS; i++)
	    objects[i] = null;
    }
    
    /**
     * кадр игрового мира, обновление всех объектов, проверка столкновений
     */
    public void update() {
	for (int i = 0; i < MAX_OBJECTS; i++) {
	    GameObject o = objects[i];
	    if (o != null) {
		if (o.deleted)
		    objects[i] = null;
		else {
		    o.update();
		    for (GameObject o2 : objects)
			if (o2 != null && checkCollision(o, o2)) {
			    o.onCollision(o2);
			    o2.onCollision(o);
			}
		}
	    }
	}
    }

    /**
     * проверка столкновения двух объектов
     */
    public boolean checkCollision(GameObject o1, GameObject o2) {
	if (o1 == null || o2 == null)
	    return false;
	if (o1 == o2)
	    return false;
	if (o1.deleted || o2.deleted)
	    return false;
	if (o1.canvas == null)
	    return false;
	if (o2.canvas == null)
	    return false;
	if (o1.x + o1.canvas.width < o2.x)
	    return false;
	if (o2.x + o2.canvas.width < o1.x)
	    return false;
	if (o1.y + o1.canvas.height < o2.y)
	    return false;
	if (o2.y + o2.canvas.height < o1.y)
	    return false;
	return true;
    }
}
