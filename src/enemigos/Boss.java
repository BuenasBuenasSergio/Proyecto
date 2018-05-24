package enemigos;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Boss extends Rectangle {
	public static int velx = 7;
	private Image imagen[];
	int cont;
	Applet applet;
	int cambioIMG = 0;
	public boolean derecha = false;
	public boolean izquierda = false;

	public Boss(Image c[], int px, int py, Applet a) {
		super(px, py, 200, 200);
		imagen = c;
		applet = a;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagen[cont], x, y, applet);
	}

	public void actualizar() {
		x -= velx;
		cambioIMG++;
		if (cambioIMG == 3) {
			cont++;
			cambioIMG = 0;
		}
		if (cont == 3) {
			cont = 0;
		}
		if (x <= 1000) {
			velx *= -1;

			// control cambio de tiempo de sprite;
			// Movimiento hacia la Izquierda
		}

		if (x >= 1750) {
			velx *= -1;
			// control cambio de tiempo de sprite;
			// Movimiento hacia la Izquierda

		}
	}

}
