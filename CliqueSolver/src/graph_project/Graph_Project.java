/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_project;

/**
 * Social Networking Experiment
 * Kareem Lawal
 * 11/29/2015
 */
import java.util.*;
import java.io.*;

public class Graph_Project {

    public static void main(String[] args) throws IOException {

        MatrixFunction mf;

        //Read number of lines        
        BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }
        reader.close();
//        System.out.println("Lines: " + lines);

        //Tokenize source file into "input" ArrayList
        Scanner scan;
        StringTokenizer st;
        ArrayList<String> input = new ArrayList<>();
        File file = new File("file.txt");
        try {
            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.next();
                st = new StringTokenizer(line);
                input.add(st.nextToken());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Print out Number of Vertices and Edges from first and second line of Arraylist
        int vertex = Integer.parseInt(input.get(0));
        int edge = Integer.parseInt(input.get(1));

        System.out.println("Vertices: " + input.get(0));
        System.out.println("Edges: " + input.get(1));
//        System.out.println("Start at:  " + input.get(18));   

        //Initialize where "People" starts from the ArrayList
        int startKnows = Integer.parseInt(input.get(0)) * 2 + 2;
//        System.out.println("Start: " + start);

        int startNodeIdentifier = 2;
        int endNodeIdentifier = startKnows - 1;
        ArrayList<String> node_identifier = new ArrayList<>();
        for (int id = startNodeIdentifier; id <= endNodeIdentifier; id++) {
            node_identifier.add(input.get(id));
        }
//        for (String a: node_identifier){System.out.print(a +"\n");}

        int to = 0, from = 0;
//        for (int a = start; a < input.size(); a++){System.out.print(" " +input.get(a) + " ");}
        int[][] shortestPathGraph = new int[vertex][vertex]; //Initialize  and populate Adjacency Matrix by calling methods in MatrixFuntion
        for (int i = startKnows; i < input.size(); i++) {
            from = (Integer.parseInt(input.get(i)));
            i++;
            to = (Integer.parseInt(input.get(i)));
            mf = new MatrixFunction(shortestPathGraph, vertex);
            mf.makeEdge(shortestPathGraph, to, from, 1);
        }

//        System.out.println(Arrays.deepToString(shortestPathGraph));
        
        //Calculate Minimum, Maximum and Average Degree by iterating the Adjacency Matrix "shortestPathGraph"
        int max = 0, min = 0, temp = 0, totalKnows = 0;
        for (int i = 0; i < shortestPathGraph.length; i++) {
            temp = 0;
            mf = new MatrixFunction(shortestPathGraph, vertex);
            for (int j = 0; j < shortestPathGraph.length; j++) {
                int node = mf.getEdge(shortestPathGraph, i, j);
                if (node == 1) {
                    temp++;
                    totalKnows++;
                    if (j == 1) {
                        min = temp;
                    }
                    if (max < temp) {
                        max = temp;
                    } else if (min > temp) {
                        min = temp;
                    }
                }
            }
        }

        //Cloning the Adjacency Matrix array to be able to use one to calculate Average All-Pair Shortest path 
        //and the other to calculate Clique
         
        int[][] clonedArray = new int[shortestPathGraph.length][];
        for (int i = 0; i < shortestPathGraph.length; i++) {
            clonedArray[i] = shortestPathGraph[i].clone();
        }

        System.out.println();
        double averageDegree = (double) totalKnows / vertex;
        System.out.println("Minimum Degree: " + min);
        System.out.println("Maximum Degree: " + max);
        System.out.println("Average Degree: " + averageDegree);

        
        //Calculate Average All-Pair Shortest Path by cancelling out self knows (eg. bob must not know bob)
        mf = new MatrixFunction(shortestPathGraph, vertex);
        final int INFINITY = 999;
        for (int source = 0; source < vertex; source++) {
            for (int destination = 0; destination < vertex; destination++) {
                int node = mf.getEdge(shortestPathGraph, source, destination);
                shortestPathGraph[source][destination] = node;
                if (source == destination) {
                    shortestPathGraph[source][destination] = 0;
                    continue;
                }
                if (shortestPathGraph[source][destination] == 0) {
                    shortestPathGraph[source][destination] = INFINITY;
                }
            }

        }
//      System.out.println(Arrays.deepToString(shortestPathGraph));
//        System.out.println(Arrays.deepToString(clonedArray));

        System.out.println();
//        System.out.println("The Transitive Closure of the Graph");
        All_Pair_Shortest_Path allPairShortestPath = new All_Pair_Shortest_Path(vertex);
        allPairShortestPath.All_shortest_pair(shortestPathGraph);

        System.out.println();

        //Calculate Maximum Clique with the Cloned Array
        Maximum_Clique_Calculator cs = new Maximum_Clique_Calculator(clonedArray);
    }

}
