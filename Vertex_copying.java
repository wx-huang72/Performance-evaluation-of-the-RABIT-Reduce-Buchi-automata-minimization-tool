package mainfiles;

// import java.lang.management.*;

import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.lang.Math;

import automata.FAState;
import automata.FiniteAutomaton;
import datastructure.HashSet;

//vertex copying automate generator

public class Vertex_copying{
    public static void main(String[] args) {
		// genVertexCopying(100, 1.3, 0.3, 0.5, 3);
		
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
			System.out.println("Example: java -jar vertex_copying.jar 100 1.9 0.8 0.5 2 testVC");
		}else{
			int n=Integer.parseInt(args[0]);
			float transDen=Float.parseFloat(args[1]);
            float finDen=Float.parseFloat(args[2]);
			float copyProb=Float.parseFloat(args[3]);
			int AlphaSize = Integer.parseInt(args[4]);
			String name=args[5];
			FiniteAutomaton ba=genVertexCopying(n,transDen,finDen,copyProb,AlphaSize);
			ba.saveAutomaton(name+".ba");
        }
		
    }

	/**
	 * Generate vertex copying model
	 * 
	 * @param n				size
	 * @param transDen		transition density
	 * @param finDen		accepting state density
	 * @param copyProb		copying probability
	 * @param AlphaSize		number of symbols
	 * @return a random vertex-copying automata				
	 */
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
            Q.add(key);										//Q, set of all states
        }

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

		automataVC.setInitialState(st.get(0));				//Q0, initial state
		automataVC.F.add(st.get(0));                        //F, accepting states

		Set<Integer> F = new HashSet<>();
		Iterator<FAState> st_it=automataVC.F.iterator();
			while(st_it.hasNext()){
				FAState stnxt=st_it.next();
				F.add(stnxt.id);							
			}
				
		for (int a = 0; a < AlphaSize;a++){
			added.clear();
			int i=ran.nextInt(n);
			int j=ran.nextInt(n);
			automataVC.addTransition(st.get(i),st.get(j),("a"+a));
			added.add(i*n+j);
			for(int t = 0; t < Math.floor(transDen*n)-1;t++){
				double r = Math.random();
				
				if(r<=copyProb){			//if the rendom number generated between 0-b, then copy, else generate randomly
					//Copy  
					int u=ran.nextInt(n);
					int u_=ran.nextInt(n);
					int v=ran.nextInt(n);
					if(added.contains(u*n+v) && !added.contains(u_*n+v)){
						automataVC.addTransition(st.get(u_),st.get(v),("a"+a));
						added.add(u_*n+v);
					}
					else
						t--;
				}else{
					//Randomly generate
					int uGene=ran.nextInt(n);
					int vGene=ran.nextInt(n);
					if(!added.contains(uGene*n+vGene)){
						automataVC.addTransition(st.get(uGene),st.get(vGene),("a"+a));
						added.add(uGene*n+vGene);
					}
					else
						t--;
				}
							
			}
		}
		return automataVC;
    }        
}
