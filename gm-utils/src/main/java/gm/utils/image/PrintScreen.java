package gm.utils.image;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gm.utils.comum.USystem;
import gm.utils.files.UFile;
import gm.utils.number.UInteger;
import gm.utils.number.UNumber;
import gm.utils.string.UString;

public class PrintScreen {

	static Robot robot;

	public static void main(String[] args) throws AWTException, IOException {
		robot = new Robot();
		print(0);
	}

	public static void exec() throws AWTException, IOException {
		robot.mouseMove(350, 380);
		int mask = InputEvent.BUTTON1_DOWN_MASK;
		sleep();
		
		for (int i = 0; i < 1; i+=5) {

			robot.mousePress(mask);
			sleep();
			robot.mouseRelease(mask);
			key(KeyEvent.VK_DELETE);
			String s = UString.toString(i);
			while (!s.isEmpty()) {
				int x = UInteger.toInt(s.substring(0,1));
				s = s.substring(1);
				keyNumber(x);
			}
			key(KeyEvent.VK_ENTER);
			print(i);
		}
		
	}

	private static void print(int i) throws IOException {
		String file = "/tmp/pie"+UNumber.format00(i, 3)+".jpg";

		UFile.delete(file);
		USystem.sleepSegundos(1);
		int w = 84;
		BufferedImage bi = robot.createScreenCapture(new Rectangle(500, 498, w, w));
		ImageIO.write(bi, "jpg", new File(file));
	}

	private static void sleep() {
		USystem.sleepMiliSegundos(75);
	}

	private static void keyNumber(int i) {
		key(KeyEvent.VK_0+i);
	}

	private static void key(int i) {
		sleep();
		robot.keyPress(i); 
		sleep();
		robot.keyRelease(i);
		sleep();
	}

}
