package NeuroGenetic;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.sql.*;

/**
 *
 * @author TrayNo
 */
public class NeuralNetwork {
    
    private ArrayList <Neuron> incomeLayout = new ArrayList <>();
    //private ArrayList <Neuron> outcomeLayout = new ArrayList <>();
    private ArrayList <ArrayList <Neuron> > hideLayouts = new ArrayList < >();
    private Neuron outNeuron;
    private int incomeLayoutNeuronCount, outcomeLayoutNeuronCount, hideLayoutsCount, hideLayoutNeuronCount;
    private ArrayList <IncomeParams> incomeParamsArrLearn = new ArrayList <>();//добавление необходимо оуществлять поочередно: параметры+выходное значение
    //private ArrayList <Double> outcomeValuesArrLearn = new ArrayList <>();
    private IncomeParams incomeParams;
    private Double outcomeValue = 0.0;
    private double maxError=0.0;
    public double tMax,sMax,vMax;
    
    public NeuralNetwork(int incomeLayoutNeuronCount, //int outcomeLayoutNeuronCount,
                         int hideLayoutsCount, int hideLayoutNeuronCount, double error){
        this.incomeLayoutNeuronCount = incomeLayoutNeuronCount;
        this.outcomeLayoutNeuronCount = outcomeLayoutNeuronCount;
        this.hideLayoutsCount = hideLayoutsCount;
        this.hideLayoutNeuronCount = hideLayoutNeuronCount;
        this.maxError = error;
    }
    
    public NeuralNetwork(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
        openNetworkFromFile(path);
        this.incomeLayoutNeuronCount = incomeLayout.size();
        //this.outcomeLayoutNeuronCount = outcomeLayout.size();
        this.hideLayoutsCount = hideLayouts.size();
        this.hideLayoutNeuronCount = hideLayouts.get(0).size();
    }
    
    public void createIncomeParamsArray(String path, String tableName) throws ClassNotFoundException, SQLException{
        IBcore ibCore = new IBcore();
        ResultSet set=null;
        if(ibCore.createConnection(path)){
            set = ibCore.executeSQL("Select max(T) as T from "+tableName);
            set.next();
            tMax = Double.valueOf(set.getString(1));
            set = ibCore.executeSQL("Select max(s) as S from "+tableName);
            set.next();
            sMax = Double.valueOf(set.getString(1));
            set = ibCore.executeSQL("Select max(v) as V from "+tableName);
            set.next();
            vMax = Double.valueOf(set.getString(1));
            set = ibCore.executeSQL("Select * from "+tableName);
            while (set.next()){
                ArrayList <Double> params = new ArrayList <Double>();
                params.add(Double.valueOf(set.getString(1))/tMax);
                params.add(Double.valueOf(set.getString(2))/sMax);
                params.add(Double.valueOf(set.getString(3))/vMax);
                incomeParamsArrLearn.add(new IncomeParams(params));
                
            }
        }
        ibCore.closeConnection();
    }
    
    public void setIncomeParams(ArrayList <Double> incomeParams){
        this.incomeParams = new IncomeParams(incomeParams);
    }
    
    public void setIncomeParamsArr(ArrayList <Double> incomeParams){
        this.incomeParamsArrLearn.add(new IncomeParams(incomeParams));
    }
    
    //public void setOutcomeValueArr(Double outcomeValue){
    //    this.outcomeValuesArrLearn.add(outcomeValue);
    //}
    
    public double getOutcomeValue(){
        return outcomeValue;
    }
    
    public void createNeurons(){
        //int incomeCount = incomeParams.getParams().size();
        
        for(int i = 0; i<incomeLayoutNeuronCount; i++){
            this.incomeLayout.add(new Neuron(1,Genetic.getInitKoef(1),i));
        }
        
        //creating hide layouts
        for(int i=0; i<hideLayoutsCount; i++){
            this.hideLayouts.add(new ArrayList<Neuron>());
            for(int j=0; j<hideLayoutNeuronCount; j++){
                if (i==0) {this.hideLayouts.get(i).add(new Neuron(incomeLayoutNeuronCount,Genetic.getInitKoef(incomeLayoutNeuronCount),j));}
                else {
                    this.hideLayouts.get(i).add(new Neuron(hideLayoutNeuronCount,Genetic.getInitKoef(hideLayoutNeuronCount),j));
                }        
            }
        }
        
        //for(int i=0; i<outcomeLayoutNeuronCount; i++){
        //    this.outcomeLayout.add(new Neuron(hideLayoutNeuronCount,Genetic.getInitKoef(hideLayoutNeuronCount),i));
        //}
        
        outNeuron = new Neuron(hideLayoutNeuronCount,Genetic.getInitKoef(hideLayoutNeuronCount),0);
    }
    
