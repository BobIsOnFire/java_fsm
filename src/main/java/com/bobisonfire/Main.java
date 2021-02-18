package com.bobisonfire;

import com.bobisonfire.fsm.*;

import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<Character> spacePredicate = ch -> (ch == '\t' || ch == ' ');
        Predicate<Character> nonSpacePredicate = spacePredicate.negate();

        FSM<Character, Boolean> wordsEvenFSM = new DeterministicFSM<>();

        FSMState<Character> evenChar = new DeterministicFSMState<>("Even words, reading char");
        FSMState<Character> evenSpace = new DeterministicFSMState<>("Even words, reading space");
        FSMState<Character> oddChar = new DeterministicFSMState<>("Odd words, reading char");
        FSMState<Character> oddSpace = new DeterministicFSMState<>("Odd words, reading space");

        evenChar.addRule(spacePredicate, evenSpace);
        evenChar.addRule(nonSpacePredicate, evenChar);

        evenSpace.addRule(spacePredicate, evenSpace);
        evenSpace.addRule(nonSpacePredicate, oddChar);

        oddChar.addRule(spacePredicate, oddSpace);
        oddChar.addRule(nonSpacePredicate, oddChar);

        oddSpace.addRule(spacePredicate, oddSpace);
        oddSpace.addRule(nonSpacePredicate, evenChar);

        wordsEvenFSM.setStartState(evenSpace);
        wordsEvenFSM.addEndState(evenSpace, true);
        wordsEvenFSM.addEndState(evenChar, true);
        wordsEvenFSM.addEndState(oddSpace, false);
        wordsEvenFSM.addEndState(oddChar, false);

        StringInput input = new StringInput("hello from the outside");
        boolean result = wordsEvenFSM.run(input);
        System.out.println(result);
    }
}
