package net.chaimae;



import net.chaimae.framework.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] image = {10, 10, 10, 200, 200, 50, 50, 50, 50, 255};
        ImageProcessor processor = new ImageProcessor(d -> d, null);

        System.out.println("Image : " + Arrays.toString(image));

        System.out.print("Classe de compression (net.chaimae.extensions) : ");
        String compressorName = sc.nextLine();
        try {
            AbstractCompressor c = (AbstractCompressor) Class.forName(compressorName)
                    .getDeclaredConstructor()
                    .newInstance();
            processor.setCompressor(c);
        } catch (Exception e) {
            System.out.println("Erreur : " + e);
            return;
        }

        boolean continuer = true;
        while (continuer) {
            System.out.print("Classe de filtre (ex: extensions.SeuilFilter) : ");
            String filterName = sc.nextLine();
            try {
                Object obj = Class.forName(filterName)
                        .getDeclaredConstructor()
                        .newInstance();

                if (obj instanceof IFilter) {
                    processor.setFilter((IFilter) obj);
                } else if (obj instanceof ImplNonStandard) {
                    System.out.print("Nom du filtre ancien (ex: double) : ");
                    String oldName = sc.nextLine();
                    processor.setFilter(new FilterAdapter((ImplNonStandard) obj, oldName));
                } else {
                    System.out.println("Classe non supportée.");
                    continue;
                }

                int[] filtered = processor.filter(image);
                System.out.println("Après filtrage    : " + Arrays.toString(filtered));
                System.out.println("Après compression : " + Arrays.toString(processor.compresser(filtered)));
            } catch (Exception e) {
                System.out.println("Erreur : " + e);
            }

            System.out.print("Changer de filtre ? (o/n) : ");
            continuer = sc.nextLine().equalsIgnoreCase("o");
        }
        sc.close();
    }
}