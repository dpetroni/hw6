/*
 * NelderMead.java 1.0 August 20, 2016
 *
 * Copyright (c) 2016 David J. Powell, Elon University
 * Elon, North Carolina, 27244 U.S.A.
 * All Rights Reserved
 */
package edu.elon.math;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import flanagan.math.*;

/*
 * Copyright September 29, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 * 
 *
 */
import java.util.Vector;

/**
 * Nelder Mead also known as direct simplex method is a widely used
 * nonlinear unconstrained optimization technique. The goSimplex code
 * is only partially implemented and always returns a optimal value of
 * 0.0. This class will be replaced in a follow on homework assignment
 * using the Adapter design pattern and code from Michael Flanagan
 * 
 * @author dpowell2
 * @version 1.0
 */
public class NelderMead implements Strategy, Serializable{

  private final double initialSimplexSize = .5;
  private int nDim;
  private double[] startPoint;
  private double[][] vertices;


  private double ftol = 1E-15;
  private Minimisation minimisation = new Minimisation();
  private MinimisationFunction minFunction;

  /**
   * Default constructor to satisfy coding best practices
   */
  public NelderMead() {
    // intentionally empty
  }

  /**
   * Arrays will start at 1 instead of 0 so we need to allocate 1 more
   * space than anticipated.
   * 
   * @param nDim the number of elements in the input
   */
//  public void allocateArrays(int nDim) {
//    startPoint = new double[nDim + 1];
//    vertices = new double[nDim + 2][nDim + 1];
//  }
  
  
  /**
   * inner class that changes input values from array to arrayList, decides whether 
   * it is a maximize or minimize problem, and returns a MinimisationFunction
   * compatible with the nelderMead method.
   */
  public class GetFunction implements MinimisationFunction{
  	private FunctionInterface f;
  /**
   * Default constructor to satisfy coding best practices
   */
  	public GetFunction(FunctionInterface function){
  		this.f = function;
  	}
  /**
   * converts the passed array into an arrayList
   */ 	
    private ArrayList<Double> convertDoubleArrayToArrayList(double[] aInputArray) {
      ArrayList<Double> bestInputPoint = new ArrayList<Double>();
      for (double d : aInputArray) {
        bestInputPoint.add(d);
      }
      return bestInputPoint;
    }
  /**
   * converts the array back into an arrayList, and populates the input fields with the correct values.
   */  	
		@Override
		public double function(double[] param){
			ArrayList<Double> inputs = convertDoubleArrayToArrayList(param);
			try {
				f.setInputValues(inputs);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			double a = 0;
			try {
				a = f.evaluate();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(f.isMinimize()){
					return a;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return a*(-1);
			
		}
  	
  }

  /**
   * 
   * Leaves optimal design as the value of the parameter, function
   * instance, field called inputValues;
   * 
   * @param function Function instance containing function starting
   *        point and evaluation logic
   * @return Double value for optimal design.
 * @throws RemoteException 
   */
  public Double getOptimalValue(FunctionInterface function) throws RemoteException {
  	double objective = 0.0;
  	minFunction = new GetFunction(function);

  	ArrayList<Double> inputs = function.getInputValues();
  	
  	
    int length = inputs.size();
    double[] startPts = new double[length];
    double[] steps = new double[length];
    for (int i = 0; i < length; i++) {
      startPts[i] = inputs.get(i);
      steps[i] = initialSimplexSize * inputs.get(i);
      if(Math.abs(inputs.get(i)) < 1.0){
    	  steps[i] = 1.0;
    	  }
    }
    

    
//  	for (int i = 0; i <= len; i++){	
//  		startPts[i] = inputs.get(i);
//  		steps[i] = initialSimplexSize * inputs.get(i);
//  		if(Math.abs(inputs.get(i)) < 1){
//  			steps[i] = 1;
//  		}
//  	}
  	
    //getElonCopyrightVertices();

    
    
  	minimisation.nelderMead(minFunction, startPts, steps, ftol);
  	minimisation.getMinimum();
  	
  	double[] bestInputs = minimisation.getParamValues();
  	ArrayList<Double> bestList= new ArrayList<Double>();
  	for(int i = 0; i < bestInputs.length; i++){
  		bestList.add(bestInputs[i]);
  	}
  	function.setInputValues(bestList);
  	objective = function.evaluate();
  	
    System.out.println("Nelder Mead stub invoked");
    return new Double(objective);
  }

  @SuppressWarnings("unused")
  private void amoeba() {
    Vector<Double> pSum = new Vector<Double>();
    pSum.add(10.0); // first element is a dummy placeholder
  }

  @SuppressWarnings("unused")
  private void amoeba(double[][] p, double[] y, int elondim, float ftol,
                      Integer nFunk) {
    // not implemented
  }

//  private void createInitialSetOfPoints() {
//  	int counter = 0;
//    for (int i = 2; i < nDim + 2; i++) {
//      for (int j = 1; j < startPoint.length; j++) {
//        double value = initialSimplexSize * startPoint[j];
//        if (Math.abs(startPoint[j]) < 1.0) {
//          value = 1.0;
//        }
//        if ((i - 1) == j) {
//          vertices[i][j] = value;
//          //to get an array of the step points
//          steps[counter] = value;
//          counter++;
//        } else {
//          vertices[i][j] = startPoint[j];
//        }
//      }
//    }
//  }

  private double[][] getElonCopyrightVertices() {
    return vertices;
  }

  @SuppressWarnings("unused")
  private int getNDim() {
    return nDim;
  }

  @SuppressWarnings("unused")
  private double[] getStartPoint() {
    return startPoint;
  }

//  private void setStartingPoint(ArrayList<Double> values) {
//    // 0 row index is the dummy position
//    int col;
//    int row;
//    for (col = 0; col < vertices[0].length; col++) {
//      vertices[0][col] = 0;
//    }
//    // 0 column is not used on each row
//    for (row = 0; row < vertices.length; row++) {
//      vertices[row][0] = 0.0;
//    }
//    // row 1 is the entry design point
//    row = 1;
//    for (col = 0; col < values.size(); col++) {
//      startPoint[col + 1] = values.get(col).doubleValue();
//      vertices[row][col + 1] = startPoint[col + 1];
//    }
//  }

}
