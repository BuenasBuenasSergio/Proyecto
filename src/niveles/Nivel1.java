package niveles;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Nivel1 extends Rectangle {
	public static int velx = 8;
	Image imagen;
	public boolean derecha = false;
	public boolean izquierda = false;

	public Nivel1(Image img) {
		super(0, 0, 6000, 500);
		imagen = img;
	}

	public void dibujar(Graphics g, Applet a) {
		g.drawImage(imagen, x, y, width, height, a);
	}

	public void actualizar() {

		if (derecha) {
			x -= velx;
			velx = 8;
			if (x == -2080) {
				velx = 0;

			}
		}
		if (izquierda) {
			x += velx;
			if (x >= 0) {
				velx = 0;
			}
		}
	}
}
