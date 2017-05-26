package src.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Enoc Martinez <e@enocmartinez.com>
 *
 */
public class FWriterCheckTests {
    public static BufferedWriter bw = null;

    public static void open(String tag) {
        try {
            bw = new BufferedWriter(new FileWriter("report_"+tag+".txt"));
        } catch (IOException ex) {
            Logger.getLogger(FWriterCheckTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void write(String line) {
        try {
            bw.write(line);
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(FWriterCheckTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void finish() {
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(FWriterCheckTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
