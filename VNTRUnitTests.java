/*
 * VNTR String coding sample
 * Created by Kelly Anlas for Oxer Technologies
 * 3/20/2016
 */

package test;

import vntrstringproject.VNTR;
import org.junit.Test;
import static org.junit.Assert.*;

public class VNTRUnitTests {
    
    public VNTRUnitTests() {
    }
    
    @Test
    public void stringTooShort() {
        VNTR testVNTR = new VNTR();
        testVNTR.beginProcess("AAAA");
        assertEquals("Input too short.", testVNTR.getError());
    }
    
    @Test
    public void noMatches() {
        VNTR testVNTR = new VNTR();
        testVNTR.beginProcess("AAAACCCCTTTTGGGGAAAA");
        assertEquals("No found repetitions.", testVNTR.getError());
    }
    
    @Test
    public void validInput() {
        VNTR testVNTR = new VNTR();
        testVNTR.beginProcess("ACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGA");
        assertEquals(null, testVNTR.getError());
    }
    
    @Test
    public void correctAnswer() {
        VNTR testVNTR = new VNTR();
        testVNTR.beginProcess("ACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGAACTGA");
        assertEquals(16, testVNTR.getData().size());
    }
}
