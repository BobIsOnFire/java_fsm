package com.bobisonfire;

import com.bobisonfire.fsm.FSMInput;

public class StringInput implements FSMInput<Character> {
    private final String string;
    private int pointer;

    public StringInput(String string) {
        this.string = string;
        this.pointer = 0;
    }

    @Override
    public Character next() {
        return this.string.charAt(this.pointer++);
    }

    @Override
    public boolean hasNext() {
        return this.string.length() > this.pointer;
    }
}
