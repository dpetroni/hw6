/*
 * Function.java 1.0 August 20, 2016
 *
 * Copyright (c) 2016 David J. Powell, Elon University
 * Elon, North Carolina, 27244 U.S.A.
 * All Rights Reserved
 */
package edu.elon.math;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * Copyright October 10, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Observable;

/**
 * Standard interface for consistency in the Elon evaluation process.
 * Each concrete Elon Function must extend this class.
 * 
 * @author dpowell2
 * @version 1.0
 */
public abstract class Function extends UnicastRemoteObject implements FunctionInterface{

  /**
	 * 
	 */
	private static final long serialVersionUID = 8643733787434604787L;

/**
   * constant to represent new line
   */
  public static final String EOL = "\n";

  /**
   * constant to represent one blank space
   */
  public static final String SPACE = " ";

  private ArrayList<String> inputNames;

  private ArrayList<Double> inputValues;

  // true if minimization function and false if maximization
  private boolean minimize;

  private String optimizationTechnique = "edu.elon.math.RandomWalk";

  private Double output;
  
  private Strategy strategy;

  private String title;
  
  public ArrayList<ObserverObject> observers = new ArrayList<ObserverObject>();
  
  public String env; 
  
  public boolean changed;

  /**
   * Default constructor
   */
  public Function() throws RemoteException{

  }
  
  public void setStrategy(Strategy aStrategy){
	System.out.println("got here");
  	strategy = aStrategy;
  }
  
  /*
   * notifies the observers that data has changed, and calls all update methods
   */
  
  public void valuesChanged() throws RemoteException{
	  setChanged();
	  notifyObservers();
  }

  public void notifyObservers() throws RemoteException {
	  
	  for(int i = 0 ; i < observers.size(); i++){
		  ObserverObject o = observers.get(i);
		  if(this.changed == true){
			  o.update(this);
		  }
	  }
	  
}

  public void setChanged() {
	if(changed == false){
		changed = true;
	}
	else{
		changed = false;
	}
	
}

/**
   * Evaluates the current set of input values to calculate the
   * function value. We currently consider one output value for a
   * function. If the function has multiple output values then the
   * function must have these combined into a single value.
   * 
   * @return Double of function result from evaluation at current
   *         point.
 * @throws RemoteException 
   */
  public abstract Double evaluate() throws RemoteException;

  /**
   * Returns an ArrayList of String for the names of each input
   * parameter. This allows the class creator to make the names
   * meaningful to a user instead of X1, X2, ...
   * 
   * @return ArrayList<String> of names for each input parameter
   */
  public ArrayList<String> getInputNames() {
    return inputNames;
  }

  /**
   * Returns the current value of each input for the function. All
   * function inputs are treated as Double
   * 
   * @return ArrayList<Double> of values representing current point.
   */
  public ArrayList<Double> getInputValues() {
    return inputValues;
  }

  /**
   * Gets the full package qualified classname of the currently set
   * optimization technique
   * 
   * @return String representing package qualified classname of
   *         optimization technique
   */
  public String getOptimizationTechnique() {
    return optimizationTechnique;
  }

  /**
   * Gets the function output value resulting from the evaluation of
   * the current input set.
   * 
   * @return Double representing function result
   */
  public Double getOutput() {
    return output;
  }

  /**
   * Gets the name of the function
   * 
   * @return String representing the user friendly name of the
   *         function.
   */
  public String getTitle() {
    return title;
  }
  
  public String getEnv() throws RemoteException{
	  env = System.getenv("optimizers");
	  return env;
  }

  /**
   * Gets the direction of the optimization problem. If true then we
   * have a minimization problem otherwise a maximization problem
   * 
   * @return boolean value of true if minimization
   */
  public boolean isMinimize() {
    return minimize;
  }

  /**
   * Optimizes function. The optimizer is required to leave the best
   * design point and function value as the current state of the
   * function in <code>inputValues</code> and <code>output</code>
   * 
   * @return Double representing best achieved function value.
 * @throws RemoteException 
   */
  public Double optimize() throws RemoteException {
    Function function = this;
    Double optimalValue = strategy.getOptimalValue(function);
    

//    if (this.getOptimizationTechnique().contains("NelderMead")) {
//      NelderMead nm = new NelderMead();
//      optimalValue = nm.goSimplex(function);
//      function.getInputValues();
//    } else if (this.getOptimizationTechnique().contains("RandomWalk")) {
//      RandomWalk rw = new RandomWalk();
//      optimalValue = rw.guess(function);
//      function.getInputValues();
//    } else if (this.getOptimizationTechnique().contains("Powell")) {
//      Powell powell = new Powell();
//      optimalValue = powell.findMinimum(function);
//      function.getInputValues();
//    }
//
    return optimalValue;
  }

  /**
   * Set the current set of input names for the input parameters to
   * the inputNames passed as a parameter.
   * 
   * @param inputNames ArrayList<String> of names for set of input
   *        parameters for the function.
   */
  public void setInputNames(ArrayList<String> inputNames) {
    this.inputNames = inputNames;
  }

  /**
   * Sets the current value of the input set for the function.
   * 
   * @param inputValues ArrayList<Double> representing the value of
   *        each input parameter. The input set is assumed to be all
   *        Doubles.
   */
  public void setInputValues(ArrayList<Double> inputValues) {
    this.inputValues = inputValues;
  }

  /**
   * Sets function to be a minimization or a maximization
   * 
   * @param minimize boolean of true if minimization
   */
  public void setMinimize(boolean minimize) {
    this.minimize = minimize;
  }

  /**
   * Sets internal field to the value of the passed parameter which
   * represents the package qualified class name of the optimization
   * technique to use.
   * 
   * @param optimizationTechnique String representing package and
   *        class name of the optimizer to use for the problem.
   */
  public void setOptimizationTechnique(String optimizationTechnique) {
    this.optimizationTechnique = optimizationTechnique;
  }

  /**
   * Sets the value of the function result.
   * 
   * @param output Double instance of function result
 * @throws RemoteException 
   */
  public void setOutput(Double output) throws RemoteException {
    this.output = output;
    notifyObservers();
  }

  /**
   * Sets the user friendly name of the function
   * 
   * @param title String representing function name
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * User friendly representation of the function state and
   * configuration. Shows the function name, input variable names and
   * input variable values
   * 
   * @return String representing state of function.
   */
  @Override
  public String toString() {
    StringBuffer s = new StringBuffer();
    s.append("Function: " + this.getTitle() + EOL);
    for (int i = 0; i < getInputValues().size(); i++) {
      s.append(getInputNames().get(i) + SPACE + getInputValues().get(i) + EOL);
    }
    return s.toString();
  }
  
  public void addObserver(ObserverObject o){
	  observers.add(o);
	  
  }
  public void removeObserver(ObserverObject o){
	  
	  observers.remove(o);
	  
  }

}
