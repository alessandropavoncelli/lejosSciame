// A server that uses multithreading to handle 
// any number of clients.
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

class OneJabberLego extends Thread {
  private Socket conn;
  private static BufferedReader in;
  private static PrintWriter out;
  private  static RCXMotor a,b,c;
  private Comandi comando ;
  public OneJabberLego(Socket s, Comandi ferma) throws IOException {
	c=new RCXMotor(MotorPort.C);
	a=new RCXMotor(MotorPort.A);
	b=new RCXMotor(MotorPort.B);
	this.comando = ferma;
	this.comando.setFerma(0);
    conn = s;
    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())), true);
    start(); // Calls run()
  }
  @SuppressWarnings("deprecation")
public void run() {
	  	int tempo = 0;
	  	String messaggio=new String();
		String response="";
		PrintWriter p=null;
		int numGiriDirezione=10;
		int numGiri=0;
		char direzione ='s';
		EV3TouchSensor toccoSensor;
		EV3UltrasonicSensor sonar;
		//LCD.drawString("in attesa di start", 0, 4);
		// setto touch
		toccoSensor = new EV3TouchSensor(SensorPort.S1);
		/// setto ultrasonic	
		sonar = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider distanceMode = sonar.getDistanceMode();
		float []value=new float[distanceMode.sampleSize()];
		float[] sampletouch = new float[toccoSensor.sampleSize()];
		
	    try {
	    	    LCD.drawString("PROGRAMMA INIZIATO", 0, 4);
				//p=new PrintWriter(conn.getOutputStream());
	    	    this.comando.setInterrompi(false);
	    	    this.comando.setFerma(0);
	    	    vai();
				do {
					
					// leggo dal sensore di tocco ( terminazione)
					toccoSensor.getTouchMode();
					toccoSensor.fetchSample(sampletouch, 0);
					// leggo dal sonar
					sonar.fetchSample(value, 0);
					// costruisco output
					for(int i=0;i<distanceMode.sampleSize();i++) {
						response=response+new Float(value[i]).toString()+"-";;	
					}
					//LCD.drawString("xxxxxxFINO QUI 3", 0, 4);
					//p.println(response);
					response="";
					//p.flush();
					// se si avvicina a qualcosa cambia altrimenti va dritto
					if(value[0]<0.1) {
						//stop();
						if(this.comando.getFerma()!=1) {
							if (direzione=='s')
								giraSx();
							else
								giraDx();
							numGiri++;
							//tempo di curvatura, per tutto questo tempo rimane attivo il comando
							Delay.msDelay(100);
							// dopo un po'se retrocede
							if (numGiri  > numGiriDirezione) {
								retrocedi();
								Delay.msDelay(2000);
								numGiri=0;
							}
						}
						else
							stoppati();
					}
					// nel caso non veda oggetti 
					else {
						if(this.comando.getFerma()==0)
							vai();
						else	
							stoppati();
						//setta la direzione a muzzo per la prossima occasione di prossimitá con un oggetto ( if) 
						if(Math.random() > 0.5)
							direzione = 's';
						else
							direzione = 'd';
						numGiri=0;
					}
					//se ricevuto un comando..
					if(comando.isNuovoComando()){
						direzione = this.comando.getDirezione();
						this.comando.setNuovoComando(false);;
					}
					Delay.msDelay(30);	
					//LCD.drawString("hhhhhhhhhhhhhhhhhhhhhhh", 0, 4);
					// si ferma quando tocca
				}while((sampletouch[0]==0)&&(this.comando.isInterrompi()==false));	
				
			
		} catch (Exception e) {
			p.close();
			try {
					conn.close();
					stoppati();
					//socket.close();
			} catch (IOException e1) {
					// TODO Auto-generated catch block
					LCD.drawString("ERROR!!!", 0, 4);
					e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		sonar.close();
		stoppati();
		////toccoSensor.close();
		LCD.drawString(messaggio, 0, 4);
		//Delay.msDelay(10000);
		/////
	  }

static void  vai() {
	
	c.setPower(50);
	b.setPower(50);
	
}
static void  stoppati() {
	
	b.setPower(0);
	c.setPower(0);
}

static void  giraDx() {

	b.setPower(30);
	c.setPower(0);
}
static void  giraSx() {
	
	b.setPower(0);
	c.setPower(30);
}
static void  retrocedi() {
	
	b.setPower(-50);
	c.setPower(-50);
}
}





