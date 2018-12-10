public class Comandi {
	private int ferma=0;
	private char direzione;
	private int centrale;
	private boolean nuovoComando=false;
	private boolean interrompi=false;
	
	public boolean isInterrompi() {
		return interrompi;
	}

	public void setInterrompi(boolean interrompi) {
		this.interrompi = interrompi;
	}

	public boolean isNuovoComando() {
		return nuovoComando;
	}

	public void setNuovoComando(boolean nuovoComando) {
		this.nuovoComando = nuovoComando;
	}

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