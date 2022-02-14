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

public class co_accessible{
    public static void main(String[] args) {
		genRandomAcc(10, 1.0, 0.3, 2);
		/*
		
        if (args.length<5){
            System.out.println("A generator of co-accessible model.");
			System.out.println("Usage: java -jar co_accessible.jar n transDen finDen AlphaSize filename");
			System.out.println("n = size, number of states.");
			System.out.println("transDen=transition density, typically between 1.1 and 4.5");
			System.out.println("finDen=acceptance density, must be between 0 and 1.0, e.g., 0.5");
			System.out.println("AlphaSize =number of symbols, must be an integer >=1");
			System.out.println("filename=The filename (without extension) where the automaton is stored.");
			System.out.println("Output: filename.ba");
			System.out.println("Example: java -jar co_accessible.jar 100 1.9 0.8 2 testAcc");
		}else{
			int n=Integer.parseInt(args[0]);
			float transDen=Float.parseFloat(args[1]);
			float finDen=Float.parseFloat(args[2]);
			int AlphaSize = Integer.parseInt(args[3]);
			String name=args[4];
			FiniteAutomaton ba=genRandomAcc(n,transDen,finDen,AlphaSize);
			ba.saveAutomaton(name+".ba");
			
        }
		*/
    }

    // public static void genRandomAcc(int n, float transDen, float finDen, int AlphaSize){
	public static FiniteAutomaton genRandomAcc(int n, double transDen, double finDen, int AlphaSize){

        FiniteAutomaton automataAcc = new FiniteAutomaton();
		TreeMap<Integer,FAState> st=new TreeMap<Integer,FAState>();
		
		Random ran = new Random();
		TreeSet<Integer> added=new TreeSet<Integer>(); 
		
		//generate n states
		for(int i=0;i<n;i++){
			st.put(i, automataAcc.createState());
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
				automataAcc.F.add(st.get(i+1));
				added.add(i+1);
			}else
				m--;
		}

		automataAcc.setInitialState(st.get(0));				//Q0
		automataAcc.F.add(st.get(0)); //F

		// System.out.println("F"+automataAcc.F+"\n");

		Set<Integer> F = new HashSet<>();
		Iterator<FAState> st_it=automataAcc.F.iterator();
			while(st_it.hasNext()){
				FAState stnxt=st_it.next();
				F.add(stnxt.id);							
				// System.out.println("Iterator stuff ["+stnxt.id+"]\n");				
			}
		System.out.println("F"+F+"\n");	
	
		Set<Integer> C = new HashSet<>();				//set C
		C.addAll(F);
		System.out.println("C"+C+"\n");	

		//U = Q-C
		Set<Integer> U = new HashSet<>();
		U.addAll(Q);
		U.removeAll(C);									//set U					
		System.out.println("U"+U+"\n");	


		//最后条件是当automataAcc.trans == Math.floor(transDen*n) && U.size() == 0

		Iterator<Integer> cIte = C.iterator();
		Iterator<Integer> uIte = U.iterator();

		for (int a = 0;a <AlphaSize; a++){
			added.clear();
			while(cIte.hasNext() && uIte.hasNext()){
				Integer cValue = cIte.next();
				Integer uValue = uIte.next();
				if (!added.contains(uValue*n+cValue)){
					automataAcc.addTransition(st.get(uValue), st.get(cValue),("a"+a));
					added.add(uValue*n+cValue);
					U.remove(uValue);
					C.add(uValue);
				}
			}
	
		}

		// for (int a = 0;a <AlphaSize; a++){
		// 	added.clear();
		// 	for (int t = 0;t<Math.round(transDen*n);t++){
		// 		for (int u: U){
		// 			for (int c:C){
		// 				if (!added.contains(u*n+c)){
		// 					automataAcc.addTransition(st.get(u), st.get(c),("a"+a));
		// 					added.add(u*n+c);
		// 					U.remove(u);
		// 					C.add(u);
		// 				}else{
		// 					t--;
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		
		// System.out.println(U.size());
	
		return automataAcc;

    }
}

