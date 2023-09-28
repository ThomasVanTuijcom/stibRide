package stib.model.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import stib.config.ConfigManager;
import stib.model.Pair;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;
import stib.model.jdbc.StopsDao;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopsRepositoryTest {
    @Mock
    private StopsDao mock;
    private StopsDto stop;
    private final List<StopsDto> all;

    public StopsRepositoryTest() {
        System.out.println("====StopsDaoTest constructor====");
        stop = new StopsDto(new Pair<>(1, 10), 1);
        all = List.of(
                new StopsDto(new Pair<>(1, 10), 1),
                new StopsDto(new Pair<>(2, 10), 2),
                new StopsDto(new Pair<>(2, 11), 3),
                new StopsDto(new Pair<>(2, 13), 4),
                new StopsDto(new Pair<>(1, 14), 5)
        );
    }

    @BeforeEach
    public void init() throws Exception {
        Mockito.lenient().when(mock.select(stop.getKey())).thenReturn(stop);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(new Pair<>(10, 110))).thenReturn(null);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testSelectExist() throws Exception {
        //Arrange
        StopsDto expected = stop;
        StopsRepository repository = new StopsRepository(mock);
        //Action
        StopsDto result = repository.get(stop.getKey());
        //Assert
        assertEquals(result, expected);
        Mockito.verify(mock, times(1)).select(stop.getKey());
    }

    @Test
    public void testSelectNotExist() throws Exception {
        //Arrange
        StopsRepository repository = new StopsRepository(mock);
        //Action
        StopsDto result = repository.get(new Pair<>(10, 110));
        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(new Pair<>(10, 110));
    }

    @Test
    public void testSelectWrongParameter() throws Exception {
        //Arrange
        StopsRepository repository = new StopsRepository(mock);
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            StopsDto result = repository.get(null);
        });
        Mockito.verify(mock, times(1)).select(null);
    }

    @Test
    public void testSelectAllExist() throws Exception {
        //Arrange
        List<StopsDto> expected = all;
        StopsRepository repository = new StopsRepository(mock);
        //Action
        List<StopsDto> result = repository.getAll();
        //Assert
        assertEquals(result, expected);
        Mockito.verify(mock, times(1)).selectAll();
    }
}