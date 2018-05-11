package personaje;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Disparo extends Rectangle {

	Applet applet;
	Image imagen;

	public Disparo(Image img, int posX, int posY, Applet applet) {
		super(posX, posY, 50, 50);
		imagen = img;
		this.applet = applet;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagen, x, y, 30, 30, applet);
	}

	public void actualizar() {
		x += 10;
	}
}
