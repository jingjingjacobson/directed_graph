package DiGraph_A5;

public class DiGraphPlayground {

	public static void main (String[] args) {

		// thorough testing is your responsibility
		//
		// you may wish to create methods like 
		//    -- print
		//    -- sort
		//    -- random fill
		//    -- etc.
		// in order to convince yourself your code is producing
		// the correct behavior
		exTest();
	}

	public static void exTest(){
		DiGraph d = new DiGraph();
		d.addNode(1, "a");
		d.addNode(2, "b");
		d.addNode(3, "c");
		d.addNode(4, "d");
		d.addNode(5, "e");
		d.addNode(6, "f");
		d.addEdge(1, "d", "b", 0, null);
		d.addEdge(2, "d", "c", 0, null);
		d.addEdge(3, "e", "c", 0, null);
		d.addEdge(4, "e", "f", 0, null);
		d.addEdge(5, "a", "f", 0, null);
		d.addEdge(6, "b", "a", 0, null);
		
		
	/*	
		d.addNode(1, "f");
		d.addNode(3, "s");
		d.addNode(7, "t");
		d.addNode(0, "fo");
		d.addNode(4, "fi");
		d.addNode(6, "si");
		d.addEdge(0, "f", "s", 0, null);
		d.addEdge(1, "f", "si", 0, null);
		d.addEdge(2, "s", "t", 0, null);
		d.addEdge(3, "fo", "fi", 0, null);
		d.addEdge(4, "fi", "si", 0, null);  */
		System.out.println("numEdges: "+d.numEdges());
		System.out.println("numNodes: "+d.numNodes()); 
		printTOPO(d.topoSort());

	}
	public static void printTOPO(String[] toPrint){
		System.out.print("TOPO Sort: ");
		for (String string : toPrint) {
			System.out.print(string+" ");
		}
		System.out.println();
	}

}