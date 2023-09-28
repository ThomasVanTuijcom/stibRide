package stib.model.jdbc;

import stib.config.ConfigManager;
import stib.model.exceptions.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private Connection connection;

    public DBManager(){
    }

    Connection getConnection() throws RepositoryException{
        String dbUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        if (connection == null){
            try{
                connection = DriverManager.getConnection(dbUrl);
            }catch(SQLException ex){
                throw new RepositoryException("Connexion impossible à la base de donnée");
            }
        }
        return connection;
    }

    public static DBManager getInstance(){
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder{
        private static final DBManager INSTANCE = new DBManager();
    }
}
