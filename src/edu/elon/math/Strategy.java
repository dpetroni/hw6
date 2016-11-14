package edu.elon.math;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Copyright October 10, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 * 
 * Interface used in the Strategy design pattern
 */
public interface Strategy extends Remote{
	
  /**
   * gets the optimal value from whatever function type the user selects
   * @param function
   * @return
   */
  public Double getOptimalValue(Function function);

}
