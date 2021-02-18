package com.bobisonfire.fsm;

import java.util.function.Predicate;

public interface FSMState<T> {
    FSMState<T> switchState(T input);
    void addRule(Predicate<T> rule, FSMState<T> result);
    void addElseRule(FSMState<T> result);
}
