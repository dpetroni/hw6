package edu.elon.math;
/*
 * Copyright October 10, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 */
public class SimpleFactory {
	
	private static SimpleFactory uniqueInstance;
	
	private SimpleFactory(){
		
	}
	
	
	/**
	 * creates a unique instance of a simpleFactory
	 * @return
	 */
	public static SimpleFactory getInstance(){
		if (uniqueInstance == null){
			synchronized (SimpleFactory.class){
				if (uniqueInstance == null){
					uniqueInstance = new SimpleFactory();
				}
			}
		}
		return uniqueInstance;
		
	}
	
	/**
	 * instantiates the optimizer strategy
	 * @return
	 * @param
	 */
	
	public Strategy createStrategy(String technique){
		
		Strategy strategy = null;
		try {
			strategy = (Strategy) Class.forName(technique).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strategy;
		
	}

}
