package com.mcourse.parallelAnimation;

/**
 * Created on 2019-4-10 15:49.
 * Describe: TODO
 */

public class ParallelViewTag {
     int index;
     float xIn;
     float xOut;
     float yIn;
     float yOut;
     float alphaIn;
     float alphaOut;

    @Override
    public String toString() {
        return "ParallelViewTag{" +
                "index=" + index +
                ", xIn=" + xIn +
                ", xOut=" + xOut +
                ", yIn=" + yIn +
                ", yOut=" + yOut +
                ", alphaIn=" + alphaIn +
                ", alphaOut=" + alphaOut +
                '}';
    }
}
