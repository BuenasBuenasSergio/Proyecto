package personaje;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Yoshi extends Rectangle {

	Applet applet;
	private Image[] imagenes;
	int cont;
	public boolean salto = false;
	public boolean caida = false;
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

	public void setImagenes(Image[] imagenes) {
		this.imagenes = imagenes;
	}

	public void actualizar() {

		if (derecha) {

			// x += velX;
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
		if (izquierda) {

			// x -= velX;
			cambioIMG++;
			// control cambio de tiempo de sprite;
			if (cambioIMG == 3) {
				cont++;
				cambioIMG = 0;
			}
			// cambio de sprite
			// Movimiento hacia la Izquierda
			if (cont == 8) {
				cont = 0;
			}
		}
	}

	public void salto() {
		if (salto == true && y >= -50 && caida == false) {
			y -= 50;
			// cont = 0;
			if (y == -50) {
				caida = true;
				salto = false;
			}
		}
		if (caida == true) {
			y += 50;
			// cont = 1;
			if (y == 150) {
				caida = false;
			}
		}
	}
}
