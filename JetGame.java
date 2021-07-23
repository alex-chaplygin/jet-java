import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.util.Random;
import graphics.GraphicsEngine;
import graphics.Canvas;
import graphics.TextCanvas;
import graphics.RectCanvas;
import graphics.AnimatedCanvas;
import game.GameEngine;
import game.GameObject;

/**
 * Логика игры
 */
public class JetGame extends GameEngine{
    // ширина окна
    public final int WIDTH = 300;
    // высота окна
    public final int HEIGHT = 600;
    // число кадров в секунду
    public final int fps = 30;
    // длительность кадра
    public final int rate = 60 / fps;
    // позиция заголовка
    final int TITLE_X = 20;
    final int TITLE_Y = 20;
    // позиция меню
    final int MENU_X = 40;
    final int MENU1_Y = 140;
    final int MENU2_Y = 160;
    // размер указателя меню
    final int MENUPOS_SIZE = 10;
    // позиция указателя меню
    final int MENUPOS_X = 20;
    final int MENUPOS_Y1 = 130;
    final int MENUPOS_Y2 = 150;
    // скорость фона
    final int BACK_SPEED = 2;
    // стартовая позиция самолета
    final int JET_X = 200;
    final int JET_Y = 500;
    // скорость самолета
    final int JET_SPEED = 5;
    // смещение пули
    final int BULOFS_X = 5;
    final int BULOFS_Y = -1;
    // число врагов до босса
    final int NUM_ENEMIES = 5;    
    // число кадров между выпуском врагов
    final int ENEMY_TIME = 70;    
    // вертикальная скорость врагов
    final int ENEMY_SPEED = 3;    
    // состяния игры
    enum State {
	MENU,
	GAME,
	SCORE
    };
    // текущее состояние
    State gameState;
    // пункты меню
    Canvas[] menu;
    // указатель меню
    Canvas menupos;
    // счет после окончания игры
    Canvas finalScore;
    // самолет
    GameObject jet;
    // враг
    GameObject enemy;
    // босс
    GameObject boss;
    // число врагов
    int enemyCount;
    // счетчик времени генерации нового врага
    int enemyTimer;
    // статистика
    TextCanvas stat;
    // очки
    int score;
    // случайные числа
    Random random;

    /**
     * конструктор игры
     */
    public JetGame() {
	random = new Random();
	width = WIDTH;
	height = HEIGHT;
	gameState = State.MENU;
    }

    /**
     * переход в меню
     */
    void initMenu() {
	menu = new Canvas[4];
	menu[0] = GraphicsEngine.addCanvas(new RectCanvas(Color.yellow, 0, 0, width, height));
	menu[1] = GraphicsEngine.addCanvas(new TextCanvas(Color.red, "JET JAVA", TITLE_X, TITLE_Y));
	menu[2] = GraphicsEngine.addCanvas(new TextCanvas(Color.red, "New game", MENU_X, MENU1_Y));
	menu[3] = GraphicsEngine.addCanvas(new TextCanvas(Color.red, "Quit", MENU_X, MENU2_Y));
	menupos = GraphicsEngine.addCanvas(new RectCanvas(Color.red, MENUPOS_X, MENUPOS_Y1, MENUPOS_SIZE, MENUPOS_SIZE));
    }

    /**
     * переход в счет
     */
    void initScore() {
	GraphicsEngine.addCanvas(new RectCanvas(Color.yellow, 0, 0, width, height));
	finalScore = GraphicsEngine.addCanvas(new TextCanvas(Color.red, "Счет: " + score, TITLE_X, TITLE_Y));
    }
    
    /**
     * переход в игру
     */
    void initGame() {
	BufferedImage world = getImage("/img/WorldObj.png");
	GameObject b = addObject(new Background(world, this));
	b.speed_y = BACK_SPEED;
	jet = addObject(new GameObject(GraphicsEngine.addCanvas(new AnimatedCanvas(getImage("/img/jet.png"), JET_X, JET_Y, 4)), this) {
		/**
		 * при столкновении убираем себя
		 */    
		@Override
		public void onCollision(GameObject o) {
		    world.removeObject(this);
		}
	    });
	stat = (TextCanvas)GraphicsEngine.addCanvas(new TextCanvas(Color.red, "Objects:", TITLE_X, TITLE_Y));
	enemyCount = 0;
	enemyTimer = 0;
	score = 0;
    }
    
    /**
     * загрузка изображения из ресурса
     */
    BufferedImage getImage(String filename) {
	BufferedImage img = null;
	try {
	    img = ImageIO.read(Main.class.getResource(filename));
	} catch (IOException ex) {
                System.out.println("problem! image can't be loaded!");
	}
	return img;
    }
    
