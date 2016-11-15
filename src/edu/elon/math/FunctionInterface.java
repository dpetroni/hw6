package edu.elon.math;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FunctionInterface extends Remote {
	
	public void setStrategy(Strategy aStrategy) throws RemoteException;
	
	public void valuesChanged() throws RemoteException;
	
	public void setChanged() throws RemoteException;
	
	public void notifyObservers() throws RemoteException;
	
	public abstract Double evaluate() throws RemoteException;
	
	public ArrayList<String> getInputNames() throws RemoteException;
	
	public ArrayList<Double> getInputValues() throws RemoteException;
	
	public String getOptimizationTechnique() throws RemoteException;
	
	public Double getOutput() throws RemoteException;
	
	public String getTitle() throws RemoteException;
	
	public boolean isMinimize() throws RemoteException;
	
	public Double optimize() throws RemoteException;
	
	public void setInputNames(ArrayList<String> inputNames) throws RemoteException;
	
	public void setInputValues(ArrayList<Double> inputValues) throws RemoteException;
	
	public void setMinimize(boolean minimize) throws RemoteException;
	
	public void setOptimizationTechnique(String optimizationTechnique) throws RemoteException;
	
	public void setOutput(Double output) throws RemoteException;
	
	public void setTitle(String title) throws RemoteException;
	
	public void addObserver(ObserverObject o) throws RemoteException;
	
	public void removeObserver(ObserverObject o) throws RemoteException;
	
	public String getEnv() throws RemoteException;

}
