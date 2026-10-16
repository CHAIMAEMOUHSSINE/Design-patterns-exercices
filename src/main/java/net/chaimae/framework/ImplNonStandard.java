package net.chaimae.framework;

public class ImplNonStandard {

    public int[] appliquerFiltre(String filterName, int[] data) {
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            if (filterName.equalsIgnoreCase("double")) {
                result[i] = data[i] * 2;
            } else {
                result[i] = data[i];
            }
        }
        return result;
    }
}