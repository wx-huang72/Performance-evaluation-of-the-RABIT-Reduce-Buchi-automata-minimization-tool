package mainfiles;

import java.util.ArrayList;
import java.util.List;

import algorithms.Minimization;
import automata.FAState;
import automata.FiniteAutomaton;
import mainfiles.co_accessible;

public class accExperiment {

    public static void main(String[] args) {
        
        accReduce();
        // Minimization x = new Minimization();
        // co_accessible autoAcc = new co_accessible();

        // Integer n = 100;                                   //# of state set to 200 and the # of symbols set to 2
        // Integer AlphaSize = 2;
        // FiniteAutomaton randomAcc = co_accessible.genRandomAcc(n, 1.5, 0.5, AlphaSize);
        // System.out.println(autoAcc);

        // FiniteAutomaton reduceAcc = x.experimental_noj_Minimize_Buchi(randomAcc,4);
        // System.out.println(reduceAcc);

    
    }

    public static FiniteAutomaton accReduce(){
       
        Minimization x = new Minimization();

        Integer n = 100;                                   //# of state set to 200 and the # of symbols set to 2
        Integer AlphaSize = 2;
        // FiniteAutomaton randomAcc = TV.genRandomTV(n, (float)1.5, (float)0.5, AlphaSize);
        FiniteAutomaton randomAcc = co_accessible.genRandomAcc(n, 1.5, 0.5, AlphaSize);
        System.out.println(randomAcc.trans);

        FiniteAutomaton reduceAcc = x.experimental_noj_Minimize_Buchi(randomAcc,4);
        System.out.println(reduceAcc.trans);


        // List<Integer> lalist=new ArrayList<Integer>();
        // lalist.add(1);
        // for (int i =4; i <= 12; i+=4) {
        //     lalist.add(i);
        // }

        // FiniteAutomaton autoAcc = co_accessible.genRandomAcc(n, 1.5, 0.5, AlphaSize);
        // System.out.println(autoAcc);

        // FiniteAutomaton reduceAcc = x.experimental_noj_Minimize_Buchi(autoAcc,4);

        // for (double finDen = 0; finDen <=10;finDen +=0.1){
        //     for (double transDen = 1.1; transDen<=4.5;transDen +=0.1) {
            
        //         autoAcc = co_accessible.genRandomAcc(n, transDen, finDen, AlphaSize);         //不同参数的co-accessable model
        //         // System.out.println(autoAcc);

        //         for (Integer la: lalist){
        //             reduceAcc = x.experimental_noj_Minimize_Buchi(autoAcc,la);                      //输入分别为上面生成的co-accessble model以及la分别是1，4，8，12的时候的用过reduce function的结果
        //             System.out.println(reduceAcc);
        //             System.out.printf("The transiton density is %f, finite density is %f and la is %d",transDen/10,finDen/10,la);
        //         }

        //     }
        // }
        
        
        return reduceAcc;
    }

    
}
