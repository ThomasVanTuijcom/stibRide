package stib.model.jdbc;

import stib.model.Pair;
import stib.model.dto.StationDto;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDao implements Dao<Integer, StationDto> {
    Connection connexion;

    public StationDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static StationDao getInstance() throws RepositoryException{
        return StationDaoHolder.getInstance();
    }

    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        List<StationDto> stations = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();
            String sql = "SELECT * " +
                    "FROM STATIONS s";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Integer key = result.getInt("id");
                String station_name = result.getString("name");
                StationDto station = new StationDto(key, station_name);
                stations.add(station);
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
        return stations;
    }

    @Override
    public StationDto select(Integer key) throws RepositoryException {
        if (key == null){
            throw new RepositoryException("Aucune clé n'est donnée");
        }
        try {
            String sql = """
                    SELECT * 
                    FROM STATIONS 
                    WHERE id = ? 
                    """;
            PreparedStatement prestmt = connexion.prepareStatement(sql);
            prestmt.setInt(1, key);
            ResultSet result = prestmt.executeQuery();

            while(result.next()){
                Integer station_id = result.getInt("id");
                String station_name = result.getString("name");
                return new StationDto(station_id, station_name);
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
        return null;
    }

    private static class StationDaoHolder{
        private static StationDao getInstance() throws RepositoryException{
            return new StationDao();
        }
    }
}

