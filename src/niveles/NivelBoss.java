package niveles;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class NivelBoss extends Rectangle {
	public static int velx = 0;
	Image imagen;
	public boolean derecha = false;
	public boolean izquierda = false;

	public NivelBoss(Image img) {
		super(0, 0, 1920, 500);
		imagen = img;

	}

	public void dibujar(Graphics g, Applet a) {
		g.drawImage(imagen, x, y, width, height, a);
	}

}