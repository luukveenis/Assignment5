/* MWST.java
   CSC 225 - Summer 2013
   Assignment 5 - Template for a Minimum Weight Spanning Tree algorithm
   
   The assignment is to implement the mwst() method below, using any of the minimum 
   spanning tree algorithms studied in the course. The mwst() method computes
   a minimum weight spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in the correct worst case
   running time.

   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java MWST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
    java MWST graphs.txt
	
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
    3
	0 1 0
	1 0 2
	0 2 0
	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   B. Bird - 03/11/2012
*/

import java.util.Scanner;
import java.io.File;
import java.util.PriorityQueue;
import java.util.ArrayList;


public class MWST{
	
	private static final boolean DEBUG = true;
	
	/**
	 * Initializes all entries in the adjacency matrix to the maximum integer value (infinity)
	 * @param adjacency - the adjacency matrix to be initialized
	 */
	static void initializeMatrix(int[][] adjacency){
		int numVerts = adjacency.length;
		for (int i=0; i<numVerts; i++){
			for (int j=0; j<numVerts; j++){
				adjacency[i][j] = Integer.MAX_VALUE; //set each index
			}
		}
	}


	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all edges in a minimum weight spanning tree.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static int mwst(int[][] G){
		int numVerts = G.length;
		
		//use prim's algorithm
		boolean[] inTree = new boolean[numVerts]; // set when removing from queue
		ArrayList<HeapEntry> MST = new ArrayList<HeapEntry>();
		int vertsInTree = 0; //when we have n vertices in the MST we know we're done
		int newestVertex;
		PriorityQueue<HeapEntry> queue = new PriorityQueue<HeapEntry>(); //tracks all edges going out of cloud

		
		newestVertex = 0; //start from vertex 0
		inTree[0] = true;
		vertsInTree++;
		
		while (vertsInTree < numVerts){
			// add all edges leaving newest vertex to heap
			// don't add the edge going back to where we came from
			for (int i=0; i<numVerts; i++){
				if (G[newestVertex][i] != 0 && !inTree[i]){
					HeapEntry entry = new HeapEntry(G[newestVertex][i], newestVertex, i);
					queue.add(entry);
				}
			}
			
			//Get the smallest outgoing edge and add it to the MST
			//Update all corresponding fields
			boolean alreadyInTree = true;
			HeapEntry min = queue.remove();
			while (alreadyInTree){
				if (!inTree[min.endVertex]) alreadyInTree = false; //get the next smallest if end vertex is already in the cloud
				else min = queue.remove();
			}
			MST.add(min); //add the minimum weighted edge into the cloud
			vertsInTree++;
			newestVertex = min.endVertex; //set newly added vertex so as to add its outgoing edges to the priority queue
			inTree[min.endVertex] = true; //
		}
		
		
		
		/* Add the weight of each edge in the minimum weight spanning tree
		   to totalWeight, which will store the total weight of the tree.
		*/
		int totalWeight = 0;
		/* ... Your code here ... */
		for (HeapEntry entry:MST){
			totalWeight += entry.edgeWeight;
			if(DEBUG){
				System.out.println("(" + entry.startVertex + "," + entry.endVertex + ")");
			}
		}
		
		
		return totalWeight;
		
	}


	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(!s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				G[i] = new int[n];
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			if (!isConnected(G)){
				System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
				continue;
			}
			int totalWeight = mwst(G);
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
				
		}
	}

	/* isConnectedDFS(G, covered, v)
	   Used by the isConnected function below.
	   You may modify this, but nothing in this function will be marked.
	*/
	static void isConnectedDFS(int[][] G, boolean[] covered, int v){
		covered[v] = true;
		for (int i = 0; i < G.length; i++)
			if (G[v][i] > 0 && !covered[i])
				isConnectedDFS(G,covered,i);
	}
	   
	/* isConnected(G)
	   Test whether G is connected.
	   You may modify this, but nothing in this function will be marked.
	*/
	static boolean isConnected(int[][] G){
		boolean[] covered = new boolean[G.length];
		for (int i = 0; i < covered.length; i++)
			covered[i] = false;
		isConnectedDFS(G,covered,0);
		for (int i = 0; i < covered.length; i++)
			if (!covered[i])
				return false;
		return true;
	}

}