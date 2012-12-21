package NeuroGenetic;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author TrayNo
 */
public class Genetic {
    
    private static Random random = new Random();
    
    public static HashMap <Integer, Double> getInitKoef(int count){
        HashMap <Integer, Double> initKoef = new HashMap <Integer, Double>();
        for(int i =0;i<count;i++){
            initKoef.put(i, random.nextDouble());
        }
        return initKoef;
    }
}
