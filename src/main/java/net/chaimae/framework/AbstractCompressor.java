package net.chaimae.framework;

public abstract class AbstractCompressor {

    // Squelette de l'algorithme : non redéfinissable
    public final int[] compresser(int[] data) {
        int[] prepared = preparer(data);
        int[] encoded = encoder(prepared);
        return finaliser(encoded);
    }

    // Hook : comportement par défaut
    protected int[] preparer(int[] data) {
        return data.clone();
    }

    // Étape obligatoire à définir dans les sous-classes
    protected abstract int[] encoder(int[] data);

    // Hook : comportement par défaut
    protected int[] finaliser(int[] data) {
        return data;
    }
}