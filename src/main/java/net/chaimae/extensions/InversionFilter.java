package net.chaimae.extensions;


import net.chaimae.framework.IFilter;

public class InversionFilter implements IFilter {

    @Override
    public int[] filter(int[] data) {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = 255 - data[i];
        }
        return result;
    }
}
