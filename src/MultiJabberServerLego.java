/**
 * programma che viene eseguito su EV#. E'un server che lancia due Thread, uno che esege lálgoritmo di cammino (OneJabberLego)
 * e uno che invece riceve i comandi ( TwoJabberLego).
 * I due thread condividono una area condivisa costituita da un oggetto ferma di classe Semaforo ( da modificare in classe Comandi)
 */

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.RCXMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class MultiJabberServerLego {  
  static final int PORT = 9898; 
  static final int PORTCOMMAND = 9899; 
  
  public static void main(String[] args) throws IOException {
    ServerSocket s = new ServerSocket(PORT);
    ServerSocket sc = new ServerSocket(PORTCOMMAND);
    Ev3MobileRobot robot;
    //System.out.println("Server Started");
    Comandi comandi=new Comandi();
    comandi.setFerma(0);
    try {
      //while(true) {
        // Blocks until a connection occurs:
    	LCD.drawString("istanzio robot", 0, 4);
    	robot = new Ev3MobileRobot();
    	//robot.setPowerMotorForward(50);
    	//robot.goForward();
    	LCD.drawString("Pronto per la connessione", 0, 4);
    	// primo socket da dare al Thread OneJabberLego...questo thread sviluppa il cammino del robot e invia il feedback al PC
        Socket socket = s.accept();
        LCD.drawString("CONNESSO!", 0, 4);
        try {
        		new OneJabberLego(socket,comandi,robot);
        } catch(IOException e) {
        		socket.close();
        }
        // altro socket per il secondo Thread...questo thread riceve comandi dal PC
        Socket so = sc.accept();
	    try {
	            new TwoJabberLego(so,comandi);
	    } catch(IOException e) {
	            so.close();
	    }
    } finally {
      s.close();
    }
  } 
} 
 
 