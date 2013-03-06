package NeuroGenetic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TrayNo
 */
public class NeuralNetworkTests {
    NeuralNetwork network;
  /*  
    public NeuralNetworkTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    */
    @Before
    public void setUp() {
        //network = new NeuralNetwork(1,3,3,0.05);
    }/*
    
    @After
    public void tearDown() {
    }*/
    /*
    @Test
    public void creatingNetwork(){
        //network = new NeuralNetwork(1,3,3,3,0.05);
        ArrayList <Double> param = new ArrayList <Double>();
        param.add(5.0);
        IncomeParams incomeParams = new IncomeParams(param);
        network.setIncomeParams(param);
        network.createNeurons();
    }
    /*
    @Test
    public void learning(){
        ArrayList <Double> param = new ArrayList <Double>();
        param.add(5.0/10.0);
        IncomeParams incomeParams = new IncomeParams(param);
        network.setIncomeParams(param);
        network.createNeurons();
        network.startLerning();
    }
    /*
    @Test
    public void params3learning(){
        network = new NeuralNetwork(3,3,3,3,0.001);
        ArrayList <Double> param = new ArrayList <Double>();
        param.add(5.0/10.0);
        param.add(10.0/20.0);
        param.add(173.0/200.0);
        //IncomeParams incomeParams = new IncomeParams(param);
        network.setIncomeParams(param);
        network.createNeurons();
        network.startLerning();
        System.out.println("------------------------------");
        System.out.println(network.countMultiFunction(param));
        param.clear();
        param.add(1.0/10.0);
        param.add(4.0/20.0);
        param.add(200.0/200.0);
        System.out.println(network.countMultiFunction(param));

    }
    /*
    @Test
    public void multiParamsLoadingTest() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
        network = new NeuralNetwork(3,10,5,10,0.015);
        network.createIncomeParamsArray("d:\\inst\\IV курс\\MSII\\NeuroGenetic\\BASETONEURON.GDB","NOKORKA");
        network.createNeurons();
        network.startMultipulLearning();
        ArrayList <Double> param = new ArrayList <Double>();
        param.add(40.0/network.tMax);
        param.add(0.4/network.sMax);
        param.add(84.0/network.vMax);
        System.out.println("------------------------------");
        System.out.println(network.countMultiFunction(param));
        network.saveNetworkToFile("NetPromo.neunet");
    }*/
    
    /*@Test
    public void openNetworkTest() throws FileNotFoundException, IOException, IOException, ClassNotFoundException{
        network = new NeuralNetwork("NetTest_1.neunet");
    }*/
    
    @Test
    public void creatingDatabases() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
        ArrayList <Double> param = new ArrayList <>();
        for(int i=1; i<21;i++){
            NeuralNetwork network = new NeuralNetwork(3,1,20,0.025);
            network.createIncomeParamsArray("d:\\inst\\IV курс\\MSII\\NeuroGenetic\\BASETONEURON.GDB","NOKORKA");
            network.createNeurons();
            network.startMultipulLearning();
            network.saveNetworkToFile("NetTest_"+String.valueOf(i) +".neunet");
            ///////////
            param.add((30.0+network.tMax)*network.tA);
            param.add((0.4+network.sMax)*network.sA);
            param.add((86.0+network.vMax)*network.vA);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add((25.0+network.tMax)*network.tA);
            param.add((0.5+network.sMax)*network.sA);
            param.add((80.0+network.vMax)*network.vA);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add((40.0+network.tMax)*network.tA);
            param.add((0.6+network.sMax)*network.sA);
            param.add((71.0+network.vMax)*network.vA);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add((20.0+network.tMax)*network.tA);
            param.add((1+network.sMax)*network.sA);
            param.add((62.0+network.vMax)*network.vA);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add((30.0+network.tMax)*network.tA);
            param.add((2.5+network.sMax)*network.sA);
            param.add((34.0+network.vMax)*network.vA);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add((25.0+network.tMax)*network.tA);
            param.add((0.4+network.sMax)*network.sA);
            param.add((88.0+network.vMax)*network.vA);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();/*
            param.add(30.0/network.tMax);
            param.add(0.4/network.sMax);
            param.add(86.0/network.vMax);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add(25.0/network.tMax);
            param.add(0.5/network.sMax);
            param.add(80.0/network.vMax);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add(40.0/network.tMax);
            param.add(0.6/network.sMax);
            param.add(71.0/network.vMax);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add(20.0/network.tMax);
            param.add(1/network.sMax);
            param.add(62.0/network.vMax);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add(30.0/network.tMax);
            param.add(2.5/network.sMax);
            param.add(34.0/network.vMax);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();
            
            param.add(25.0/network.tMax);
            param.add(0.4/network.sMax);
            param.add(88.0/network.vMax);
            System.out.println(network.countMultiFunction(param)-0.5);
            param.clear();/*
            param.add(30.0);
            param.add(0.4);
            param.add(86.0);
            System.out.println(network.countMultiFunction(param));
            param.clear();
            
            param.add(25.0);
            param.add(0.5);
            param.add(80.0);
            System.out.println(network.countMultiFunction(param));
            param.clear();
            
            param.add(40.0);
            param.add(0.6);
            param.add(71.0);
            System.out.println(network.countMultiFunction(param));
            param.clear();
            
            param.add(20.0);
            param.add(1.0);
            param.add(62.0);
            System.out.println(network.countMultiFunction(param));
            param.clear();
            
            param.add(30.0);
            param.add(2.5);
            param.add(34.0);
            System.out.println(network.countMultiFunction(param));
            param.clear();
            
            param.add(25.0);
            param.add(0.4);
            param.add(88.0);
            System.out.println(network.countMultiFunction(param));
            param.clear();*/
            
            System.out.println("Results for Network number"+String.valueOf(i) +"------------------------------");
            
            //////////////
        }
    }
}
