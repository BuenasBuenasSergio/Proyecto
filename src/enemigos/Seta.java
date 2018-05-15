package enemigos;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Seta extends Rectangle {
	public static int velx = 5;
	public static int velxLevel2 = 7;
	private Image imagen[];
	int cont;
	Applet applet;
	int cambioIMG = 0;
	public boolean derecha = false;
	public boolean izquierda = false;

	public Seta(Image c[], int px, int py, Applet a) {
		super(px, py, 50, 50);
		imagen = c;
		applet = a;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagen[cont], x, y, applet);
	}

	public void actualizar() {
		if (derecha) {
			x -= velx;
			cambioIMG++;
			// control cambio de tiempo de sprite;
			// Movimiento hacia la Izquierda
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			if (cont == 3) {
				cont = 0;
			}
		}
		if (izquierda) {
			x -= velx - 2;
			cambioIMG++;
			// control cambio de tiempo de sprite;
			// Movimiento hacia la Izquierda
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			if (cont == 3) {
				cont = 0;
			}
		}
	}

	public Seta(Image c[], int px, int py, Applet a, String level) {
		super(px, py, 50, 50);
		imagen = c;
		applet = a;
	}

	public void dibujarLevel2(Graphics g) {
		g.drawImage(imagen[cont], x, y, applet);
	}

	public void actualizarLevel2() {
		if (derecha) {
			x -= velx;
			cambioIMG++;
			// control cambio de tiempo de sprite;
			// Movimiento hacia la Izquierda
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			if (cont == 3) {
				cont = 0;
			}
		}
		if (izquierda) {
			x -= velx - 2;
			cambioIMG++;
			// control cambio de tiempo de sprite;
			// Movimiento hacia la Izquierda
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			if (cont == 3) {
				cont = 0;
			}
		}
	}
}
