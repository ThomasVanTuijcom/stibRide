package stib.model.jdbc;

import stib.model.Pair;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<Pair<Integer, Integer>, StopsDto> {
    Connection connexion;

    public StopsDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StopsDao getInstance() throws RepositoryException{
        return StopsDaoHolder.getInstance();
    }

    @Override
    public List<StopsDto> selectAll() throws RepositoryException {
        List<StopsDto> stops = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();
            String sql = "SELECT * " +
                    "FROM STOPS s " +
                    "ORDER BY id_line, id_order";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Integer line = result.getInt("id_line");
                Integer station_id = result.getInt("id_station");
                Integer order = result.getInt("id_order");
                Pair<Integer, Integer> key = new Pair<>(line, station_id);
                StopsDto stop = new StopsDto(key, order);
                stops.add(stop);
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
        return stops;
    }

    @Override
    public StopsDto select(Pair<Integer, Integer> pair) throws RepositoryException {
        if (pair == null){
            throw new RepositoryException("Aucune clé n'est donnée");
        }
        try {
            String sql = """
                    SELECT * 
                    FROM STOPS s 
                    WHERE id_line = ? AND id_station = ? 
                    """;
            PreparedStatement prestmt = connexion.prepareStatement(sql);
            prestmt.setInt(1, pair.getFirst());
            prestmt.setInt(2, pair.getSecond());
            ResultSet result = prestmt.executeQuery();

            while(result.next()){
                Integer line = result.getInt("id_line");
                Integer station_id = result.getInt("id_station");
                Integer order = result.getInt("id_order");
                Pair<Integer, Integer> key = new Pair<>(line, station_id);
                return new StopsDto(key, order);
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
        return null;
    }

    private static class StopsDaoHolder{
        private static StopsDao getInstance() throws RepositoryException{
            return new StopsDao();
        }
    }
}
