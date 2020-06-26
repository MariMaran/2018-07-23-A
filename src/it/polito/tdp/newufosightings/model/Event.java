package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event> {
	
	public enum EventType {
		INCREMENTO, DECREMENTO
	}
	
  LocalDateTime dataOra;
  StatoAllerta stato;
  EventType tipo;
  
public Event(LocalDateTime dataOra, StatoAllerta stato, EventType tipo) {
	super();
	this.dataOra = dataOra;
	this.stato = stato;
	this.tipo = tipo;
}

@Override
public int compareTo(Event arg0) {
	return this.dataOra.compareTo(arg0.dataOra);
}
  
  
  
}
