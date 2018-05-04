package niveles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Nivel2 extends Rectangle{
	int velx= 2; 
	
	public Nivel2() {
		super(0,600,2000,500);
	}
	public void dibujar(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
	}
	
	public void actualizar() {
		x +=velx;
	}
}