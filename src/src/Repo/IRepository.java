package Repo;

import Domeniu.Entity;

import java.util.ArrayList;

public interface IRepository<T extends Entity> extends Iterable<T> {
    public Boolean findByID(int id);
    ArrayList<T> getAll();
    T getById(int id);
    void add(T elem) throws RepoException;
    void deleteById(int id) throws RepoException;
    void updateById(int id, T newElem);

}
