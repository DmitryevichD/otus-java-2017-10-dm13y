package by.dm13y.study;

import by.dm13y.study.agent.AgentMemoryCounter;

import java.util.BitSet;

public class AgentTester {
    public static void printObjectSize(Object object) {
        System.out.println(object.getClass().getSimpleName() + ":" + AgentMemoryCounter.getObjectGraphSize(object));
    }
    public static void main(String[] args) {
        printObjectSize(new String[1024]);
        printObjectSize(new BitSet(1024));
        printObjectSize(new Boolean[1024]);
        printObjectSize(new boolean[1024]);
    }
}
