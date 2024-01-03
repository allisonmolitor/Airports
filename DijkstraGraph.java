import java.util.PriorityQueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {


    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

 
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        
        MapADT<NodeType, Node> visited =new PlaceholderMap<>();
        PriorityQueue<SearchNode> Q = new PriorityQueue<>();
        Q.add(new SearchNode(nodes.get(start), 0, null));
        
        while ( !Q.isEmpty()) {
            
          SearchNode searchCurrent = Q.poll();
          Node node = searchCurrent.node;
          if (visited.containsKey(node.data )) {
            continue; 
          }
          visited.put(node.data, node);
          if(node.data.equals(end)) {
            return searchCurrent;
            
            
          }
          
          Iterator<Edge> it = node.edgesLeaving.iterator();
          for (int i =0; i < node.edgesLeaving.size(); i++ ) {
            Edge edgy = it.next();
            Node next = edgy.successor;
            double cost = searchCurrent.cost+ edgy.data.doubleValue();
            if(!visited.containsKey(next.data)) {
                
              Q.add(new SearchNode(next, cost,searchCurrent )); 
    
            }
              
            
          }
          
          
        }
        throw new NoSuchElementException("no path from start to end was found or either start or end data do not correspond to a graph node");
        
        
      }

    public ArrayList<NodeType> shortestPathData(NodeType start, NodeType end) {
        
        try {
        
        SearchNode endSearch = computeShortestPath(start, end);
        ArrayList<NodeType> P = new ArrayList<>();
        
        SearchNode search = endSearch;
        while(search !=null) {
          P.add(0, search.node.data);
          search = search.predecessor; 
        }

        return P;
        } 
        catch (NoSuchElementException e) {
            throw e;
            
        }
	}

    public double shortestPathCost(NodeType start, NodeType end) {
        try { 
        SearchNode endnode = computeShortestPath(start, end);
        return endnode.cost;
        
        } 
        catch (NoSuchElementException e) {
            throw e;
        }
        
        
    }
    public ArrayList<Double> shortestPathEdgeCosts(NodeType start, NodeType end) {
        try {
            SearchNode endnode = computeShortestPath(start, end);
            ArrayList<Double> edge =new ArrayList<>();

            SearchNode node = endnode;
            while (node.predecessor != null) {
                double edgeCost = node.cost - node.predecessor.cost;
                edge.add(0,edgeCost);
                node = node.predecessor;
            }

            return edge;
        } catch (NoSuchElementException e){
            throw e;
        }
    }
    
}
