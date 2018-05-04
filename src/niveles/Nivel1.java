package niveles;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Nivel1 extends Rectangle {
	public static int velx = 8;
	Image imagen;

	public Nivel1(Image img) {
		super(0, 0, 6000, 500);
		imagen = img;
	}

	public void dibujar(Graphics g, Applet a) {
		g.setColor(Color.green);
		g.drawImage(imagen, x, y, width, height, a);
	}

	public void actualizar() {
		x -= velx;
		if (x == -2080) {
			velx = 0;
		}
	}
}
