package DiGraph_A5;

import java.util.HashMap;



public class Vertex{
	long id;
	String label;
	public Vertex(long id, String label) {
		this.id = id;
		this.label = label;
	}
	HashMap<String,Edge> edgesIn = new HashMap<String,Edge>();
	HashMap<String,Edge> edgesOut = new HashMap<String,Edge>();
	
	
	public long getID() {
		return id;
	}
	
	
	public String getLabel() {
		return label;
	}
	
	
	public void addIn(String s, Edge e) {
		edgesIn.put(s, e);
	}
	
	public void delIn(String s) {
		edgesIn.remove(s);
	}
	
	public void addOut(String s, Edge e) {
		edgesOut.put(s, e);
	}
	
	public void delOut(String s) {
		edgesOut.remove(s);
	}
	
	public int getInDegree() {
		return edgesIn.size();
	}


}
