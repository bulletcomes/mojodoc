package com.bulletcomes.mojodoc.logic;

import java.util.Comparator;

public class MojoComparator implements Comparator<MojoMethod> {

    @Override
    public int compare(MojoMethod o1, MojoMethod o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
