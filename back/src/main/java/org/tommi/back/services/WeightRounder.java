package org.tommi.back.services;

public class WeightRounder {

    public static double roundDownToNearest2_5(double weigth) throws IllegalArgumentException {
        if (weigth < 0) {
            throw new IllegalArgumentException("negatiivinen luku ei kelpaa");
        }

        int componentsOf2_5 = (int) (weigth / 2.5);
        return (double) (componentsOf2_5 * 2.5);
    }
}
