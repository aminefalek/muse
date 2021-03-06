// Copyright 2021 Technology & Strategy / ICube, GNU Public License.

package structures;

/**
 * The project contains the implementation of MUSE algorithm for the purpose 
 * of multimodal route planning. The project requires a dataset available at:
 * https://doi.org/10.5281/zenodo.5276749
 * 
 * MUSE has been sumbitted to the Transportation Science Journal in a paper
 * entitled "MUSE: Multimodal Separators for Efficient Route Planning in 
 * Transportation Networks".
 * 
 * This project also includes the implementation of the SDALT algorithm as
 * described in the paper. It allows for reproducing the stated performance
 * evaluation.
 *  
 * @author Amine Falek <a.falek@technologyandstrategy.com>
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NFA {
	public final int nfa_id;
	public int numberOfStates;
	public int numberOfTransitions;
	public List<State> states;
	public List<List<Transition>> transitions;
	
	public NFA(String filepath, int nfa_id) throws IOException {
		this.nfa_id = nfa_id;
		
		loadStates(filepath + "nfa" + String.valueOf(nfa_id) + "_states.txt");
		loadTransitions(filepath + "nfa" + String.valueOf(nfa_id) + "_transitions.txt");
	}
	
	public void loadStates(String filepath) throws IOException {
		
		numberOfStates = 0;
		states = new ArrayList<>();
		
		FileReader file = new FileReader(filepath);
		BufferedReader reader = new BufferedReader(file);
		
		String sCurrentLine;
		while ((sCurrentLine = reader.readLine()) != null) {
			if (sCurrentLine.length() > 0) {
				
				List<String> row = Arrays.asList(sCurrentLine.split(","));
				
				boolean isInitial = "1".equals(row.get(0));
				boolean isFinal = "1".equals(row.get(1));
				char label = row.get(2).charAt(0);
				
				states.add(new State(isInitial, isFinal, label));
				
				numberOfStates++;
			}					
		}
		reader.close();		
	}
	
	public void loadTransitions(String filepath) throws IOException {
		
		numberOfTransitions = 0;
		transitions = new ArrayList<>();
		
		for (int i=0; i<numberOfStates; i++) {
			transitions.add(new ArrayList<>());
		}
		
		FileReader file = new FileReader(filepath);
		BufferedReader reader = new BufferedReader(file);
		
		String sCurrentLine;
		while ((sCurrentLine = reader.readLine()) != null) {
			if (sCurrentLine.length() > 0) {
				
				List<String> row = Arrays.asList(sCurrentLine.split(","));
				
				int from_state = Integer.parseInt(row.get(0));
				int to_state = Integer.parseInt(row.get(1));
				char label = row.get(2).charAt(0);
				
				transitions.get(from_state).add(new Transition(to_state, label));				
				numberOfTransitions++;
			}					
		}
		reader.close();		
	}
}
