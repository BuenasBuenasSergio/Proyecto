package obstaculos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import niveles.Nivel1;

public class Obstaculo extends Rectangle {

	public Obstaculo() {
		super(700, 200, 50, 100);
	}

	public void dibujar(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, width, height);
	}

	public void actualizar() {
		x -= Nivel1.velx;
	}
}