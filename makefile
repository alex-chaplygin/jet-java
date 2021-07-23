SOURCE=Main.java JetGame.java graphics/GraphicsEngine.java graphics/Canvas.java graphics/RectCanvas.java graphics/TextCanvas.java graphics/AnimatedCanvas.java game/GameObject.java game/GameEngine.java Background.java Enemy.java Bullet.java Boss.java
CLASS=$(SOURCE:.java=.class)

all: $(CLASS) 

%.class: %.java
	javac -d . -classpath . $<
