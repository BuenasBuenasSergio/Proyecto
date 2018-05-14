package personaje;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import enemigos.Seta;

public class PowerUP extends Rectangle {
	Image imagen;
	Applet applet;
	int velX = Seta.velx;

	public PowerUP(Image img, int px, int py, Applet a) {
		super(px, py, 40, 60);
		imagen = img;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagen, x, y, width, height, applet);
	}

	public void actualizar() {
		x -= velX;
	}
}
