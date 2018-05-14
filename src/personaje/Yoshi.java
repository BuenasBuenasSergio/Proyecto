package personaje;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import niveles.Nivel1;
import niveles.Nivel2;
import niveles.NivelBoss;

public class Yoshi extends Rectangle {

	Applet applet;
	private Image[] imagenes;
	int cont;// lleva la imagen en la que esta
	int velY = 5;
	public boolean derecha = false;
	public boolean izquierda = false;
	public static int velX = 5;
	int cambioIMG = 0;

	public Yoshi(Image[] imgs, Applet applet, int posX, int posY) {
		super(posX, posY, 80, 50);
		imagenes = imgs;
		this.applet = applet;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagenes[cont], x, y, applet);
	}

	public void actualizar() {
		rigthMove();
		leftMove();
	}

	public void salto() {
		if (velY == 0) {
			y = y - 150;
			velY = 3;
		}
	}

	public void caida() {
		if (y > 350) {
			velY = 0;
		}

		y += velY;

	}

	public void leftMove() {
		if (izquierda) {
			cont = 8;
			if ((Nivel1.velx == 0) || (Nivel2.velx == 0) || (NivelBoss.velx == 0)) {
				x -= velX;
			}
			cambioIMG++;
			// control cambio de tiempo de sprite;
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			// cambio de sprite
			// Movimiento hacia la Izquierda
			if (cont == 16) {
				cont = 9;
			}
		}
	}

	public void rigthMove() {
		if (derecha) {
			cont = 0;
			if ((Nivel1.velx == 0) || (Nivel2.velx == 0) || (NivelBoss.velx == 0)) {
				x += velX;
			}
			cambioIMG++;
			// control cambio de tiempo de sprite;
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			// cambio de sprite
			// Movimiento hacia la derecha
			if (cont == 8) {
				cont = 0;
			}
		}
	}
}
