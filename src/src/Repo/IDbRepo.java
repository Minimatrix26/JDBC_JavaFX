package Repo;

import Domeniu.Entity;

public interface IDbRepo<T extends Entity> extends IRepository<T> {
    void openConnection();
    void closeConnection();
    void createTable();
    void initTable();
}
