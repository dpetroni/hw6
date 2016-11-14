package edu.elon.math;

import javax.naming.Context;
import javax.naming.InitialContext;

public class FunctionServer {
	
	public static void main(String[] args){
		
	    System.setProperty("java.security.policy", "server.policy");
	    System.setSecurityManager(new SecurityManager());
		
		try{
			System.out.println("creating functions...");
			Dell dell = new Dell();
			SamsClub samsClub = new SamsClub();
			MinimumAbsoluteSum mas = new MinimumAbsoluteSum();
			String env = "edu.elon.math.NelderMead,edu.elon.math.RandomWalk,edu.elon.math.Powell";
			System.out.println("binding to registry...");
			Context namingContext = new InitialContext();
			namingContext.bind("rmi:env", env);
			namingContext.bind("rmi:Dell", dell);
			namingContext.bind("rmi:SamsClub", samsClub);
			namingContext.bind("rmi:MinimumAbsoluteSum", mas);
			System.out.println("waiting on invocations form clients...");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
