package enemigos;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class DisparoBoss extends Rectangle {

	Applet applet;
	Image imagen;

	public DisparoBoss(Image img, int posX, int posY, Applet applet) {
		super(posX, posY, 70, 50);
		imagen = img;
		this.applet = applet;
	}

	public void dibujar(Graphics g) {
		g.drawImage(imagen, x, y, width, height, applet);
	}

	public void actualizar() {
		x -= 10;
	}
}
