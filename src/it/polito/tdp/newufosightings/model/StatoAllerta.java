package it.polito.tdp.newufosightings.model;

public class StatoAllerta {
	State stato;
	Integer allerta;
	public StatoAllerta(State stato) {
		super();
		this.stato = stato;
		this.allerta=5;
	}
	public void decrementa() {
		if(allerta>1)
			allerta--;
	}
	
	public void incrementa() {
		if(allerta<5)
			allerta++;
	}
	@Override
	public String toString() {
		return "StatoAllerta [stato=" + stato + ", allerta=" + allerta + "]";
	}
	

}
