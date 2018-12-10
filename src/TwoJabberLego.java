//A server that uses multithreading to handle 
//any number of clients.
import java.io.*;
import java.net.*;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.RCXMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

class TwoJabberLego extends Thread {
	private Socket conn;
	private static BufferedReader in;
	private static PrintWriter out;
	private  RCXMotor a,b,c;
	private Comandi comando;
	TwoJabberLego(Socket s, Comandi comando) throws IOException {
		this.comando = comando;
		conn = s;
		in = 
				new BufferedReader(
						new InputStreamReader(
								conn.getInputStream()));
		// Enable auto-flush:
		out = 
				new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(
										conn.getOutputStream())), true);
		
		start(); // Calls run()
	}
	public void run() {
		  	int tempo = 0;
		  	String messaggio=new String("");
			try {
				in =new BufferedReader(new InputStreamReader (conn.getInputStream()));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			LCD.drawString("attendo comandi", 0, 4);
			this.comando.setNuovoComando(false);
			while(true) {
				try {
					messaggio = in.readLine();
					LCD.drawString("arrivato messaggio!", 0, 4);
					//LCD.drawString(messaggio,0,6);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LCD.drawString("ERRORRRRE",0,6);
					e.printStackTrace();
				}
				LCD.drawString("aaaaaaaaaaaaaaaaaaaaaa",0,4);
				if(messaggio.equals("stop")) {
					LCD.drawString("mi fermo     ",0,6);
					this.comando.setNuovoComando(true);
					this.comando.setFerma(1);
				}
				if(messaggio.equals("sx")) {
					LCD.drawString("giro a sx  ",0,6);
					this.comando.setNuovoComando(true);
					this.comando.setDirezione('s');	
				}
				if(messaggio.equals("dx")) {
					LCD.drawString("giro a dx  ",0,6);
					this.comando.setNuovoComando(true);
					this.comando.setDirezione('d');	
				}
				if(messaggio.equals("vai")) {
					LCD.drawString("vado       ",0,6);
					this.comando.setNuovoComando(true);
					this.comando.setFerma(0);	
					this.comando.setDirezione('a');	
				}
				if(messaggio.equals("end")) {
					LCD.drawString("fine       ",0,6);
					this.comando.setNuovoComando(true);
					this.comando.setFerma(1);
					this.comando.setInterrompi(true);
					
				}
			}
	}
	
}


