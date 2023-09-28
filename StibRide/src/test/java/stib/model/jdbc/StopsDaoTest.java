package stib.model.jdbc;

import org.junit.Test;
import stib.config.ConfigManager;
import stib.model.Pair;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StopsDaoTest {
    private StopsDto stop;
    private final List<StopsDto> all;
    private StopsDao instance;

    public StopsDaoTest(){
        System.out.println("====StopsDaoTest constructor====");
        stop = new StopsDto(new Pair<>(1, 10), 1);
        all = List.of(
                new StopsDto(new Pair<>(1,10), 1),
                new StopsDto(new Pair<>(2,10), 2),
                new StopsDto(new Pair<>(2,11), 3),
                new StopsDto(new Pair<>(2,13), 4),
                new StopsDto(new Pair<>(1,14), 5)
        );

        try{
            ConfigManager.getInstance().load();
            instance = StopsDao.getInstance();
        }catch(RepositoryException | IOException ex){
            org.junit.jupiter.api.Assertions.fail("Erreur de connection à la base de données de test", ex);
        }
    }

    @Test
    public void testSelectExist() throws Exception{
        //Arrange
        StopsDto expected = stop;
        //Action
        StopsDto result = instance.select(new Pair<>(1, 10));
        //Assert
        assertEquals(result, expected);
    }

    @Test
    public void testSelectNotExist() throws Exception{
        //Arrange
        //Action
        StopsDto result = instance.select(new Pair<>(10, 110));
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectWrongParameter() throws Exception{
        //Arrange
        //Assert
        assertThrows(RepositoryException.class, ()->{
            //Action
            StopsDto result = instance.select(null);
        });
    }

    @Test
    public void testSelectAllExist() throws Exception{
        //Arrange
        List<StopsDto> expected = all;
        //Action
        List<StopsDto> result = instance.selectAll();
        //Assert
        assertEquals(result, expected);
    }

}