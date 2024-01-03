import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Backend {
    public int totalDistance = 0;
    String departure = "";
    String arrival = "";

    public GraphADT<String, Integer> graph;
    public Backend(GraphADT<String, Integer> graph) {
        this.graph = graph;
    }

    
    public void readData(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.matches("\"[A-Z]+\" -- \"[A-Z]+\" \\[miles=\\d+\\];")) {
                    String[] s = line.split("\"");
                    String start = s[1];
                    String end = s[3];
                    int distance = Integer.parseInt(line.split("=")[1].replaceFirst("\\];", "").trim());

                    graph.insertNode(start);
                    graph.insertNode(end);
                    graph.insertEdge(start, end, distance);
                    totalDistance += distance;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public String getShortestRoute(String start, String destination) {
        departure = start;
        arrival = destination;
        try {
            StringBuilder results = new StringBuilder("Shortest Route: ");
            results.append(String.join(" -> ", getRoute()));
            results.append("\nTotal miles for the route: ").append(getTotalMiles());
            results.append("\nMiles to travel for each segment of the route: ").append(getMilesToTravelEach());


            return results.toString();
        } catch (NoSuchElementException e) {
            return "No path found from " + start + " to " + destination;
        }
    }

    
    public String getDataStatistics() {
        return "Number of Airports(nodes): " + graph.getNodeCount() +
                "\nNumber of Flights(edges): " + graph.getEdgeCount() +
                "\nTotal Miles(sum of weights): " + totalDistance ;

    }

    
    public ArrayList<String> getRoute() {
        DijkstraGraph<String, Integer> dijkstraGraph = (DijkstraGraph<String, Integer>) graph;
        ArrayList<String> shortestPathData = dijkstraGraph.shortestPathData(departure, arrival);
        return shortestPathData;
    }

    
    public ArrayList<Double> getMilesToTravelEach() {
        DijkstraGraph<String, Integer> dijkstraGraph = (DijkstraGraph<String, Integer>) graph;
        ArrayList<Double> shortestPathEdgeCost = dijkstraGraph.shortestPathEdgeCosts(departure, arrival);
        return shortestPathEdgeCost;
    }

    
    public double getTotalMiles() {
        DijkstraGraph<String, Integer> dijkstraGraph = (DijkstraGraph<String, Integer>) graph;
        double totalMiles = dijkstraGraph.shortestPathCost(departure, arrival);
        return totalMiles;
    }
    
    public static void main(String[] args) {
        Backend backend = new Backend(new DijkstraGraph<>(new PlaceholderMap<>()));
        Frontend frontend = new Frontend(backend, new Scanner(System.in));
        frontend.startMainLoop();
    }
}
