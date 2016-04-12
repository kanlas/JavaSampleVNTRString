/*
 * VNTR String coding sample
 * Created by Kelly Anlas for Oxer Technologies
 * 3/20/2016
 */
package vntrstringproject;

//This class creates an object that contains an index, repetitions, and length

public class CollectData {
    int index, reps, length;
    
    public CollectData (int index, int reps, int length) {
        this.index = index;
        this.reps = reps;
        this.length = length;
    }
    
    public void print () {
        System.out.println(index+ " "+ reps + " " +length);
    }
}
