package model.interfaces;

import java.util.List;

/**
 * Implement CRUD (CREATE, READ o SELECT, UPDATE, DELETE)
 * Implement GENERIC type (<>)
 * The 'Anything' word is generic type
 */
public abstract class IAbstractFactoryModel <Anything, T>{
    abstract public boolean insert(Anything a);
    abstract public boolean update(Anything a);
    abstract public boolean delete(T key);
    
    abstract public Anything select(Anything a);
    abstract public List<Anything> selectAll();
    abstract public List<Anything> selectAll(List<T> a);
    
    public boolean updateAnything(Anything a){ return false; }
}

//public interface IAbstractFactoryModel <Anything, T>{
//    public boolean insert(Anything a);
//    public boolean update(Anything a);
//    public boolean delete(T key);
//    
//    public Anything select(Anything a);
//    public List<Anything> selectAll();
//    public List<Anything> selectAll(List<T> a);
//}