package NeuroGenetic;

import java.util.ArrayList;

/**
 *
 * @author Tray
 */
public class IncomeParams {
    private ArrayList <Double> incomeParams;// = new ArrayList <>();
    
    public IncomeParams (ArrayList <Double> income){
        this.incomeParams = income;
    }
    
    public ArrayList<Double> getParams(){
        return incomeParams;
    }
}
