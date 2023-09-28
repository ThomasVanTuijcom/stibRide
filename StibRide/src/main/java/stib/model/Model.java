package stib.model;

import stib.model.dto.FavoriDto;
import stib.model.dto.StationDto;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;
import stib.model.repository.FavoriRepository;
import stib.model.repository.StationRepository;
import stib.model.repository.StopsRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {
    private Graph graph;
    private StationRepository stationRepository;
    private FavoriRepository favoriRepository;
    public Model(){
        graph = new Graph();
        try {
            stationRepository = new StationRepository();
            favoriRepository = new FavoriRepository();
        }catch(Exception ex){
            System.out.println("Erreur lors du création du model: " + ex.getMessage());
        }
    }

    public void calculateShortestPathFromSource(Node source, Node destination) {
        graph.resetNodes();
        source.setDistance(0);

        List<Node> settledNodes = new ArrayList<>();
        List<Node> unsettledNodes = new ArrayList<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            if(currentNode == destination){
                break;
            }

            for (Map.Entry<Node, Integer> adjacencyPair: currentNode.getNeighbours().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private Node getLowestDistanceNode(List<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public List<StationDto> getAllStations() throws RepositoryException{
        return stationRepository.getAll();
    }

    public List<FavoriDto> getAllFavorites() throws RepositoryException{
        return favoriRepository.getAll();
    }

    public Node getGraphNode(Integer key){
        return graph.getNode(key);
    }
}
