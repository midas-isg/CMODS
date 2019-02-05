/// Contents: Run Count-based ODS
/// Author:   John Aronis
/// Date:     February 2019

package edu.pitt.isg.cmods;

public class RunCMODS {

  private static int NUMBER_OF_OUTBREAKS = 1 ;
  private static int WEEK = 7 ;

  public static void main(String[] args) {

    String fileName, columnName ;
    int    startYear, startWeek, numberOfWeeks, predictionYear, predictionWeek, population, predictionDay, numberOfModels, week ;
    double theta ;
    Data confirmed ;
    CMODS weightedModel ;

    if ( args.length != 10) {
      System.out.println(
        "Args: fileName columnName startYear startWeek numberOfWeeks predictionYear predictionWeek population theta numberOfModels"
      ) ;
      return ;
    }

    fileName = args[0] ;
    columnName = args[1] ;
    startYear = Integer.parseInt(args[2]) ;
    startWeek = Integer.parseInt(args[3]) ;
    numberOfWeeks = Integer.parseInt(args[4]) ;
    predictionYear = Integer.parseInt(args[5]) ;
    predictionWeek = Integer.parseInt(args[6]) ;
    population = Integer.parseInt(args[7]) ;
    theta = Double.parseDouble(args[8]) ;
    numberOfModels = Integer.parseInt(args[9]) ;

    predictionDay = WEEK*(MMW.rawWeek(fileName,predictionYear,predictionWeek)-MMW.rawWeek(fileName,startYear,startWeek)) ;

    confirmed = new Data(fileName, columnName, startYear, startWeek, numberOfWeeks) ;

    weightedModel = new CMODS(confirmed, population, confirmed.numberOfDays(), predictionDay, theta, NUMBER_OF_OUTBREAKS, numberOfModels) ;

    int predictedPeakWeek = MMW.MMWWeekNumber(fileName, startYear, startWeek, weightedModel.peakDay()/WEEK) ;

/*

    System.out.println( "fileName:              " + fileName ) ;
    System.out.println( "columnName:            " + columnName ) ;
    System.out.println( "startYear:             " + startYear ) ;
    System.out.println( "startWeek:             " + startWeek ) ;
    System.out.println( "numberOfWeeks:         " + numberOfWeeks ) ;
    System.out.println( "predictionYear:        " + predictionYear ) ;
    System.out.println( "predictionWeek:        " + predictionWeek ) ;
    System.out.println( "predictionDay:         " + predictionDay ) ;
    System.out.println( "population:            " + population ) ;
    System.out.println( "theta:                 " + theta ) ;
    System.out.println( "numberOfModels:        " + numberOfModels ) ;
    System.out.println( "NUMBER_OF_OUTBREAKS:   " + NUMBER_OF_OUTBREAKS ) ;
    System.out.println( "MIN_BASELINE:          " + RandomModel.MIN_BASELINE ) ;
    System.out.println( "MAX_BASELINE:          " + RandomModel.MAX_BASELINE ) ;
    System.out.println( "MIN_START_DAY:         " + RandomModel.MIN_START_DAY ) ;
    System.out.println( "MAX_START_DAY:         " + RandomModel.MAX_START_DAY ) ;
    System.out.println( "MIN_INITIAL:           " + RandomModel.MIN_INITIAL ) ;
    System.out.println( "MAX_INITIAL:           " + RandomModel.MAX_INITIAL ) ;
    System.out.println( "MIN_R0:                " + RandomModel.MIN_R0 ) ;
    System.out.println( "MAX_R0:                " + RandomModel.MAX_R0 ) ;
    System.out.println( "MIN_SUSCEPTIBLE:       " + RandomModel.MIN_SUSCEPTIBLE ) ;
    System.out.println( "MAX_SUSCEPTIBLE:       " + RandomModel.MAX_SUSCEPTIBLE ) ;
    System.out.println( "MIN_LATENT_PERIOD:     " + RandomModel.MIN_LATENT_PERIOD ) ;
    System.out.println( "MAX_LATENT_PERIOD:     " + RandomModel.MAX_LATENT_PERIOD ) ;
    System.out.println( "MIN_INFECTIOUS_PERIOD: " + RandomModel.MIN_INFECTIOUS_PERIOD ) ;
    System.out.println( "MAX_INFECTIOUS_PERIOD: " + RandomModel.MAX_INFECTIOUS_PERIOD ) ;
    System.out.println( "Actual peak day:       " + confirmed.peakDay() ) ;
    System.out.println( "Predicted peak day:    " + weightedModel.peakDay() ) ;
    System.out.println( "Predicted peak week:   " + predictedPeakWeek ) ;

*/

    System.out.println( "Location,Target,Type,Unit,Bin_start_incl,Bin_end_notincl,Value") ;

    int peakDay ;
    double rate ;

    peakDay = weightedModel.peakDay() ;
    rate = weightedModel.infectious(peakDay)*0.01*theta ;
    System.out.println("Overall, Season peak week, Point, Week, NA, NA, " + predictedPeakWeek) ;

/*

    System.out.println("Overall, Season peak percentage, Point, percent, NA, NA," + rate) ;

    peakDay += WEEK ;
    rate = weightedModel.infectious(peakDay)*0.01*theta ;
    System.out.println("Overall, 1 wk ahead, Point, percent, NA, NA, " + rate) ;

    peakDay += WEEK ;
    rate = weightedModel.infectious(peakDay)*0.01*theta ;
    System.out.println("Overall, 2 wk ahead, Point, percent, NA, NA, " + rate) ;

    peakDay += WEEK ;
    rate = weightedModel.infectious(peakDay)*0.01*theta ;
    System.out.println("Overall, 3 wk ahead, Point, percent, NA, NA, " + rate) ;

    peakDay += WEEK ;
    rate = weightedModel.infectious(peakDay)*0.01*theta ;
    System.out.println("Overall, 4 wk ahead, Point, percent, NA, NA, " + rate) ;

*/

  }

}

/// End-of-File
