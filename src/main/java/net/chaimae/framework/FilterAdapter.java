package net.chaimae.framework;

public class FilterAdapter implements IFilter {

    private ImplNonStandard oldImpl;
    private String filterName;

    public FilterAdapter(ImplNonStandard oldImpl, String filterName) {
        this.oldImpl = oldImpl;
        this.filterName = filterName;
    }

    @Override
    public int[] filter(int[] data) {
        return oldImpl.appliquerFiltre(filterName, data);
    }
}