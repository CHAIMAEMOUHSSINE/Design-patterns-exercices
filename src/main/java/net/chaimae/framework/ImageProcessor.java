package net.chaimae.framework;

public class ImageProcessor {

    private IFilter filter;
    private AbstractCompressor compressor;

    public ImageProcessor(IFilter filter, AbstractCompressor compressor) {
        this.filter = filter;
        this.compressor = compressor;
    }

    public void setFilter(IFilter filter) {
        this.filter = filter;
    }

    public void setCompressor(AbstractCompressor compressor) {
        this.compressor = compressor;
    }

    public int[] filter(int[] data) {
        return filter.filter(data);
    }

    public int[] compresser(int[] data) {
        return compressor.compresser(data);
    }
}