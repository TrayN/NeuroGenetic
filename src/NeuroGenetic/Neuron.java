package NeuroGenetic;

import java.util.HashMap;

/**
 *
 * @author TrayNo
 */
public class Neuron {
    
    private HashMap <Integer, Double> incomeSynapsesKoef;
    private HashMap <Integer, Double> incomeSynapsesValues = new HashMap <Integer, Double>();
    private double outcomeValue = 0;
    private int incomeSynapsesCount;
    
    public Neuron(int incomeSynapsesCount, HashMap <Integer, Double> initializingKoefArr){
        this.incomeSynapsesKoef = initializingKoefArr;
        this.incomeSynapsesCount = incomeSynapsesCount;
    }
    
    public void setValue(int synapseNum, double value){
        incomeSynapsesValues.put(synapseNum, value);
    }
    
    public double getOutcomeValue(){
        return outcomeValue;
    }
    
    public void countOutcomeValue(){
        outcomeValue = 0;
        for(int i=0; i < incomeSynapsesCount; i++){
            outcomeValue+=incomeSynapsesValues.get(i)*incomeSynapsesKoef.get(i);
        }
    }
}
