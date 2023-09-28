package stib.model.repository;

import stib.model.Pair;
import stib.model.dto.StationDto;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;
import stib.model.jdbc.StationDao;
import stib.model.jdbc.StopsDao;

import java.util.List;

public class StationRepository implements Repository<Integer, StationDto>{
    private StationDao dao;

    public StationRepository() throws RepositoryException{
        dao = StationDao.getInstance();
    }

    StationRepository(StationDao dao){
        this.dao = dao;
    }
    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return (get(key) != null);
    }
}
