package com.github.dewarepk.model;

/**
 * The Simple interface which used for SimpleUtil
 *
 * @param <T>
 */
public interface SimpleCallback<T> {

    void onDataReceived(T result);
}
