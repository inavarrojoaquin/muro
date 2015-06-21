package service;

import java.util.List;

public abstract class AbstractMuroService<Anything, T> {
    abstract public List<Anything> get(T object);
    abstract public List<Anything> get(List<T> object);

    public boolean insert(Anything a)   { return false; }
    public boolean delete(T key)        { return false; }
    public boolean disable(Anything A)  { return false; }
    public boolean update(Anything a)   { return false; }

}
