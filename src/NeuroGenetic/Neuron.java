package NeuroGenetic;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author TrayNo
 */
public class Neuron implements Serializable{
    
    private HashMap <Integer, Double> incomeSynapsesKoef,deltaSynapsesKoef = new HashMap <Integer, Double>();
    private HashMap <Integer, Double> incomeSynapsesValues = new HashMap <Integer, Double>();
    private double outcomeValue = 0;
    private double localGradient = 0;
    private double outcomeError = 0;
    private int neuronNum = 0;
    private int incomeSynapsesCount;
    private double nu=1.0,a=6.8;
    
    public Neuron(int incomeSynapsesCount, HashMap <Integer, Double> initializingKoefArr, int neuronNum){
        this.incomeSynapsesKoef = initializingKoefArr;
        this.incomeSynapsesCount = incomeSynapsesCount;
        this.neuronNum = neuronNum;
        
        for(int i=0;i<incomeSynapsesCount;i++){
            deltaSynapsesKoef.put(i, 0.0);
        }
    }
    
    private double activeFunction(double x){
        return 1/(1+Math.exp(-a*(x-0.5)));
    }
    
    public void countError(double correctValue){
        outcomeError = correctValue - outcomeValue;
    }
    
    public void countLocalGradient(){
        double sum=0;
        for(int i=0;i<incomeSynapsesCount;i++){
            sum+=incomeSynapsesValues.get(i)*incomeSynapsesKoef.get(i);
        }
        if (sum>1) sum=1;
        localGradient = outcomeError*proizvod(sum);
    }
    
    public void countLocalGradientNeuron(double sumGrad){
        double sum=0;
        for(int i=0;i<incomeSynapsesCount;i++){
            sum+=incomeSynapsesValues.get(i)*incomeSynapsesKoef.get(i);
        }
        if (sum>1) sum=1;
        localGradient = proizvod(sum)*sumGrad;
    }
    
    private double proizvod(double x){
        //return (Math.exp(-x)) /(1-Math.exp(-x)*Math.exp(-x));//not right
        return (Math.exp(-(x-0.5)*a)*a)/((1+Math.exp(-(x-0.5)*a))*(1+Math.exp(-(x-0.5)*a)));
    }
    
    public double getLocalGradient(){
        return localGradient;
    }
    
    public double getSynapsesKoefForSynapse(int num){
        return incomeSynapsesValues.get(num);
    }
    
    public void countDeltaNeuronForSynapse(int num){
        deltaSynapsesKoef.put(num, nu*localGradient*incomeSynapsesValues.get(num)*incomeSynapsesKoef.get(num));
    }
    
   // public void countDeltaNeuronForSynapse(int num){
        
   //     deltaSynapsesKoef.put(num, nu**incomeSynapsesValues.get(num));
    //}
    
    public void setValue(int synapseNum, double value){
        incomeSynapsesValues.put(synapseNum, value);
    }
    
    public void clearSynapsesValues(){
        incomeSynapsesValues.clear();
    }
    
    public double getOutcomeValue(){
        return this.outcomeValue;
    }
    
    public int getNum(){
        return neuronNum;
    }
    
    public void setNewKoef(){
        for(int i=0;i<incomeSynapsesCount;i++){
            incomeSynapsesKoef.put(i,incomeSynapsesKoef.get(i)+deltaSynapsesKoef.get(i));
        }
    }
    
    public void countOutcomeValue(){
        this.outcomeValue = 0;
        double sum=0;
        for(int i=0; i < incomeSynapsesCount; i++){
            sum+=incomeSynapsesValues.get(i)*incomeSynapsesKoef.get(i);
        }
        this.outcomeValue = activeFunction(sum);
    }
}
