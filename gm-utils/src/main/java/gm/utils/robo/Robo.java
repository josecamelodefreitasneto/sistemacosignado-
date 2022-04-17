package gm.utils.robo;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import gm.utils.comum.USystem;

public class Robo {
	
	private Robot robot;
	
	private Robo() {
		try {
			this.robot = new Robot();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Robo instance;
	
	private static Robo get() {
		if (instance == null) {
			instance = new Robo();
		}
		return instance;
	}
	
	public static void move(int x, int y) {
		get().robot.mouseMove(x, y);
	}
	public static void click() {
		get().robot.mousePress(InputEvent.BUTTON1_MASK);
		get().robot.mouseRelease(InputEvent.BUTTON1_MASK);
		USystem.sleepMiliSegundos(50);
	}
	public static void click(int x, int y) {
		move(x, y);
		click();
	}

	public static void main(String[] args) throws AWTException {
		int i = 100;
		while (true) {
			if (i == 100) {
				i = 500;
			} else {
				i = 100;
			}
			move(i, i);
			USystem.sleepSegundos(20);
		}
	}

}
