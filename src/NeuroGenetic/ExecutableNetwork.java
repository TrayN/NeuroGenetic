package NeuroGenetic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author TrayNo
 */
public class ExecutableNetwork {
    
    //NeuralNetwork network;
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException{
        //пример исполняемого кода для использования под генетический алгоритм
        NeuralNetwork network = new NeuralNetwork("NetTest1.neunet");   //создание нейронной сети - в качестве нейронки берется заранее созданный файл - обучение не производится
        ArrayList <Double> params = new ArrayList<>();                  //создаешь массив, куда закидуешь нормированные хромосомы
        params.add(30.0/network.tMax);
        params.add(0.4/network.sMax);
        params.add(86.0/network.vMax);
        Double value = network.countMultiFunction(params);              //на выходе получаешь значение степени принадлежности(идеал 0.5 - ошибка обучения сетей +-0.015)
        params.clear();                                                 //не забывай чистить массив
        System.out.println(value);
    }
}
