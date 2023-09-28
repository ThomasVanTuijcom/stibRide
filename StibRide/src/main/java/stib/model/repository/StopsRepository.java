package stib.model.repository;

import stib.model.Pair;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;
import stib.model.jdbc.StopsDao;

import java.util.List;

public class StopsRepository implements Repository<Pair<Integer, Integer>, StopsDto>{
    private StopsDao dao;

    public StopsRepository() throws RepositoryException{
        dao = StopsDao.getInstance();
    }

    StopsRepository(StopsDao dao){
        this.dao = dao;
    }
    @Override
    public List<StopsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopsDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Pair<Integer, Integer> key) throws RepositoryException {
        return (get(key) != null);
    }
}
