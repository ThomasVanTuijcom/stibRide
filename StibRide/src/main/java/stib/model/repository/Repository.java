package stib.model.repository;

import stib.model.dto.Dto;
import stib.model.exceptions.RepositoryException;

import java.util.List;

public interface Repository<K, T extends Dto<K>>{

    /**
     * Returns all the elements of the repository.
     *
     * @return all the elements of the repository.
     *
     * @throws RepositoryException if the repository can't access to the elements.
     */
    List<T> getAll() throws RepositoryException;

    /**
     * Return the element of the repository with the specific key.
     *
     * @param key key of the element.
     *
     * @return the element of the repository with the specific key.
     * @throws RepositoryException if the repository can't access to the element.
     */
    T get(K key) throws RepositoryException;

    /**
     * Returns true if the element exist in the repository and false otherwise.
     * An element is found by this key.
     *
     * @param key key of the element.
     * @return true if the element exist in the repository and false otherwise.
     * @throws RepositoryException if the repository can't access to the element.
     */
    boolean contains(K key) throws RepositoryException;
}
