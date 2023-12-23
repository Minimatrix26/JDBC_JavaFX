package Repo;

import Domeniu.Entity;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MemoryRepo<T extends Entity> extends AbstractRepo<T> {
    @Override
    public Boolean findByID(int id) {
        for(T element: data){
            if (element.getId() == id)
                return true;
        }
        return false;
    }

    @Override
    public ArrayList<T> getAll() {
        return data;
    }

    @Override
    public T getById(int id) {
        if(!findByID(id))
            throw new NoSuchElementException("Nu exista entitate cu acest ID!");
        for(T elem : data){
            if(elem.getId() == id)
                return elem;
        }
        return null;
    }

    @Override
    public void add(T elem) throws RepoException {
        if(findByID(elem.getId())){
            throw new DuplicateObjectException("Dublaj, id deja existent!");
        }
        this.data.add(elem);
    }

    @Override
    public void deleteById(int id) {
        if(!findByID(id))
            throw new NoSuchElementException("Nu exista entitate cu acest Id!");

        this.data.remove(getById(id));
    }

    @Override
    public void updateById(int id, T newElem) {
        if(!findByID(id))
            throw new NoSuchElementException("Nu exista entitate cu acest ID!");
        for(int i = 0; i < data.size(); i++){
            T crt = data.get(i);
            if(crt.getId() == id)
            {
                data.set(i, newElem);
                return;
            }
        }
    }
}
