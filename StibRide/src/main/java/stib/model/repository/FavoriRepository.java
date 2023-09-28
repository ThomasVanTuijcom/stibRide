package stib.model.repository;

import stib.model.Pair;
import stib.model.dto.FavoriDto;
import stib.model.dto.StationDto;
import stib.model.dto.StopsDto;
import stib.model.exceptions.RepositoryException;
import stib.model.jdbc.FavoriDao;
import stib.model.jdbc.StationDao;
import stib.model.jdbc.StopsDao;

import java.util.List;

public class FavoriRepository implements Repository<String, FavoriDto>{
    private FavoriDao dao;

    public FavoriRepository() throws RepositoryException{
        dao = FavoriDao.getInstance();
    }

    FavoriRepository(FavoriDao dao){
        this.dao = dao;
    }
    @Override
    public List<FavoriDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    public void delete(String key) throws RepositoryException{
        dao.delete(key);
    }

    public void update(FavoriDto item) throws RepositoryException{
        dao.update(item);
    }

    public void insert(FavoriDto item) throws RepositoryException{
        if(contains(item.getKey())){
            update(item);
        }else{
            dao.insert(item);
        }
    }

    @Override
    public FavoriDto get(String key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(String key) throws RepositoryException {
        return (get(key) != null);
    }
}

