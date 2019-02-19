package edu.pitt.isg.cmods;/// Contents: Calculate MMW numbers
/// Author:   John M. Aronis
/// Date:     February 2019

import java.io.* ;

public class MMW {

  public static int rawWeek(String fileName, int year, int week) {
    String line ;
    String[] tokens ;
    int weekNumber, result ;
    result = weekNumber = 0 ;
    try {
      FileReader fileReader = new FileReader(fileName) ;
      BufferedReader inputFile = new BufferedReader(fileReader) ;
      inputFile.readLine() ;
      inputFile.readLine() ;
      while ( (line=inputFile.readLine())!=null ) {
        tokens = line.split(",") ;
        if ( Integer.parseInt(tokens[2])==year && Integer.parseInt(tokens[3])==week ) { result = weekNumber ; break ; }
	weekNumber++ ;
      }
      inputFile.close() ;
    } catch (IOException e) {} ;
    return result ;
  }

  public static int MMWWeekNumber(String fileName, int year, int week, int advanceWeeks) {
    String line ;
    String[] tokens ;
    int weekNumber, result ;
    result = weekNumber = 0 ;
    try {
      FileReader fileReader = new FileReader(fileName) ;
      BufferedReader inputFile = new BufferedReader(fileReader) ;
      inputFile.readLine() ;
      inputFile.readLine() ;
      while ( (line=inputFile.readLine())!=null ) {
        tokens = line.split(",") ;
        if ( Integer.parseInt(tokens[2])==year && Integer.parseInt(tokens[3])==week ) { result = weekNumber ; break ; }
	weekNumber++ ;
      }
      for (int n=0 ; n<advanceWeeks ; n++) {
        line=inputFile.readLine() ;
        tokens = line.split(",") ;
	result = Integer.parseInt(tokens[3]) ;
      }
      inputFile.close() ;
    } catch (IOException e) {} ;
    return result ;
  }

}

/// End-of-File
