package Repo;

import Domeniu.Entity;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractRepo<T extends Entity> implements IRepository<T> {
    protected ArrayList<T> data = new ArrayList<>();

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(data).iterator();
    }
}
