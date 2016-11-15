package edu.elon.math;

import javax.naming.Context;
import javax.naming.InitialContext;

public class FunctionServer {
	
	public static void main(String[] args){
		

		
		try{
		    System.setProperty("java.security.policy", "server.policy");
		    System.setSecurityManager(new SecurityManager());
			System.out.println("creating functions...");
			Dell dell = new Dell();
			SamsClub samsClub = new SamsClub();
			MinimumAbsoluteSum mas = new MinimumAbsoluteSum();
			String env = System.getenv("optimizers");
			System.out.println("binding to registry...");
			Context namingContext = new InitialContext();
			//namingContext.bind("rmi:env", env);
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
