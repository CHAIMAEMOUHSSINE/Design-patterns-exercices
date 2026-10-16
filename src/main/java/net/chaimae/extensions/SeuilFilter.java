package net.chaimae.extensions;

import net.chaimae.framework.IFilter;

public class SeuilFilter implements IFilter {

    @Override
    public int[] filter(int[] data) {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i] > 127 ? 255 : 0;
        }
        return result;
    }
}