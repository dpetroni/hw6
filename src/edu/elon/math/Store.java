package edu.elon.math;
/*
 * Copyright October 10, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 */
public class Store {
	
  /**
   * default constructor
   */
  public Store(){
		
  }
	
  /*
   * tells the factory to instantiate a new optimizer strategy
   */

  public Strategy instantiateStrategy(String technique){
	
	SimpleFactory factoryInstance = SimpleFactory.getInstance();
	Strategy strategy;
	strategy = factoryInstance.createStrategy(technique);
	return strategy;
	
  }

}
