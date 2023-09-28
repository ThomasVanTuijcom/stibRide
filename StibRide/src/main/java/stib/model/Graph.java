package stib.model;

import stib.model.dto.StationDto;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;
import stib.model.repository.StationRepository;
import stib.model.repository.StopsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, Node> nodes = new HashMap<>();

    public Graph() {
        try {
            StationRepository stationRepository = new StationRepository();
            StopsRepository stopsRepository = new StopsRepository();
            List<StationDto> stations = stationRepository.getAll();
            List<StopsDto> stops = stopsRepository.getAll();
            for (StationDto station : stations) {
                Node noeud = new Node(station);
                nodes.put(station.getKey(), noeud);
            }
            for (int i = 0; i < stations.size(); i++) {
                for (int j = 0; j < stops.size(); j++) {
                    //Si la station courante n'est pas celle du stop
                    if(stations.get(i).getKey().equals(stops.get(j).getKey().getSecond())){
                        StationDto station = stations.get(i);
                        Integer line = stops.get(j).getKey().getFirst();
                        Node currentNode = nodes.get(station.getKey());
                        currentNode.addLine(line);
                        if (j - 1 >= 0 && stops.get(j-1).getKey().getFirst().equals(line)){
                            Integer id_station = stops.get(j - 1).getKey().getSecond();
                            currentNode.addNeighbour(nodes.get(id_station), 1);
                        }
                        if (j + 1 < stops.size() && stops.get(j+1).getKey().getFirst().equals(line)){
                            Integer id_station = stops.get(j + 1).getKey().getSecond();
                            currentNode.addNeighbour(nodes.get(id_station), 1);
                        }
                    }
                }
            }
        } catch (RepositoryException ex) {
            System.out.println("Erreur création graphe: " + ex.getMessage());
        }
    }
    public void resetNodes(){
        for (Map.Entry<Integer, Node> entry : nodes.entrySet()){
            entry.getValue().setDistance(Integer.MAX_VALUE);
            entry.getValue().getShortestPath().clear();
        }
    }

    public Node getNode(Integer key){
        return nodes.get(key);
    }
}
