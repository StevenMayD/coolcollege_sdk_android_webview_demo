package com.coolcollege.dsbridgedemo.dsbridge.main.java.wendu.dsbridge;

/**
 * Created by du on 16/12/31.
 */

public interface  CompletionHandler<T> {
    void complete(T retValue);
    void complete();
    void setProgressData(T value);
}
