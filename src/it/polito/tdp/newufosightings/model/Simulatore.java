package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;
import it.polito.tdp.newufosightings.model.Event.EventType;

public class Simulatore {

	
	int T1=200;
	int alfa=50;
	Graph<State, DefaultWeightedEdge> grafo;
	NewUfoSightingsDAO dao;
	
	PriorityQueue<Event> coda;
	List<StatoAllerta> ris;
	
	public Simulatore() {
		dao=new NewUfoSightingsDAO();
	}
	
	public void init(int anno, String forma, Graph<State, DefaultWeightedEdge> grafo, Map<String, State> idMap) {
		coda=new PriorityQueue();
		ris=new ArrayList();
		this.grafo=grafo;
		List<Sighting> list=dao.loadSightings(anno, forma);
		for(Sighting s: list) {
			StatoAllerta sa=new StatoAllerta(idMap.get(s.getState().toUpperCase()));
			Event e=new Event(s.getDatetime(),sa,EventType.DECREMENTO);
			coda.add(e);
		}
		
		
	}
	
	public void simula() {
		while(!coda.isEmpty()) {
			Event e=coda.poll();
			this.processEvent(e);
		}
	}

	private void processEvent(Event e) {

     switch(e.tipo) {
     case DECREMENTO:
    	 e.stato.decrementa();
    	 Event nuovo=new Event(e.dataOra.plusDays(T1),e.stato,EventType.INCREMENTO);
    	 coda.add(nuovo);
    	 System.out.println("Decremento "+e.stato);
    	 ris.add(e.stato);
    	 break;
    	 
     case INCREMENTO:
    	 e.stato.incrementa();
    	 ris.add(e.stato);
    	 System.out.println("Incremento "+ e.stato);
    	 break;
     }
		
	}
}
