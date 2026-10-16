package net.chaimae.extensions;

import net.chaimae.framework.AbstractCompressor;

import java.util.ArrayList;
import java.util.List;

public class RLECompressor extends AbstractCompressor {

    @Override
    protected int[] encoder(int[] data) {
        List<Integer> out = new ArrayList<>();
        int i = 0;
        while (i < data.length) {
            int value = data[i];
            int count = 1;
            while (i + count < data.length && data[i + count] == value) {
                count++;
            }
            out.add(value);
            out.add(count);
            i += count;
        }
        int[] result = new int[out.size()];
        for (int k = 0; k < result.length; k++) {
            result[k] = out.get(k);
        }
        return result;
    }
}