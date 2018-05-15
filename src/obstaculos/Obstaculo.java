package obstaculos;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import niveles.Nivel1;

public class Obstaculo extends Rectangle {

	Image imagen;
	Applet applet;

	public Obstaculo(Image img, Applet a) {
		super(500, 350, 60, 150);
		imagen = img;
		applet = a;
	}

	public void dibujar(Graphics g) {
		// g.setColor(Color.cyan);
		// g.fillRect(x, y, width, height);
		g.drawImage(imagen, x, y, width, height, applet);
	}

	public void actualizar() {
		x -= Nivel1.velx;
	}
}