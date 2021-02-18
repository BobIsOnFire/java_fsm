package com.bobisonfire.fsm;

public interface FSMInput<T> {
    T next();
    boolean hasNext();
}
