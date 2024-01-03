import java.util.List;

public interface GraphADT<NodeType, EdgeType extends Number> {
    
    public boolean insertNode(NodeType data);

    public boolean removeNode(NodeType data);


    public boolean containsNode(NodeType data);


    public int getNodeCount();


    public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight);

    
    public boolean removeEdge(NodeType pred, NodeType succ);


    public boolean containsEdge(NodeType pred, NodeType succ);


    public EdgeType getEdge(NodeType pred, NodeType succ);


    public int getEdgeCount();


    public List<NodeType> shortestPathData(NodeType start, NodeType end);


    public double shortestPathCost(NodeType start, NodeType end);

}