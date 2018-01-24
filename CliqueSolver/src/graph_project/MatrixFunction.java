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
public class MatrixFunction {
    
    private int vertices;
    private int[][] adjMatrix;

     MatrixFunction(int [][] input, int v) {
        this.vertices = v;
        this.adjMatrix = new int[v][v];
        
    }

    public void makeEdge(int [][] make, int to, int from, int edge) {
        try {
            make[to-1][from-1] = edge;
            make[from-1][to-1] = edge;
        } catch (ArrayIndexOutOfBoundsException index) {
            System.out.println("The vertices does not exists");
        }
    }

    public int getEdge(int [][] get, int to, int from) {
        try {
            return get[to][from];
        } catch (ArrayIndexOutOfBoundsException index) {
            System.out.println("The vertices does not exists");
        }
        return -1;
    }
    
    public int [][] matrix(int [][] matrix){
        
       this.adjMatrix = matrix;
       return matrix;
    }

    
}
