package stib.model.jdbc;

import stib.model.dto.FavoriDto;
import stib.model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriDao implements Dao<String, FavoriDto> {
    Connection connexion;

    public FavoriDao() throws RepositoryException {
        connexion = DBManager.getInstance().getConnection();
    }

    public static FavoriDao getInstance() throws RepositoryException{
        return FavoriDaoHolder.getInstance();
    }

    @Override
    public List<FavoriDto> selectAll() throws RepositoryException {
        List<FavoriDto> favoris = new ArrayList<>();
        try {
            Statement stmt = connexion.createStatement();
            String sql = "SELECT * " +
                    "FROM FAVORIS s ";
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                String name = result.getString("name");
                Integer origin = result.getInt("id_origin");
                Integer destination = result.getInt("id_destination");
                FavoriDto favori = new FavoriDto(name, origin, destination);
                favoris.add(favori);
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
        return favoris;
    }

    @Override
    public FavoriDto select(String key) throws RepositoryException {
        if (key == null){
            throw new RepositoryException("Aucune clé n'est donnée");
        }
        try {
            String sql = """
                    SELECT * 
                    FROM FAVORIS s 
                    WHERE name = ?
                    """;
            PreparedStatement prestmt = connexion.prepareStatement(sql);
            prestmt.setString(1, key);
            ResultSet result = prestmt.executeQuery();

            while(result.next()){
                String name = result.getString("name");
                Integer origin = result.getInt("id_origin");
                Integer destination = result.getInt("id_destination");
                return new FavoriDto(name, origin, destination);
            }
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
        return null;
    }

    public void insert(FavoriDto item) throws  RepositoryException{
        if (item == null){
            throw new RepositoryException("L'item est rien");
        }
        try{
            String query = "INSERT INTO FAVORIS VALUES (?, ?, ?)";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, item.getKey());
            stmt.setInt(2, item.getOrigin());
            stmt.setInt(3, item.getDestination());
            stmt.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void delete(String key) throws  RepositoryException{
        if (key == null){
            throw new RepositoryException("La clé est rien");
        }
        try{
            String query = "DELETE FROM FAVORIS WHERE name = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, key);
            stmt.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void update(FavoriDto item) throws  RepositoryException{
        if (item == null){
            throw new RepositoryException("L'item est rien");
        }
        try{
            String query = "UPDATE FAVORIS SET id_origin = ?, id_destination = ? WHERE name = ?";
            PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setInt(1, item.getOrigin());
            stmt.setInt(2, item.getDestination());
            stmt.setString(3, item.getKey());
            stmt.executeUpdate();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private static class FavoriDaoHolder{
        private static FavoriDao getInstance() throws RepositoryException{
            return new FavoriDao();
        }
    }
}
