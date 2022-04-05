package mainfiles;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVWriter;

import algorithms.Minimization;
import automata.FiniteAutomaton;
public class Experiment {
    public static void main(String[] args) {

        //3000 times test with la = 8
        try{
            String file = "./vc-reduce-results-la8.csv";   //change file name according to the different tests
            FileWriter vcOutputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(vcOutputFile);
           
          //co-accessible model
//          List<String[]> Acchead = new ArrayList<String[]>();
//          Acchead.add(new String[] {"transDen","finDen","la","transNumAf","statesNumAf","testCount"});
//          writer.writeAll(Acchead);

          //Vertex Copying model
            List<String[]> VChead = new ArrayList<String[]>();
            VChead.add(new String[] {"transDen","finDen","copyProb","la","transNumAf","statesNumAf","testCount"});
            writer.writeAll(VChead);
         
            Integer n = 200;                                   //# of state set to 200 and the # of symbols set to 2
            Integer AlphaSize = 2;
            ArrayList<Integer> laList = new ArrayList<>(Arrays.asList(1,4,8,12));
           
            for (Integer la:laList) {
                for (double copyProb = 0; copyProb <1;copyProb +=0.1){
                    for (double finDen = 0; finDen <1;finDen +=0.1){
                        for (double transDen = 1; transDen<3;transDen +=0.1) {
                            //co-accessible model
        //                     writer.writeAll(accReduce(n,transDen, finDen,AlphaSize,la));

                            //Vertex Copying model
                            writer.writeAll(VCReduce(n,transDen, finDen,copyProb, AlphaSize,la));
                            writer.flush();
                        }
                    }
                }      
            }
            writer.close();
            System.out.println("Successfully wrote to the file. ");
        }catch (IOException e){
            System.out.println("An error occured. ");
            e.printStackTrace();
        }
           
    }

    public static List<String[]> accReduce(Integer n,Double transDen,Double finDen, Integer AlphaSize,Integer la){
       
      Minimization x = new Minimization();
           
           List<String[]> Accresult = new ArrayList<String[]>();
           for (Integer testCount=0; testCount < 300; testCount++){
           FiniteAutomaton randomAcc = co_accessible.genRandomAcc(n, transDen, finDen,AlphaSize);        
           FiniteAutomaton reduceAcc = x.experimental_noj_Minimize_Buchi(randomAcc,la);                      
           System.out.printf("The transiton density is %f, finite density is %f, copy probability is %f, la is %d and the testCount is %d/300 \n",transDen,finDen,la,testCount);
           String[] testResult = new String[] {Double.toString(transDen),Double.toString(finDen),Integer.toString(la),Integer.toString(reduceAcc.trans),Integer.toString(reduceAcc.states.size()),Integer.toString(testCount)};
           Accresult.add(testResult);
           }
           return Accresult;
       }

    public static List<String[]> VCReduce(Integer n,Double transDen,Double finDen,Double copyProb, Integer AlphaSize,Integer la){
       
        Minimization x = new Minimization();
       
        List<String[]> VCresult = new ArrayList<String[]>();
        for (Integer testCount=0; testCount < 300; testCount++){
       FiniteAutomaton randomVC = Vertex_copying.genVertexCopying(n, transDen, finDen,copyProb, AlphaSize);        
       FiniteAutomaton reduceVC = x.experimental_noj_Minimize_Buchi(randomVC,la);                      
       System.out.printf("The transiton density is %f, finite density is %f, copy probability is %f, la is %d and the testCount is %d/300 \n",transDen,finDen,copyProb,la,testCount);
       String[] testResult = new String[] {Double.toString(transDen),Double.toString(finDen),Double.toString(copyProb),Integer.toString(la),Integer.toString(reduceVC.trans),Integer.toString(reduceVC.states.size()),Integer.toString(testCount)};
       VCresult.add(testResult);
        }

        return VCresult;
    }
   
}
