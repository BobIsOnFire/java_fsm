package com.bobisonfire.fsm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class DeterministicFSMState<T> implements FSMState<T> {
    private FSMState<T> elseRule;
    private final Map<Predicate<T>, FSMState<T>> rules;
    private String name;

    public DeterministicFSMState(String name) {
        this.elseRule = null;
        this.rules = new HashMap<>();
        this.name = name;
    }

    @Override
    public FSMState<T> switchState(T input) {
        if (input == null) throw new FSMException("Cannot switch state: input is null.");
        if (elseRule == null && rules.size() == 0) throw new FSMException("Cannot switch state: no rule is set.");

        for (Map.Entry<Predicate<T>, FSMState<T>> e: rules.entrySet()) {
            if (e.getKey().test(input)) return e.getValue();
        }

        if (elseRule == null) throw new FSMException("Cannot switch state: no rule has applied, but else rule is not set");
        return elseRule;
    }

    @Override
    public void addRule(Predicate<T> rule, FSMState<T> result) {
        if (rule == null) throw new FSMException("Cannot add rule: rule is null.");
        if (result == null) throw new FSMException("Cannot add rule: provided result is null.");
        if (this.rules.containsKey(rule)) throw new FSMException("Cannot add rule: rule already exists.");
        this.rules.put(rule, result);
    }

    @Override
    public void addElseRule(FSMState<T> result) {
        if (elseRule != null) throw new FSMException("Cannot add else rule: else rule already exists.");
        if (result == null) throw new FSMException("Cannot add else rule: provided result is null.");
        this.elseRule = result;
    }

    @Override
    public String toString() {
        return name;
    }
}
