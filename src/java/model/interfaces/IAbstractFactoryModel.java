package model.interfaces;

import java.util.List;

/**
 * Implement CRUD (CREATE, READ o SELECT, UPDATE, DELETE)
 * Implement GENERIC type (<>)
 * The 'Anything' word is generic type
 */
public interface IAbstractFactoryModel <Anything, T>{
    public boolean insert(Anything a);
    public boolean update(Anything a);
    public boolean delete(T key);
    
    public Anything select(Anything a);
    public List<Anything> selectAll();
    public List<Anything> selectAll(List<T> a);
}
