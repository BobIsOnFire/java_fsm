package com.bobisonfire.fsm;

import java.util.HashMap;
import java.util.Map;

public class DeterministicFSM<T, V> implements FSM<T, V> {
    private FSMState<T> startState;
    private final Map<FSMState<T>, V> endStatesMap;

    public DeterministicFSM() {
        this.startState = null;
        this.endStatesMap = new HashMap<>();
    }

    public V run(FSMInput<T> input) {
        if (input == null) throw new FSMException("Cannot run FSM: input is null.");
        if (startState == null) throw new FSMException("Cannot run FSM: start state is not set.");
        if (endStatesMap.size() == 0) throw new FSMException("Cannot run FSM: end state is not set.");

        FSMState<T> currentState = startState;

        while (input.hasNext())
            currentState = currentState.switchState(input.next());

        V value = endStatesMap.get(currentState);
        if (value == null) throw new FSMException("FSM execution did not finish in any of the end states.");
        return value;
    }

    public void setStartState(FSMState<T> state) {
        if (startState != null) throw new FSMException("Cannot set start state: start state already exists.");
        if (state == null) throw new FSMException("Cannot set start state: state is null.");
        this.startState = state;
    }

    public void addEndState(FSMState<T> state, V result) {
        if (state == null) throw new FSMException("Cannot add end state: state is null.");
        if (result == null) throw new FSMException("Cannot add end state: provided result is null.");
        if (endStatesMap.containsKey(state)) throw new FSMException("Cannot add end state: this end state already exists.");
        this.endStatesMap.put(state, result);
    }
}
