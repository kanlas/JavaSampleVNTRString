/*
 * VNTR String coding sample
 * Created by Kelly Anlas for Oxer Technologies
 * 3/20/2016
 */
package vntrstringproject;

/*
This class contains all methods related to parsing a String to find all
VNTR for a given minimum length of repeat and a minimum amount of repeats.
*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/*
Data dictionary:
suffixmap   - HashMap<String, List<Integer>>- used to store occasions of each 
            found pattern per search size
data        - Collection<CollectData>   - all found VNTR entries
list        - List<Integer> - a list of indices per pattern
strand      - String    - the input string
offsetCounter - int - used for large patterns that could only repeat min times
x           - int   - pattern size counter, also "num" in methods
minReps     - int   - the minimum number of repetitions
minRepsMult - int   - minReps+1, used to determine when to use largeParse
errorStr    - String - used to track errors
*/

public class VNTR {
    HashMap<String, List<Integer>> suffixMap;
    Collection<CollectData> data;
    List<Integer> list;
    String strand, errorStr;
    int offsetCounter,x;
    final int minReps = 4, minRepsMult = 5, minLength = 5;
    
    public VNTR () {
        data = new HashSet<>();
        offsetCounter = 0;
    }
    
    //This method calls the parsing process and loops through pattern sizes
    public void beginProcess (String strand) {
        this.strand = strand;
        if (strand.length() < minReps*minLength) {
            errorStr = "Input too short.";
        }
        else {
            x = strand.length()/minReps;
        
            System.out.println("Processing...");
        
            while (x > 4) {
                if (x > strand.length()/minRepsMult) {
                    largeParse(x);
                }
                else {
                    parse(x);
                    interpret();
                }
                x--;
                offsetCounter+=4;
            }
            exportData();
        }
    }
    
    //This method is used when the pattern size could only repeat min times
    private void largeParse (int num) {
        for (int i=0; i<=offsetCounter; i++) {
            String pattern1 = strand.substring(i,i+(num*2));
            String pattern2 = strand.substring(i+(num*2), i+(num*4));
            if (pattern1.equals(pattern2)) {
                pattern1 = strand.substring(i, i+num);
                pattern2 = strand.substring(i+num, i+num*2);
                if (pattern1.equals(pattern2)) {
                    add(i, 4, num);
                }
            }
        }
    }
    
    //This method is for all other patterns
    private void parse (int num) {
        suffixMap = new HashMap<>();
        int i=0;
        while (i <= strand.length()-num) {
            String pattern = strand.substring(i, i+num);
            if (suffixMap.containsKey(pattern)) {
                list = suffixMap.get(pattern);
                suffixMap.remove(pattern);
                if (list.contains(i-num) || list.isEmpty()) {
                        list.add(i);
                    }
                else if (i >= num &&  i > list.get(list.size()-1)+num){
                    if (list.size() > 3) {
                        add(list.get(0), list.size(), pattern.length());
                    }
                    list.clear();
                }
                suffixMap.put(pattern, list);
            }
            else if (i <= strand.length()-num*4){
                    list = new ArrayList<>();
                    list.add(i);
                    suffixMap.put(pattern, list);
                }
            i++;
        }
    }
    
    //If the first half of the pattern equals the second, add that entry
    private void parseHalf (int start, int x, int reps) {
        String pattern = strand.substring(start, start+x);
        if (pattern.equals(strand.substring(start+x, start+(x*2)))) {
            if (start+x*reps < strand.length()-x) {
                if (pattern.equals(strand.substring(start+(x*reps), start+(x*reps)+x))) {
                    reps++;
                }
            }
            add(start, reps, pattern.length());
        }
    }
    
    //This method is called after parse to input the entries to the collection
    private void interpret () {
        for (Map.Entry<String, List<Integer>> entry: suffixMap.entrySet())
        {
            list = suffixMap.get((String)entry.getKey());
            if (list.size() > 3) {
                String pattern = (String)entry.getKey();
                add(list.get(0), list.size(), pattern.length());
            }
        }
    }
    
    //This method adds an entry to collection and calls parseHalf, if applicable
    private void add (int init, int reps, int length) {
        CollectData entry = new CollectData(init, reps, length);
        if (!data.contains(entry)) {
            data.add(entry);
            //if (length%2 == 0 && length/2 > 4) {
               // parseHalf(init, length/2, reps*2);
            //}
        }
    }
    
    //This method exports the collection at the end of the program
    private void exportData () {
        if (data.isEmpty()) {
            errorStr = "No found repetitions.";
        }
        for (CollectData e : data)
        {
            e.print();
        }
    }
    
    public String getError() {
        return errorStr;
    }
    
    public Collection<CollectData> getData () {
        return data;
    }
}
