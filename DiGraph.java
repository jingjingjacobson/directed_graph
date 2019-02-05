package DiGraph_A5;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
// import java.util.LinkedList;


public class DiGraph implements DiGraphInterface {
	HashMap edgeMap;
	HashMap labelMap;
	HashMap idMap;
	HashSet ids;

	// in here go all your data and methods for the graph
	// and the topo sort operation

	public DiGraph ( ) { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
		HashMap<String,Edge> edgeMap = new HashMap<String,Edge>();
		HashMap<String,Vertex> labelMap = new HashMap<String,Vertex>();
		HashMap<String,Vertex> idMap = new HashMap<String,Vertex>();
		HashSet<Long> ids = new HashSet<Long>();

		this.edgeMap = edgeMap;
		this.labelMap = labelMap;
		this.idMap = idMap;
		this.ids = ids;


	}

	@Override
	public boolean addNode(long idNum, String label) {
		if(idNum >= 0 && labelMap.get(label) == null && idMap.get(Long.toString(idNum)) == null) {
			Vertex node = new Vertex(idNum, label);
			labelMap.put(label,node);
			idMap.put(Long.toString(idNum), node);
			return true;
		}
		else {
			return false;
		}


	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		Vertex source = (Vertex) labelMap.get(sLabel);
		Vertex destination = (Vertex) labelMap.get(dLabel);

		if (source == null || destination == null) {
			return false;
		}

		String sourceID = Long.toString(source.getID());
		String destinationID = Long.toString(destination.getID());

		String label = sourceID + "->" + destinationID;



		if(idNum >= 0 && edgeMap.get(label) == null && !ids.contains(idNum)) {


			Edge newEdge = new Edge(idNum,source,destination,weight,eLabel);
			edgeMap.put(label, newEdge);
			ids.add(idNum);

			source.addOut(label,newEdge);
			destination.addIn(label,newEdge);


			return true;

		}
		else {
			return false;
		}
	}

	@Override
	public boolean delNode(String label) {
		if(labelMap.get(label) == null) {
			return false;
		}
		else {
			Vertex v = (Vertex) labelMap.get(label); // this node
			String id = Long.toString(v.getID()); // vertex id

			Iterator iterIn = v.edgesIn.entrySet().iterator(); // iterators


			while(iterIn.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)iterIn.next();
				String key = (String) pair.getKey(); // get edge key
				Edge e = (Edge) edgeMap.get(key); // get the edge from edgeMap
				delEdge(e.getSLabel(), e.getDLabel()); // delete edge



				//				iterIn.remove();
			}

			Iterator iterOut = v.edgesOut.entrySet().iterator();
			while(iterOut.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)iterOut.next();
				String key = (String) pair.getKey();
				Edge e = (Edge) edgeMap.get(key);
				delEdge(e.getSLabel(), e.getDLabel());

				//				iterOut.remove();
			}

			labelMap.remove(label); // remove node from the labelMap and idMap
			idMap.remove(id);

			return true;
		}
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		Vertex source = (Vertex) labelMap.get(sLabel); // edge source
		Vertex destination = (Vertex) labelMap.get(dLabel); // edge destination

		if (source == null || destination == null) {
			return false;
		}

		String key = Long.toString(source.getID()) + "->" + Long.toString(destination.getID()); // this will be the edge key, which is just "sLabel -> dLabel"


		if(edgeMap.get(key) == null) {
			return false;
		}
		else {
			Edge e = (Edge) edgeMap.get(key);
			edgeMap.remove(key);
			source.delOut(key);
			destination.delIn(key);
			ids.remove(e.id);
			return true;
		}
	}

	@Override
	public long numNodes() {
		return labelMap.size();
	}

	@Override
	public long numEdges() {
		return edgeMap.size();
	}

/*
	public boolean topoSortDFS(Vertex v, Stack sorted, HashSet visited, HashSet active) {

		if(active.contains(v)) {
			return false;
		}
		else if(visited.contains(v)) {
			return true;
		}

		visited.add(v);
		active.add(v);
		Iterator iterDFS = v.edgesOut.entrySet().iterator();
		while(iterDFS.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)iterDFS.next();
			Edge e = (Edge) pair.getValue();
			Vertex child = e.destination;
			if(!visited.contains(child)) {
				return topoSortDFS(child, sorted, visited, active);
			}

		}
		//		active.clear();
		active.remove(v);
		sorted.push(v);
		return true;
	}

*/

	@Override
	public String[] topoSort() {
				int visitedNodes = 0;
		String sorted[] = new String[(int)numNodes()];
		Queue<Vertex> q = new LinkedList<Vertex>();
		HashMap<Vertex,Integer> inDegrees = new HashMap<Vertex,Integer>();
		Iterator iterVertex = labelMap.entrySet().iterator();
		while(iterVertex.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)iterVertex.next();
			Vertex v = (Vertex) pair.getValue(); 
			inDegrees.put(v, v.getInDegree());
			if(v.getInDegree() == 0) {
				q.add(v);
			}


		}
		while(!q.isEmpty()) {
			Vertex v = q.poll();
			sorted[visitedNodes] = v.getLabel();
			visitedNodes += 1;
			Iterator neighbors = v.edgesOut.entrySet().iterator();
			while(neighbors.hasNext()) {
				HashMap.Entry pair = (HashMap.Entry)neighbors.next();
				Edge nextEdge = (Edge) pair.getValue();
				Vertex neighborNode = nextEdge.destination;
				int newInDegree = inDegrees.get(neighborNode) - 1;
				inDegrees.remove(neighborNode);

				if(newInDegree > 0) {
					inDegrees.put(neighborNode, newInDegree);
				}
				else {
					q.add(neighborNode);
				}
			}
		}
		if(visitedNodes != numNodes()) {
			return null;
		}
		else {

			return sorted;

		}
	}

} /* 
		boolean valid = true;
		Stack sorted = new Stack();
		HashSet<Vertex> visited = new HashSet<Vertex>();
		HashSet<Vertex> active = new HashSet<Vertex>();
		Iterator iterVertex = labelMap.entrySet().iterator();

		while(iterVertex.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry)iterVertex.next();
			Vertex v = (Vertex) pair.getValue(); 
			valid = topoSortDFS(v, sorted, visited, active);
			if(!valid) {
				return null;
			}

		}

		String sort[] = new String[(int) numNodes()];
		for(int i = 0; i < numNodes(); i++) {
			Vertex v = (Vertex) sorted.pop();
			sort[i] = v.getLabel();
			System.out.println(sort[i]);

		}
		return sort;

	} 
}


*/
// rest of your code to implement the various operations

