import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import graphics.GraphicsEngine;

/**
 *  компонент графики игры
 */
public class Main extends JPanel implements KeyListener, ActionListener {
    // игровой мир
    JetGame game;
    // таймер
    Timer timer;
    
    /**
     *  инициализация игры и таймера
     */
    public Main() {
	super();
	game = new JetGame();
	timer = new Timer(1000 / game.fps, this);
	timer.start();
    }

    /**
     *  кадр игры
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
	game.update();
	repaint();
    }
    
    /**
     *  отрисовка графики
     */
    @Override
    protected void paintComponent(Graphics g) {
	GraphicsEngine.draw(g);
    }

    /**
     *  возвращает размеры компонента графики
     */
    @Override
    public Dimension getPreferredSize() {
	return new Dimension(game.width, game.height);
    }

    /**
     *  обработка нажатий клавиш
     */
    @Override
    public void keyPressed(KeyEvent e) {
	int code = e.getKeyCode();
	if (code == KeyEvent.VK_LEFT)
	    game.left();
	else if (code == KeyEvent.VK_RIGHT)
	    game.right();
	if (code == KeyEvent.VK_UP)
	    game.up();
	else if (code == KeyEvent.VK_DOWN)
	    game.down();
	else if (code == KeyEvent.VK_SPACE)
	    game.action();
	else if (code == KeyEvent.VK_ESCAPE)
	    game.menu();
    }

    @Override
    public void keyReleased(KeyEvent e) {
	    game.keyRelease();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    /**
     *  главная программа
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Jet game");
		frame.setLayout (new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
                Main panel = new Main();
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addKeyListener(panel);
            }
        });
    }
}
