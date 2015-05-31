package service;

import java.util.List;

public abstract class AbstractMuroService<Anything, T> {
    abstract public List<Anything> get(T object);
    abstract public List<Anything> get(List<T> object);
}
