package it.polito.tdp.newufosightings.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
NewUfoSightingsDAO dao;
List<State> stati;
Graph<State, DefaultWeightedEdge> grafo;
Map<String, State> idMap;
Integer anno;
String forma;

public Model() {
	dao=new NewUfoSightingsDAO();
}

public List<String> getForme(int anno){
	return dao.getFormeAnno(anno);
}

public void creaGrafo(int anno, String forma) {
	this.anno=anno;
	this.forma=forma;
	List<Arco> archiDopp;
	idMap=new TreeMap();
	stati=dao.loadAllStates();
	grafo=new SimpleWeightedGraph(DefaultWeightedEdge.class);
	for(State s: stati) {
		grafo.addVertex(s);
		idMap.put(s.getId(),s);
	}
	archiDopp=dao.getArchi(anno, forma, idMap);
	for(Arco a: archiDopp) {
	
		if(!grafo.containsEdge(a.s1, a.s2)&& grafo.containsVertex(a.s1)&&grafo.containsVertex(a.s2)) {
			DefaultWeightedEdge d=grafo.addEdge(a.s1, a.s2);
			grafo.setEdgeWeight(d, a.getPeso());
		}
	}
	
}

public double getPesoCompl(String s) {
	State s2=idMap.get(s);
	double somma=0.0;
	for(DefaultWeightedEdge d: grafo.edgesOf(s2)) {
		somma+=grafo.getEdgeWeight(d);
	}
	return somma;
}

public void chiamaSimulatore() {
	Simulatore s=new Simulatore();
	s.init(anno, forma, grafo, idMap);
	s.simula();
	System.out.println(s.ris);
}


}
