package edu.pitt.isg.cmods;
/// Contents: Random model composed of multiple SEIR outbreaks
/// Author:   John Aronis
/// Date:     January 2019

import java.util.Random ;

public class RandomModel {

//  public static int    MIN_BASELINE                = 100 ;
//  public static int    MAX_BASELINE                = 1000 ;

  public static int    MIN_BASELINE                = 100000 ;
  public static int    MAX_BASELINE                = 1000000 ;

  public static int    MIN_START_DAY               = 50 ;
  public static int    MAX_START_DAY               = 300 ;
  public static int    MIN_INITIAL                 = 1 ;
  public static int    MAX_INITIAL                 = 10 ;
  public static double MIN_R0                      = 1.1 ;
  public static double MAX_R0                      = 10.0 ;
  public static double MIN_SUSCEPTIBLE             = 0.10 ;
  public static double MAX_SUSCEPTIBLE             = 0.90 ;
  public static double MIN_LATENT_PERIOD           = 1.0 ;
  public static double MAX_LATENT_PERIOD           = 4.0 ;
  public static double MIN_INFECTIOUS_PERIOD       = 1.0 ;
  public static double MAX_INFECTIOUS_PERIOD       = 10.0 ;

  private int population, numberOfDays, numberOfOutbreaks, baseline, totalInfectious ;
  private Outbreak[] outbreaks ;
  private double[] infectious ;

  private static Random random = new Random() ;
  private static int random(int low, int high) { return low + random.nextInt((high+1)-low) ; }
  private static double random(double low, double high) { return low + random.nextDouble()*(high-low) ; }

  public RandomModel(int population, int numberOfDays, int numberOfOutbreaks) {
    this.population = population ;
    this.numberOfDays = numberOfDays ;
    this.numberOfOutbreaks = numberOfOutbreaks ;
    this.baseline = random(MIN_BASELINE,MAX_BASELINE) ;
    outbreaks = new Outbreak[numberOfOutbreaks] ;
    do {
      infectious = new double[numberOfDays] ;
      for (int n=0 ; n<numberOfOutbreaks ; n++) {
        outbreaks[n] = new Outbreak(population,
                                    numberOfDays,
                                    random(MIN_START_DAY,MAX_START_DAY),
                                    random(MIN_INITIAL,MAX_INITIAL),
                                    random(MIN_R0,MAX_R0),
                                    random(MIN_SUSCEPTIBLE,MAX_SUSCEPTIBLE),
                                    random(MIN_LATENT_PERIOD,MAX_LATENT_PERIOD),
                                    random(MIN_INFECTIOUS_PERIOD,MAX_INFECTIOUS_PERIOD)
                                   ) ;
        for (int d=0 ; d<numberOfDays ; d++) { infectious[d] += baseline + outbreaks[n].infectious(d) ; }
      }
    } while ( !legal(infectious,population) ) ;
  }

  private boolean legal(double[] values, int maxValue) {
    for (int n=0 ; n<values.length ; n++) { if ( values[n]<0 || values[n]>maxValue ) return false ; }
    return true ;
  }

  public int numberOfDays() { return numberOfDays ; }

  public double infectious(int day) { return infectious[day] ; }

  public int peakDay() {
    int peakDay = 0 ;
    double maxInfectious = 0 ;
    for (int day=0 ; day<infectious.length ; day++) {
      if ( infectious[day]>maxInfectious ) { peakDay=day ; maxInfectious=infectious[day] ; }
    }
    return peakDay ;
  }

  public Precise likelihood(Data confirmed, int predictionDay, double theta) {
    Precise likelihood = new Precise(1.0) ;
    for (int day=0 ; day<predictionDay ; day++) {
      if ( this.infectious(day) < confirmed.countDaily(day) ) { return new Precise(0.0) ; }
      likelihood = likelihood.multiply(Precise.bernoulli((int)(this.infectious(day)),confirmed.countDaily(day),theta )) ;
    }
    return likelihood ;
  }

}

/// End-of-File
