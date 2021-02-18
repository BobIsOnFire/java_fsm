package com.bobisonfire.fsm;

public interface FSM<T, V> {
    V run(FSMInput<T> input);
    void setStartState(FSMState<T> state);
    void addEndState(FSMState<T> state, V result);
}