    public void sendSignalFromTo(Neuron mainNeuron, Neuron secondNeuron){
        secondNeuron.setValue(mainNeuron.getNum(), mainNeuron.getOutcomeValue());
    }
    
    public void countNeuralValues(){
        int i=0;
        int j=0;
        int k=0;
        
        for(i=0;i<incomeLayoutNeuronCount;i++){
            incomeLayout.get(i).countOutcomeValue();
        }
        
        for(i=0;i<hideLayoutNeuronCount;i++){
            for(j=0;j<incomeLayoutNeuronCount;j++){
                sendSignalFromTo(incomeLayout.get(j),hideLayouts.get(0).get(i));
            }
            hideLayouts.get(0).get(i).countOutcomeValue();
        }
        
        for(i=1; i<hideLayoutsCount; i++){
            for(j=0; j<hideLayoutNeuronCount; j++){
                for(k=0; k<hideLayoutNeuronCount; k++){
                    sendSignalFromTo(hideLayouts.get(i-1).get(k),hideLayouts.get(i).get(j));
                }
                hideLayouts.get(i).get(j).countOutcomeValue();
            }
        }
        
        //for(i=0; i<outcomeLayoutNeuronCount; i++){
        //    for(j=0; j<hideLayoutNeuronCount; j++){
        //        sendSignalFromTo(hideLayouts.get(hideLayoutsCount-1).get(j),outcomeLayout.get(i));
        //    }
        //    outcomeLayout.get(i).countOutcomeValue();
        //}
        
        for(i=0;i<hideLayoutNeuronCount;i++){
            sendSignalFromTo(hideLayouts.get(hideLayoutsCount-1).get(i),outNeuron);
        }
    }
    
    private void backpropagationMethod(){
        int numberOfSynapses;
        outNeuron.countError(0.5);
        outNeuron.countLocalGradient();
        for(int i=0; i<hideLayoutNeuronCount; i++){
            outNeuron.countDeltaNeuronForSynapse(i);
        }
        
        if (hideLayoutsCount==1){numberOfSynapses=incomeLayoutNeuronCount;}
        else {numberOfSynapses=hideLayoutNeuronCount;}
        for(int i=0; i<hideLayoutNeuronCount; i++){
            hideLayouts.get(hideLayoutsCount-1).get(i).countLocalGradientNeuron(outNeuron.getLocalGradient()*outNeuron.getSynapsesKoefForSynapse(i));
            for(int j=0; j<numberOfSynapses; j++){
                hideLayouts.get(hideLayoutsCount-1).get(i).countDeltaNeuronForSynapse(j);
            }
        }
        /*
        if (hideLayoutsCount==1){numberOfSynapses=incomeLayoutNeuronCount;}
        else {numberOfSynapses=hideLayoutNeuronCount;}
        for(int i=0; i<hideLayoutNeuronCount; i++){
            double gradSum=0;
            for(int j=0; j<outcomeLayoutNeuronCount; j++){
                gradSum+=outcomeLayout.get(j).getLocalGradient()*outcomeLayout.get(j).getSynapsesKoefForSynapse(i);
            }
            hideLayouts.get(hideLayoutsCount-1).get(i).countLocalGradientNeuron(gradSum);
            for(int j = 0;j<numberOfSynapses;j++){
               hideLayouts.get(hideLayoutsCount-1).get(i).countDeltaNeuronForSynapse(j);
            }
        }*/
        
        
        for(int k=hideLayoutsCount-2;k>0;k--){
            for(int i =0;i<hideLayoutNeuronCount; i++){
                double gradSum=0;
                for(int j=0; j<hideLayoutNeuronCount; j++){
                    gradSum+=hideLayouts.get(k+1).get(j).getLocalGradient()*hideLayouts.get(k+1).get(j).getSynapsesKoefForSynapse(i);
                }
                hideLayouts.get(k).get(i).countLocalGradientNeuron(gradSum);
                for(int j = 0;j<hideLayoutNeuronCount;j++){
                    hideLayouts.get(k).get(i).countDeltaNeuronForSynapse(j);
                }
            }
        }
        
        if (hideLayoutsCount>1){
        for(int i=0; i<hideLayoutNeuronCount; i++){
            double gradSum=0;
            for(int j=0; j<hideLayoutNeuronCount; j++){
                gradSum+=hideLayouts.get(1).get(j).getLocalGradient()*hideLayouts.get(1).get(j).getSynapsesKoefForSynapse(i);
            }
            hideLayouts.get(0).get(i).countLocalGradientNeuron(gradSum);
            for(int j = 0;j<incomeLayoutNeuronCount;j++){
                hideLayouts.get(0).get(i).countDeltaNeuronForSynapse(j);
            }
        }
        }
        
        for(int i = 0;i<incomeLayoutNeuronCount;i++){
            double gradSum=0;
            for(int j=0; j<hideLayoutNeuronCount; j++){
                gradSum+=hideLayouts.get(0).get(j).getLocalGradient()*hideLayouts.get(0).get(j).getSynapsesKoefForSynapse(i);
            }
            incomeLayout.get(i).countLocalGradientNeuron(gradSum);
            incomeLayout.get(i).countDeltaNeuronForSynapse(0);
        }
    }
    
