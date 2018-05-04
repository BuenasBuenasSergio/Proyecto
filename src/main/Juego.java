package main;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;

import enemigos.Seta;
import niveles.Nivel1;
import niveles.Nivel2;
import obstaculos.Obstaculo;
import personaje.Yoshi;

public class Juego extends Applet implements Runnable {
	public static final int numYoshis = 16;
	public static final int numSetas = 3;

	Thread animacion;
	Image imagen;
	Graphics noseve;
	Nivel1 nivel1;
	Nivel2 nivel2;
	Boolean movimineto = false;
	Yoshi yoshi;
	Image imgYoshi[], fondoNivel1, imgSeta[];
	Seta seta;
	Obstaculo tuberia;

	public void init() {
		resize(1920, 500);
		imagen = createImage(1920, 1080);
		noseve = imagen.getGraphics();
		fondoNivel1 = getImage(getCodeBase(), "Resources/Level1/fondo.png");
		nivel1 = new Nivel1(fondoNivel1);
		nivel2 = new Nivel2();
		// Creacion de Yoshi
		imgYoshi = new Image[numYoshis];
		for (int i = 0; i < numYoshis; i++)
			imgYoshi[i] = getImage(getCodeBase(), "Resources/Yoshi/yoshi" + (i + 1) + ".png");
		yoshi = new Yoshi(imgYoshi, this, 100, nivel1.height - 150);

		// Creacion de Seta
		imgSeta = new Image[numSetas];
		for (int i = 0; i < numSetas; i++)
			imgSeta[i] = getImage(getCodeBase(), "Resources/Enemigos/Seta/Seta" + (i + 1) + ".png");
		seta = new Seta(imgSeta, 1000, nivel1.height - 120, this);

	}

	public void start() {
		animacion = new Thread(this);
		animacion.start(); // llama al método run()
	}

	public void paint(Graphics g) {
		noseve.setColor(Color.white);
		noseve.fillRect(0, 0, 1920, 1080);
		nivel1.dibujar(noseve, this);
		// nivel2.dibujar(noseve);
		yoshi.dibujar(noseve);
		seta.dibujar(noseve);
		g.drawImage(imagen, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void run() {
		while (true) {
			seta.actualizar();
			repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			;
		}
	}

	public boolean keyDown(Event ev, int tecla) {
		switch (tecla) {
		case 100:
			yoshi.izquierda = false;
			yoshi.derecha = true;
			yoshi.actualizar();
			nivel1.actualizar();
			seta.velx = 10;
			break;
		case 97:
			yoshi.derecha = false;
			yoshi.izquierda = true;
			yoshi.actualizar();
			break;
		case 119:
			yoshi.salto = true;
			yoshi.salto();
			break;
		}

		repaint();
		return true;
	}

	public boolean keyUp(Event ev, int tecla) {
		switch (tecla) {
		case 100:
			seta.velx = 5;
			break;
		case 119:
			yoshi.salto = false;
		}

		repaint();
		return true;
	}

}
