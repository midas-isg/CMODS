package edu.pitt.isg.cmods;/// Contents: Read counts of cases from CDC FluView file.
/// Author:   John M. Aronis
/// Date:     January 2019

import java.io.* ;

public class Data {

  private int[] countsWeekly ;
  private int[] countsDaily ;
  private int WEEK = 7 ;

  public Data(String fileName, String columnName, int startYear, int startWeek, int numberOfWeeks) {

    String line ;
    String[] tokens ;
    int columnNumber, weekNumber ;
    boolean inRange ;
    countsWeekly = new int[numberOfWeeks] ;
    try {
      FileReader fileReader = new FileReader(fileName) ;
      BufferedReader inputFile = new BufferedReader(fileReader) ;
      inputFile.readLine() ;
      line = inputFile.readLine() ;
      tokens = line.split(",") ;
      columnNumber = 0 ;
      while ( !tokens[columnNumber].equals(columnName) ) columnNumber++ ;
      inRange = false ;
      weekNumber = 0 ;
      while ( (line=inputFile.readLine()) != null ) {
        tokens = line.split(",") ;
        if ( Integer.parseInt(tokens[2])==startYear && Integer.parseInt(tokens[3])==startWeek ) { inRange=true ; }
        if ( inRange ) { countsWeekly[weekNumber] = Integer.parseInt(tokens[columnNumber]) ; weekNumber++ ; }
        if ( weekNumber == numberOfWeeks ) { inRange=false ; }
      }
      inputFile.close() ;
    } catch (IOException e) {} ;
    countsDaily = new int[(numberOfWeeks*WEEK)-1] ;
    double change ;
    for (int day=0 ; day<countsDaily.length ; day++) {
      countsDaily[day] = countsWeekly[day/WEEK] ;
//      countsDaily[day] += (((double)(day%WEEK))/((double)WEEK)) * (countsWeekly[(day/WEEK)+1]-countsWeekly[day/WEEK]) ;
    }
  }

  public int numberOfWeeks() { return countsWeekly.length ; }

  public int countWeekly(int week) { return countsWeekly[week] ; }

  public int numberOfDays() { return countsDaily.length ; }

  public int countDaily(int day) { return countsDaily[day] ; }

  public int peakDay() {
    int peakWeek = 0 ;
    for (int week=0 ; week<countsWeekly.length ; week++) {
      if ( countsWeekly[week]>countsWeekly[peakWeek] ) { peakWeek = week ; }
    }
    return WEEK*peakWeek ;
  }

}

/// End-of-File
