package stib.model;

import stib.model.dto.StationDto;

import java.util.*;

public class Node {
    private StationDto station;
    private List<Integer> lines = new ArrayList<>();

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;
    private Map<Node, Integer> neighbours = new HashMap<>();

    public Node(StationDto station){
        this.station = station;
    }

    public void addNeighbour(Node node, int distance){
        this.neighbours.put(node, distance);
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getNeighbours() {
        return neighbours;
    }

    public void addLine(Integer num){
        lines.add(num);
    }

    public String getStation(){
        return this.station.toString();
    }

    public String getLines(){
        String s = "[";
        s += lines.get(0) + "";
        for (int i = 1; i < lines.size(); i++){
            s += ", " + lines.get(i);
        }
        s += "]";
        return s;
    }
}
