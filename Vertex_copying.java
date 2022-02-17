package mainfiles;

import java.lang.management.*;

import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

import automata.FAState;
import automata.FiniteAutomaton;
import datastructure.HashSet;

public class Vertex_copying{
    public static void main(String[] args) {
		genVertexCopying(10, 1, 0.3,0.3, 2);
		/*
		
        if (args.length<6){
            System.out.println("A generator of vertex_copying model.");
			System.out.println("Usage: java -jar vertex_copying.jar n transDen copyProb AlphaSize filename");
			System.out.println("n = size, number of states.");
			System.out.println("transDen=transition density, typically between 1.1 and 4.5");
            System.out.println("finDen=acceptance density, must be between 0 and 1.0, e.g., 0.5");
			System.out.println("copyProb=copying probability, must be between 0 and 1.0, e.g., 0.5");
			System.out.println("AlphaSize =number of symbols, must be an integer >=1");
			System.out.println("filename=The filename (without extension) where the automaton is stored.");
			System.out.println("Output: filename.ba");
			System.out.println("Example: java -jar vertex_copying.jar 100 1.9 0.8 2 testVC");
		}else{
			int n=Integer.parseInt(args[0]);
			float transDen=Float.parseFloat(args[1]);
            float finDen=Float.parseFloat(args[2]);
			float copyProb=Float.parseFloat(args[3]);
			int AlphaSize = Integer.parseInt(args[4]);
			String name=args[5];
			FiniteAutomaton ba=genVertexCopying(n,transDen,copyProb,AlphaSize);
			ba.saveAutomaton(name+".ba");
			
        }
		*/
    }

    // public static void genVertexCopying(int n, float transDen, float copyProb, int AlphaSize){
    public static FiniteAutomaton genVertexCopying(int n, double transDen, double finDen, double copyProb, int AlphaSize){
    
        FiniteAutomaton automataVC = new FiniteAutomaton();
        TreeMap<Integer,FAState> st=new TreeMap<Integer,FAState>();
            
        Random ran = new Random();
        TreeSet<Integer> added=new TreeSet<Integer>(); 
            
        //generate n states
        for(int i=0;i<n;i++){
            st.put(i, automataVC.createState());
        }
    
        Set<Integer> Q = new HashSet<>();	
        Set<Integer> keys = st.keySet();
        for (Integer key : keys){
            Q.add(key);										//set Q
        }
        System.out.println("st"+st+"\n");
        System.out.println("Q"+Q+"\n");

        //finite state generated randomly
		int acceptStateNum=(int)Math.round(n*finDen);

		for(int m=0;m<acceptStateNum-1;m++){
			int i=ran.nextInt(n-1);
			if(!added.contains(i+1)){
				automataVC.F.add(st.get(i+1));
				added.add(i+1);
			}else
				m--;
		}

		automataVC.setInitialState(st.get(0));				//Q0
		automataVC.F.add(st.get(0));                        //F

		// System.out.println("F"+automataAcc.F+"\n");

		Set<Integer> F = new HashSet<>();
		Iterator<FAState> st_it=automataVC.F.iterator();
			while(st_it.hasNext()){
				FAState stnxt=st_it.next();
				F.add(stnxt.id);							
			}
		System.out.println("F"+F+"\n");	

		return automataVC;
    }        
}
