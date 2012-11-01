package NeuroGenetic;

import java.util.ArrayList;

/**
 *
 * @author TrayNo
 */
public class NeuralNetwork {
    
    private ArrayList <Neuron> incomeLayout = new ArrayList <>();
    private ArrayList <Neuron> outcomeLayout = new ArrayList <>();
    private ArrayList <ArrayList <Neuron> > hideLayouts = new ArrayList < >();
    private Neuron outNeuron;
    private int incomeLayoutNeuronCount, outcomeLayoutNeuronCount, hideLayoutsCount, hideLayoutNeuronCount;
    private ArrayList <IncomeParams> incomeParamsArrLearn = new ArrayList <>();//добавление необходимо оуществлять поочередно: параметры+выходное значение
    private ArrayList <Double> outcomeValuesArrLearn = new ArrayList <>();
    private IncomeParams incomeParams;
    private Double outcomeValue = 0.0;
    private double maxError;
    
    public NeuralNetwork(int incomeLayoutNeuronCount, int outcomeLayoutNeuronCount,
                         int hideLayoutsCount, int hideLayoutNeuronCount, double error){
        this.incomeLayoutNeuronCount = incomeLayoutNeuronCount;
        this.outcomeLayoutNeuronCount = outcomeLayoutNeuronCount;
        this.hideLayoutsCount = hideLayoutsCount;
        this.hideLayoutNeuronCount = hideLayoutNeuronCount;
        this.maxError = error;
    }
    
    public void setIncomeParams(ArrayList <Double> incomeParams){
        this.incomeParams = new IncomeParams(incomeParams);
    }
    
    public void setInvomeParamsArr(ArrayList <Double> incomeParams){
        this.incomeParamsArrLearn.add(new IncomeParams(incomeParams));
    }
    
    public void setOutcomeValueArr(Double outcomeValue){
        this.outcomeValuesArrLearn.add(outcomeValue);
    }
    
    public double getOutcomeValue(){
        return outcomeValue;
    }
    
    public void createNeurons(){
        int incomeCount = incomeParams.getParams().size();
        
        for(int i = 0; i<incomeLayoutNeuronCount; i++){
            this.incomeLayout.add(new Neuron(incomeCount,Genetic.getInitKoef(incomeCount)));
        }
        
        //creating hide layouts
        for(int i=0; i<hideLayoutsCount; i++){
            this.hideLayouts.add(new ArrayList<Neuron>());
            for(int j=0; j<hideLayoutNeuronCount; j++){
                if (i==0) {this.hideLayouts.get(i).add(new Neuron(incomeLayoutNeuronCount,Genetic.getInitKoef(incomeLayoutNeuronCount)));}
                else {
                    this.hideLayouts.get(i).add(new Neuron(hideLayoutNeuronCount,Genetic.getInitKoef(hideLayoutNeuronCount)));
                }        
            }
        }
        
        for(int i=0; i<outcomeLayoutNeuronCount; i++){
            this.outcomeLayout.add(new Neuron(hideLayoutNeuronCount,Genetic.getInitKoef(hideLayoutNeuronCount)));
        }
        
        outNeuron = new Neuron(outcomeLayoutNeuronCount,Genetic.getInitKoef(outcomeLayoutNeuronCount));
    }
    
    public void startLerning(){
        
    }
    
    public void countFunction(){
        
    }
    
}
