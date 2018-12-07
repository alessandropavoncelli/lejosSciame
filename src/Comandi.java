// oggetto condiviso che contiene i comandi

public class Comandi {
	private int ferma=0;
	private char direzione;
	private int centrale;
	
	public int getCentrale() {
		return centrale;
	}

	public void setCentrale(int centrale) {
		this.centrale = centrale;
	}

	public char getDirezione() {
		return direzione;
	}

	public void setDirezione(char direzione) {
		this.direzione = direzione;
	}

	public int getFerma() {
		return ferma;
	}

	public void setFerma(int ferma) {
		this.ferma = ferma;
	}
	

}
