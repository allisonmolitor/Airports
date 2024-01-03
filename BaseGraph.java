import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class BaseGraph<NodeType, EdgeType extends Number> {

    protected class Node {
        public NodeType data;
        public List<Edge> edgesLeaving = new LinkedList<>();
        public List<Edge> edgesEntering = new LinkedList<>();

        public Node(NodeType data) {
            this.data = data;
        }
    }

    protected MapADT<NodeType, Node> nodes = null;

    protected class Edge {
        public EdgeType data; 
        public Node predecessor;
        public Node successor;

        public Edge(EdgeType data, Node pred, Node succ) {
            this.data = data;
            this.predecessor = pred;
            this.successor = succ;
        }
    }

    protected int edgeCount = 0;

    public BaseGraph(MapADT<NodeType, Node> map) {
        this.nodes = map;
    }


    public boolean insertNode(NodeType data) {
        if (nodes.containsKey(data))
            return false; // throws NPE when data's null
        nodes.put(data, new Node(data));
        return true;
    }

    public boolean removeNode(NodeType data) {
        if (!nodes.containsKey(data))
            return false; 
        Node oldNode = nodes.remove(data);
        for (Edge edge : oldNode.edgesLeaving)
            edge.successor.edgesEntering.remove(edge);
        for (Edge edge : oldNode.edgesEntering)
            edge.predecessor.edgesLeaving.remove(edge);
        return true;
    }

    public boolean containsNode(NodeType data) {
        return nodes.containsKey(data);
    }

    public int getNodeCount() {
        return nodes.getSize();
    }

    public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight) {
        Node predNode = nodes.get(pred);
        Node succNode = nodes.get(succ);
        if (predNode == null || succNode == null)
            return false;
        try {
            Edge existingEdge = getEdgeHelper(pred, succ);
            existingEdge.data = weight;
        } catch (NoSuchElementException e) {
            Edge newEdge = new Edge(weight, predNode, succNode);
            this.edgeCount++;
            predNode.edgesLeaving.add(newEdge);
            succNode.edgesEntering.add(newEdge);
        }
        return true;
    }


    public boolean removeEdge(NodeType pred, NodeType succ) {
        try {
            Edge oldEdge = getEdgeHelper(pred, succ);
            oldEdge.predecessor.edgesLeaving.remove(oldEdge);
            oldEdge.successor.edgesEntering.remove(oldEdge);
            this.edgeCount--;
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean containsEdge(NodeType pred, NodeType succ) {
        try {
            getEdgeHelper(pred, succ);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public EdgeType getEdge(NodeType pred, NodeType succ) {
        return getEdgeHelper(pred, succ).data;
    }

    protected Edge getEdgeHelper(NodeType pred, NodeType succ) {
        Node predNode = nodes.get(pred);
        for (Edge edge : predNode.edgesLeaving)
            if (edge.successor.data.equals(succ))
                return edge;
        throw new NoSuchElementException("No edge from " + pred.toString() + " to " +
                succ.toString());
    }

    public int getEdgeCount() {
        return this.edgeCount;
    }

}
