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
// 	genRandomAcc(10, 1.0, 0.3, 2);
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
		
    }
	
	
	/**
	 * Generate automata using co-accessble approach
	 * 
	 * @param n
	 * @param transDen
	 * @param finDen
	 * @param AlphaSize
	 * 
	 * @return a random finite automata
	 */

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

		for(int m=0;m<acceptStateNum;m++){
			int i=ran.nextInt(n-1);
			if(!added.contains(i+1)){
				automataAcc.F.add(st.get(i+1));
				added.add(i+1);
			}else
				m--;
		}
		automataAcc.setInitialState(st.get(0));				//Q0

		Set<Integer> F = new HashSet<>();
		Iterator<FAState> st_it=automataAcc.F.iterator();
			while(st_it.hasNext()){
				FAState stnxt=st_it.next();
				F.add(stnxt.id);							
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

		Set<Integer> U_ = new HashSet<>();				//set U_, to test if all of the set U is empty
		Set<Integer> Utemp = new HashSet<>();			//set Utemp, tempoary set used only in iteration
		Set<Integer> Ctemp = new HashSet<>();			//set Ctemp, tempoary set used only in iteration

		for (int a = 0;a <AlphaSize; a++){
			added.clear();
			U_.clear();
			Utemp.addAll(U);
			Ctemp.clear();
			Ctemp.addAll(C);
			do{
				for (int t = 0;t<Math.floor(transDen*n);t++){
					
					Iterator<Integer> cIte = Ctemp.iterator();
					Iterator<Integer> uIte = Utemp.iterator();
				
					if(cIte.hasNext() && uIte.hasNext()){
						Integer cValue = cIte.next();
						Integer uValue = uIte.next();
						// System.out.println("cValue"+cValue);
						// System.out.println("uValue"+uValue);
						
						if (!added.contains(uValue*n+cValue)){
							U_.add(uValue);
							automataAcc.addTransition(st.get(uValue), st.get(cValue),("a"+a));
							added.add(uValue*n+cValue);
							Utemp.remove(uValue);
							// System.out.println("Utemp"+Utemp);
							Ctemp.add(uValue);
							// System.out.println("Ctemp"+Ctemp);

						}else{
							t--;
						}
					}else{
						break;
					}
				}	
			}while (!U_.equals(U));
			continue;
		}
		return automataAcc;
    }
}

