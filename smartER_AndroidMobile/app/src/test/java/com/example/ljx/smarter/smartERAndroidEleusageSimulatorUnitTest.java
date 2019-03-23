package com.example.ljx.smarter;

import org.junit.Test;

import static org.junit.Assert.*;

public class smartERAndroidEleusageSimulatorUnitTest {

    private EleusageSimulator eleusageSimulator = new EleusageSimulator();

        @Test
        public void test_getFlag(){
            eleusageSimulator.getWashingmachine();
            int newFlag1=eleusageSimulator.getFlag();
            eleusageSimulator.getWashingmachine();
            int newFlag2=eleusageSimulator.getFlag();
            System.out.println("output:" + newFlag1 + newFlag2);
            assertTrue(newFlag1 == newFlag2);
        }

        @Test
        public void test_getTimes(){
            eleusageSimulator.getWashingmachine();
            int newTimes1=eleusageSimulator.getTimes();
            eleusageSimulator.getWashingmachine();
            int newTimes2=eleusageSimulator.getTimes();
            System.out.println("output:" + newTimes1 + newTimes2);
            assertTrue(newTimes1 == newTimes2);
        }

}
