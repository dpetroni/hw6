package edu.elon.math;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverObject extends Remote {
	
	public void update(FunctionInterface o) throws RemoteException;

}
