/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_project;

import java.text.DecimalFormat;

/**
 * Social Networking Experiment
 * Kareem Lawal
 * 11/29/2015
 */
public class All_Pair_Shortest_Path {

    private int distancematrix[][];
    private int numberofvertices;
    public static final int INFINITY = 999;

    public All_Pair_Shortest_Path(int numberofvertices) {
        distancematrix = new int[numberofvertices][numberofvertices];
        this.numberofvertices = numberofvertices;
    }

    public void All_shortest_pair(int adjacencymatrix[][]) {
        for (int source = 0; source < numberofvertices; source++) {
            for (int destination = 0; destination < numberofvertices; destination++) {
                distancematrix[source][destination] = adjacencymatrix[source][destination];
            }
        }

        for (int intermediate = 0; intermediate < numberofvertices; intermediate++) {
            for (int source = 0; source < numberofvertices; source++) {
                for (int destination = 0; destination < numberofvertices; destination++) {
                    if (distancematrix[source][intermediate] + distancematrix[intermediate][destination]
                            < distancematrix[source][destination]) {
                        distancematrix[source][destination] = distancematrix[source][intermediate]
                                + distancematrix[intermediate][destination];
                    }
                }
            }
        }

        double averageShortestPath = 0;
        int pair = 0;
        double totalShortestPath = 0;
        int longestShoPath = 0, temp = 0;

        
        for (int source = 0; source < numberofvertices; source++) {
//            System.out.println("\t");
            for (int destination = 0; destination < numberofvertices; destination++) {
                totalShortestPath += distancematrix[source][destination];
                temp = distancematrix[source][destination];
                if (temp > longestShoPath) {
                    longestShoPath++;
                }
                pair++;
//                System.out.print(distancematrix[source][destination] + "\t");
                if (distancematrix[source][destination] == 0) {
                    pair--;
                }
            }
        }

        System.out.println();
        averageShortestPath = totalShortestPath / pair;
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("Average shortest path length: " + df.format(averageShortestPath));
        System.out.println("Longest shortest path length: " +longestShoPath);
    }

}
