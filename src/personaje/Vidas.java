package personaje;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Vidas {
	Image imagen;
	public int posx;
	public int posy;
	Applet applet;

	public Vidas(Image img, int px, int py, Applet a) {

		imagen = img;
		posx = px;
		posy = py;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagen, posx, posy, 50, 50, applet);

	}
}
