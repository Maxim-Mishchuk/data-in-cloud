package com.dataincloud.core;

import java.util.List;

public interface IRepository <T, ID>{
    T create (T t);
    List<T> readAll();
    T readById(ID id);
    T update(T t);
    T delete(ID id);
}
