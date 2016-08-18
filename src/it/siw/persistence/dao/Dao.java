package it.siw.persistence.dao;

public interface Dao<Model> {

    public void create(Model modelObject);

    public <T> Model find(T key);

    public void update(Model modelObject);

    public void delete(Model modelObject);

}
