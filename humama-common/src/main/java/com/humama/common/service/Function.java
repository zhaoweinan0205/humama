package com.humama.common.service;

/**
 * @Description: .
 * @Author: ZHaoWeiNan .
 * @CreatedTime: 2016/9/12 .
 * @Version: 1.0 .
 */
public interface Function<T,E> {

    public T callback(E e);
}