    /**
     * обработка всех состояний игры
     */
    @Override
    public void update() {
	if (gameState == State.MENU) {
	    if (menu == null || menu[0] == null)
		initMenu();
	} else if (gameState == State.GAME) {
	    if (jet == null)
		initGame();
	    super.update();
	    if (jet.deleted == true) {
		clear();
		jet = null;
		enemy = null;
		gameState = State.MENU;
		return;
	    } else if (boss != null && boss.deleted == true) {
		clear();
		boss = null;
		jet = null;
		gameState = State.SCORE;
		return;
	    }
	    stat.text = "Очки: " + score;
	    doJet();
	    doEnemies();
	} else if (gameState == State.SCORE) {
	    if (finalScore == null)
		initScore();
	}
    }

    /**
     * ограничение движения самолета
     */
    void doJet() {
	if (jet.x < 0)
	    jet.x = 0;
	else if (jet.x + jet.canvas.width > width)
	    jet.x = width - jet.canvas.width;
	if (jet.y < 0)
	    jet.y = 0;
	else if (jet.y + jet.canvas.height > height)
	    jet.y = height - jet.canvas.height;
    }
    
    /**
     * генерация врагов
     */
    void doEnemies() {
	Canvas en;
	BufferedImage bi;
	if (enemy == null)
	    enemyTimer++;
	else if (enemy.deleted) {
	    if (((Enemy)enemy).killed)
		score += 1;
	    enemy = null;
	}
	if (enemyTimer == ENEMY_TIME) {
	    enemyTimer = 0;
	    if (enemyCount < NUM_ENEMIES) {
		bi  = getImage("/img/jet.png");
	    } else {
		bi  = getImage("/img/FinalEnemy.png");
	    }
	    if (enemyCount < NUM_ENEMIES) {
		en = GraphicsEngine.addCanvas(new AnimatedCanvas(bi, random.nextInt(width - bi.getWidth()), -bi.getHeight(), 4));
		en.flip = true;
		enemy = addObject(new Enemy(en, this));
		enemy.y -= enemy.canvas.height;
		enemy.speed_y = ENEMY_SPEED;
		enemyCount++;
	    } else {
		en = GraphicsEngine.addCanvas(new Canvas(bi, random.nextInt(width - bi.getWidth()), -bi.getHeight()));
		enemyTimer = ENEMY_TIME + 1;
		boss = addObject(new Boss(en, this));
		boss.y -= boss.canvas.height;
		boss.speed_y = ENEMY_SPEED;
	    }
	}
    }
    
    /**
     *  перемещение влево
     */
    public void left() {
	if (gameState == State.GAME) {
	    jet.speed_x = -JET_SPEED;
	    ((AnimatedCanvas)jet.canvas).frame = 1;
	}
    }

    /**
     *  перемещение вправо
     */
    public void right() {
	if (gameState == State.GAME) {
	    jet.speed_x = JET_SPEED;
	    ((AnimatedCanvas)jet.canvas).frame = 1;
	}
    }

    /**
     *  перемещение вверх
     */
    public void up() {
	if (gameState == State.MENU) {
	    if (menupos.y > MENUPOS_Y1)
		menupos.y = MENUPOS_Y1;
	} else if (gameState == State.GAME) {
	    jet.speed_y = -JET_SPEED;
	    ((AnimatedCanvas)jet.canvas).frame = 1;
	}	
    }

    /**
     *  перемещение вниз
     */
    public void down() {
	if (gameState == State.MENU) {
	    if (menupos.y < MENUPOS_Y2)
		menupos.y = MENUPOS_Y2;
	} else if (gameState == State.GAME)
	    jet.speed_y = JET_SPEED;
    }

    /**
     *  при отпускании клавиши устанавливаем начальный кадр
     */
    public void keyRelease() {
	if (gameState == State.GAME && jet != null) {
	    ((AnimatedCanvas)jet.canvas).frame = 0;
	    jet.speed_x = 0;
	    jet.speed_y = 0;
	}
    }
    
    /**
     *  действие: в меню выбор пункта, в игре - стрельба
     */
    public void action() {
	if (gameState == State.MENU) {
	    if (menupos.y == MENUPOS_Y2)
		System.exit(0);
	    else {
		clear();
		menu = null;
		gameState = State.GAME;
	    }
	} else if (gameState == State.GAME) 
	    addObject(new Bullet(Color.red, jet.x + jet.canvas.width / 2, jet.y - Bullet.HEIGHT + BULOFS_Y, -1, this));
	else if (gameState == State.SCORE) {
	    finalScore = null;
	    gameState = State.MENU;
	}
    }

    /**
     *  переход в меню и обратно
     */
    public void menu() {
	if (gameState == State.GAME)
	    gameState = State.MENU;
	else if (gameState == State.MENU) {
		for (Canvas c : menu)
		  GraphicsEngine.removeCanvas(c);
		GraphicsEngine.removeCanvas(menupos);
		menu = null;
		gameState = State.GAME;
	}
    }
}
