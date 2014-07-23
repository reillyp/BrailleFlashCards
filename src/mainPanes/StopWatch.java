/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPanes;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/*
    Copyright (c) 2005, Corey Goldberg

    StopWatch.java is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    
    Time variables changed from long to double for braille application.
    
*/

public class StopWatch {
    
    private double startTime = 0;
    private double stopTime = 0;
    private boolean running = false;
    
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }
    
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }
    
    //elaspsed time in milliseconds
    public double getElapsedTime() {
        double elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        }
        else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }
        
    //elaspsed time in seconds
    public String getElapsedTimeSecs() {
        
        double elapsed;
        String elapsedStr;
        
        // Format seconds to third decimal place (milliseconds). 
        NumberFormat formatter = new DecimalFormat("#0.000"); 
        
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        
        elapsedStr = formatter.format(elapsed);       
        
        return elapsedStr;
    }
    
//    sample usage
//    public static void main(String[] args) {
//        StopWatch s = new StopWatch();
//        s.start();
//        //code you want to time goes here
//        s.stop();
//        System.out.println("elapsed time in milliseconds: " + s.getElapsedTime());
//    }
    
}
