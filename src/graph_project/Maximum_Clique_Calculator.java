/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Social Networking Experiment
 * Kareem Lawal
 * 11/29/2015
 */
public class Maximum_Clique_Calculator {

    private String fileName; 
    private Scanner scanner; 
    public int matrix[][]; 	
    public int degree[]; 	 

    //Maximum Clique Calculator Constructor
    public Maximum_Clique_Calculator(int[][] graphInputFile) {
        // Get the graph in the form of an adjacency matrix
        InitMatrix(graphInputFile);
        // Get the degrees for each vertex
        InitDegree();

        for (int i = matrix.length; i >= 1; i--) {
            // ex: In a 5 node graph, if there aren't 5 nodes with with degree 4, move on.
            if (numOfDegreeOrHigher(i - 1) >= i) {
                // Check for a complete clique of size i
                if (checkClique(i)) {
                    // Found the clique, stop looking
                    return;
                }
                System.out.println("A clique does not exist of size " + i + "...\n");
            }
        }

    }

    //Duplicate Input Array
    @SuppressWarnings("resource")
    private void InitMatrix(int[][] graphInputFile) {


        int[][] myInt = new int[graphInputFile.length][];
        for (int i = 0; i < graphInputFile.length; i++) {
            int[] aMatrix = graphInputFile[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);

        }
        this.matrix = myInt;

    }

    //Set an array of the degrees for each vertex
    private void InitDegree() {
        degree = new int[matrix.length];
        int deg = 0;
        for (int i = 0; i < matrix.length; i++) {
            deg = 0;
            for (int j = 0; j < matrix.length; j++) {
                deg += matrix[i][j];
            }
            degree[i] = deg;
        }
    }

    //Finds the number of vertices with the input degree or higher
  
    public int numOfDegreeOrHigher(int deg) {
        int num = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (degree[i] >= deg) {
                num++;
            }
        }
        return num;
    }

    //Prints the input matrix Used for debugging     
    @SuppressWarnings("unused")
    private void printMatrix() {
        //print what's in Matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.print('\n');
        }
    }

    /**
     * Check if there is a complete clique with the input size
     * size The number of nodes in the clique
     * @return True if there is a complete clique with the input size
     */
    public boolean checkClique(int size) {
        /* Nodes used to store the nodes that fit the degree requirements
         i.e. if we're looking for a clique of size 4, don't include nodes that only have 
         a degree of 1 because there's no way it could be in the complete clique */

        int nodes[] = new int[numOfDegreeOrHigher(size - 1)];
        int count = 0;
        // Get the previously discussed nodes that fit the degree requirements
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] >= size - 1) {
                nodes[count] = i;
                count++;
            }
        }

        // Helper method called, used to check all of the combinations of the nodes array
        // Uses brute force, but cuts down on the number of nodes that need to be checked
        return combination(nodes, size);
    }

    /**
     * Check if the given graph has a complete clique of size "size"
     *
     *nodes The nodes in the graph
     *size The size of the complete clique we're looking for
     *return True if the graph contains a complete clique of size "size"
     */
    private boolean checkCurrClique(int[] nodes, int size) {
        boolean failed = false;

        for (int i = 0; i < nodes.length - 1; i++) // the last node NEVER needs to be checked
        {
            for (int j = i + 1; j < nodes.length; j++) {
                // Debug print statements.
				/*System.out.println("nodes.length = " + nodes.length);
                 System.out.println("i = " + i + ", j = " + j);
                 System.out.println("nodes[i] = " + nodes[i]);
                 System.out.println("nodes[j] = " + nodes[j]);
                 System.out.println("matrix[nodes[i]][nodes[j]] = " + matrix[nodes[i]][nodes[j]]);*/
                if (matrix[nodes[i]][nodes[j]] == 0) {
                    failed = true;
                }
            }
            if (failed) {
                break;
            }
        }

        if (failed) {
            failed = false;
        } else {
            // Found the largest clique
            System.out.println("Maximum clique size: " + size);
            System.out.println("Maximum clique members:");

            for (int i = 0; i < nodes.length; i++) {
                int temp = (nodes[i] + 1);
                System.out.println("\t " + temp + " " +findName(temp));
            }
            return true;
        }
        return false;
    }

    /**
     * Checks all of the nodes for a clique of the given size
     *
     *arr The nodes in the subgraph
     *r The size of the clique we are looking for
     *True if a clique of size r is found
     */
    private boolean combination(int[] arr, int r) {
        int[] res = new int[r];
        // Call the helper method
        return combine(arr, res, 0, 0, r);
    }

    /**
     * Recursive helper method that checks for all of the combinations of the
     * input nodes
     *
     * @param arr The array of nodes
     * @param res
     * @param currIndex
     * @param level
     * @param r
     * return True if a clique of size r is found
     */
    private boolean combine(int[] arr, int[] res, int currIndex, int level, int r) {
        // Check if combo found
        if (level == r) {
            // Check if the combination is a clique
            if (checkCurrClique(res, r)) {
                // Success! Let's get out of here
                return true;
            }
            // Fail, keep on chugging
            return false;
        }
        // Combo not found, keep chugging
        for (int i = currIndex; i < arr.length; i++) {
            res[level] = arr[i];
            if (combine(arr, res, i + 1, level + 1, r)) {
                return true;
            }
            // Avoid printing duplicates
            if (i < arr.length - 1 && arr[i] == arr[i + 1]) {
                i++;
            }
        }
        // Clique of size r was never found, get out of here
        return false;
    }

    String findName(int id) {

        String idString = Integer.toString(id);
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

        int startKnows = Integer.parseInt(input.get(0)) * 2 + 2;
        int startNodeIdentifier = 2;
        int endNodeIdentifier = startKnows - 1;

        ArrayList<String> node_identifier = new ArrayList<>();

        for (int identifier = startNodeIdentifier; identifier <= endNodeIdentifier; identifier++) {
            node_identifier.add(input.get(identifier));

        }

        for (int seek = 0; seek < node_identifier.size(); seek++) {
            if (idString.equals(node_identifier.get(seek))) {
                return (node_identifier.get(seek + 1));

            }

        }
        return (node_identifier.get(id + 1));
    }
}
