package personaje;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Disparo extends Rectangle {

	Applet applet;
	Image imagen;

	public Disparo(Image img, Applet applet, int posX, int posY) {
		super(posX, posY, 50, 50);
		imagen = img;
		this.applet = applet;
	}

	public Disparo(int posX, int posY, Applet applet) {
		super(posX, posY, 50, 50);
		this.applet = applet;
	}

	public void dibujar(Graphics g) {
		// g.drawImage(imagen, x, y, 50, 30, applet);
		g.setColor(Color.red);
		g.fillRect(x, y, 50, 20);
	}

	public void actualizar() {
		x += 5;
	}
}