    public void applyNewKoef(){
        outNeuron.setNewKoef();
        
        //for(int i=0;i<outcomeLayoutNeuronCount;i++){
        //    outcomeLayout.get(i).setNewKoef();
        //}
        
        for(int k=0; k<hideLayoutsCount;k++){
            for(int i=0;i<hideLayoutNeuronCount;i++){
                hideLayouts.get(k).get(i).setNewKoef();
            }
        }
        
        for(int i=0;i<incomeLayoutNeuronCount;i++){
            incomeLayout.get(i).setNewKoef();
        }
        
    }
    
    public void startLerning(){
        double error = 1;
        for(int i=0;i<incomeLayoutNeuronCount;i++){
            incomeLayout.get(i).setValue(0,incomeParams.getParams().get(i));
        }
        countNeuralValues();
        outNeuron.countOutcomeValue();
        error = Math.abs(0.5-outNeuron.getOutcomeValue());
        while (maxError<error){
            backpropagationMethod();
            applyNewKoef();
            countNeuralValues();
            outNeuron.countOutcomeValue();
            error = Math.abs(0.5-outNeuron.getOutcomeValue());
        }          
    }
    
    public void startMultipulLearning(){
        double error = 1;
        int flag=1;
        int incValuesPos=0;
        while(flag!=0){
            flag=0;
            for(incValuesPos = 0;incValuesPos<incomeParamsArrLearn.size();incValuesPos++){
                for(int i=0;i<incomeLayoutNeuronCount;i++){
                    incomeLayout.get(i).setValue(0,incomeParamsArrLearn.get(incValuesPos).getParams().get(i));
                }
                countNeuralValues();
                outNeuron.countOutcomeValue();
                error = Math.abs(0.5-outNeuron.getOutcomeValue());
                if (maxError<error) {flag=1;}
                if(maxError<error){
                    backpropagationMethod();
                    applyNewKoef();
                    countNeuralValues();
                    outNeuron.countOutcomeValue();
                    error = Math.abs(0.5-outNeuron.getOutcomeValue());
                }
            }
        }
    }
    
    public double countFunction(double value){
        incomeLayout.get(0).setValue(0, value);
        countNeuralValues();
        outNeuron.countOutcomeValue();    
        return outNeuron.getOutcomeValue();
    }
    
    public double countMultiFunction(ArrayList <Double> arr){
        for(int i=0;i<incomeLayoutNeuronCount;i++){
            incomeLayout.get(i).setValue(0,arr.get(i));
        }
        countNeuralValues();
        outNeuron.countOutcomeValue();    
        return outNeuron.getOutcomeValue();
    }
    
    public void saveNetworkToFile(String path) throws FileNotFoundException, IOException{
        File f = new File(path);
        FileOutputStream fStream = new FileOutputStream(f);
        ObjectOutputStream fObject = new ObjectOutputStream(fStream);
        fObject.writeObject(incomeLayout);
        fObject.writeObject(hideLayouts);
        //fObject.writeObject(outcomeLayout);
        fObject.writeObject(outNeuron);
        fObject.writeObject(tMax);
        fObject.writeObject(sMax);
        fObject.writeObject(vMax);
        fObject.flush();
        fObject.close();
        fStream.close();
    }
    
    public void openNetworkFromFile(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
        File f = new File(path);
        FileInputStream fStream = new FileInputStream(f);
        ObjectInputStream fObject = new ObjectInputStream(fStream);
        incomeLayout = (ArrayList <Neuron>)fObject.readObject();
        hideLayouts = (ArrayList <ArrayList <Neuron> >)fObject.readObject();
        //outcomeLayout = (ArrayList <Neuron>)fObject.readObject();
        outNeuron = (Neuron)fObject.readObject();
        tMax = (Double)fObject.readObject();
        sMax = (Double)fObject.readObject();
        vMax = (Double)fObject.readObject();
        fObject.close();
    }
    
}
