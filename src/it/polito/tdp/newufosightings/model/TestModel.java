package it.polito.tdp.newufosightings.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Model m=new Model();

m.creaGrafo(1970, "light");
System.out.println(m.grafo.edgeSet());
System.out.println(m.getPesoCompl("IL"));
m.chiamaSimulatore();
	}

}
