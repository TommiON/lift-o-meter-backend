package org.tommi.back.services;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeightRounderTest {

    @Test
    public void roundsCorrectly() {
        double result = WeightRounder.roundDownToNearest2_5(100);
        assertEquals(100, result, 0.0001);

        result = WeightRounder.roundDownToNearest2_5(101);
        assertEquals(100, result, 0.0001);

        result = WeightRounder.roundDownToNearest2_5(102.5);
        assertEquals(102.5, result, 0.0001);

        result = WeightRounder.roundDownToNearest2_5(103.5);
        assertEquals(102.5, result, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotAcceptNegatives() {
        double result = WeightRounder.roundDownToNearest2_5(-100);
    }

}