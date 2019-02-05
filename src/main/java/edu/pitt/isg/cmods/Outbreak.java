package edu.pitt.isg.cmods;/// Contents: Single SEIR Outbreak
/// Author:   John Aronis
/// Date:     January 2019

public class Outbreak {

  private int population, initial, start, numberOfDays ;
  private double R0, susceptible, latentPeriod, infectiousPeriod, total ;
  private double[] infectious ;

  public Outbreak(int population, int numberOfDays, int start, int initial, double R0, double susceptible, double latentPeriod, double infectiousPeriod) {
    double S_current, E_current, I_current, R_current, S_next, E_next, I_next, R_next, f, r, lambda, beta ;
    this.population = population ;
    this.initial = initial ;
    this.numberOfDays = numberOfDays ;
    this.start = start ;
    this.R0 = R0 ;
    this.susceptible = susceptible ;
    this.latentPeriod = latentPeriod ;
    this.infectiousPeriod = infectiousPeriod ;
    this.infectious = new double[numberOfDays] ;
    for (int day=0 ; day<start ; day++) { this.infectious[day] = 0.0 ; }
    S_current = susceptible*population ;
    E_current = 0.0 ;
    I_current = initial ;
    R_current = population - (S_current + E_current + I_current) ;
    f = 1.0 / latentPeriod ;
    r = 1.0 / infectiousPeriod ;
    beta = R0/(population*infectiousPeriod) ;
    for (int day=start ; day<this.infectious.length ; day++) {
      infectious[day] = I_current ;
      lambda = beta*I_current ;
      S_next = (S_current - lambda*S_current) ;
      E_next = (E_current + lambda*S_current - f*E_current) ;
      I_next = (I_current + f*E_current - r*I_current) ;
      R_next = (R_current + r*I_current) ;
      S_current = S_next ;
      E_current = E_next ;
      I_current = I_next ;
      R_current = R_next ;
    }
  }

  public double infectious(int day) { return infectious[day] ; }

}

/// End-of-File
