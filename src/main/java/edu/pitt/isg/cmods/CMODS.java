package edu.pitt.isg.cmods;/// Contents: Weighted model of outbreak cases.
/// Author:   John Aronis
/// Date:     January 2019

public class CMODS {

  private Precise[] infectious ;
  private Precise[][] infectiousBins ;

  public CMODS(Data confirmed, int population, int numberOfDays, int predictionDay, double theta, int numberOfOutbreaks, int numberOfModels) {
    RandomModel currentModel ;
    Precise currentScore, totalProbability=new Precise(0.0) ;
    int binNumber ;
    infectious = new Precise[numberOfDays] ;
    for (int d=0 ; d<numberOfDays ; d++) { infectious[d] = new Precise(0.0) ; }
    for (int m=0 ; m<numberOfModels ; m++) {
      currentModel = new RandomModel(population, numberOfDays, numberOfOutbreaks) ;
      currentScore = currentModel.likelihood(confirmed, predictionDay, theta) ;
      for (int d=0 ; d<numberOfDays ; d++) {
        infectious[d] = infectious[d].add( (new Precise(currentModel.infectious(d))).multiply(currentScore) ) ;
      }
      totalProbability = totalProbability.add(currentScore) ;
    }
    for (int d=0 ; d<numberOfDays ; d++) { infectious[d] = infectious[d].divide(totalProbability) ; }
  }

  public double infectious(int day) { return infectious[day].clear() ; }

  public int peakDay() {
    int peakDay = 0 ;
    double maxInfectiousExpected = 0 ;
    for (int day=0 ; day<infectious.length ; day++) {
      if ( infectious[day].clear()>maxInfectiousExpected ) { peakDay=day ; maxInfectiousExpected=infectious[day].clear() ; }
    }
    return peakDay ;
  }

  public double probabilityOfBin(int day, int bin) { return infectiousBins[day][bin].clear() ; }

}

/// End-of-File
