package edu.elon.math;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * Copyright October 10, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 * 
 * This class is used to dynamically create a gui for a given function, and also updates when needed
 */

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Creates a gui dynamically 
 */

public class CreateGui extends UnicastRemoteObject implements ObserverObject {
	
	protected CreateGui() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	Store store = new Store();
	
	private ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	private ArrayList<Double> newValues;
	private JComboBox box;
	private JTextField result;
//	public String[] envResult;
	
	
//	private ObserverObject observable;
//	
//	public CreateGui(Observable observable){
//		this.observable = observable;
//		observable.addObserver(this);
//	}
//	
	private FunctionInterface function;
	
	/**
	 * Method used to dynamically create the gui specific for each of the three functions
	 * @param function
	 * @param envResult
	 * @param x
	 * @param y
	 * @throws RemoteException 
	 */
	
	
	
	public void createGui(FunctionInterface function, int x, int y) throws RemoteException{
		
		
		
		
		
		this.function = (FunctionInterface) function;
		function.addObserver(this);
		
		
		String preResult = "edu.elon.math.NelderMead,edu.elon.math.RandomWalk,edu.elon.math.Powell";
		//String preResult = function.getEnv();
		String[] envResult = preResult.split(",");
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));

		ArrayList<String> inputNames = function.getInputNames();
		ArrayList<Double> inputValues = function.getInputValues();
		
		JFrame frame = new JFrame(function.getTitle());
		JLabel boxLabel = new JLabel("technique");
		box = new JComboBox<String>(envResult);
		JPanel boxPanel = new JPanel();
		boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.LINE_AXIS));
		boxPanel.add(boxLabel);
		boxPanel.add(box);

		JLabel resultLabel = new JLabel("Result");
		result = new JTextField();
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.LINE_AXIS));
		resultPanel.add(resultLabel);
		resultPanel.add(result);
		
		JButton solve = new JButton("Solve");
		JButton optimize = new JButton("Optimize");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(solve);
		buttonPanel.add(optimize);
		
		main.add(boxPanel);
		JPanel panel = new JPanel();
		
		
		for(int i = 0; i < inputNames.size(); i++){
			JLabel label = new JLabel(inputNames.get(i));
			JTextField field = new JTextField(""+inputValues.get(i));
			textFields.add(field);
			JPanel innerPanel = new JPanel();
			innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.LINE_AXIS));
			innerPanel.add(label);
			innerPanel.add(field);
			panel.add(innerPanel);
			panel.setLayout(new BoxLayout (panel, BoxLayout.Y_AXIS));
			
		}
		JScrollPane scrollPane = new JScrollPane(panel);
		main.add(scrollPane);
		main.add(resultPanel);
		main.add(buttonPanel);
		
		frame.add(main);
		
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(x, y);
		frame.setVisible(true);
		
		optimize.addActionListener(event -> {
			
			Thread t1 = new Thread(new Runnable(){
				public void run(){
					newValues = new ArrayList<Double>();
					for (int i = 0; i<textFields.size(); i++){
						newValues.add(Double.parseDouble(textFields.get(i).getText()));
					}
					try {
						function.setInputValues(newValues);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String a = (String) box.getSelectedItem();
						Strategy strategy = store.instantiateStrategy(a);
//						try {
//							function.setStrategy(strategy);
//						} catch (RemoteException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						try {
//							function.optimize();
//						} catch (RemoteException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						try {
							result.setText(""+strategy.getOptimalValue(function));
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
				}
			});
			t1.start();
		});
		
		
		solve.addActionListener(event -> {
			
			Thread t2 = new Thread(new Runnable(){
				public void run(){
					newValues = new ArrayList<Double>();

					for (int i = 0; i<textFields.size(); i++){
						newValues.add(Double.parseDouble(textFields.get(i).getText()));
					}
					try {
						function.setInputValues(newValues);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					try {
//						function.valuesChanged();
//					} catch (RemoteException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					try {
						result.setText(""+function.evaluate());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
					newValues.clear();
				}
				});
			t2.start();
		});
	}
	
	
/*
 * 
 * updates the gui with new observed data
 */
	public void update(FunctionInterface o) throws RemoteException {
		
		if(o instanceof FunctionInterface){

			ArrayList<Double> temp = o.getInputValues();
			
			for (int i = 0; i < textFields.size(); i++){
				textFields.get(i).setText(temp.get(i).toString());
			}
			result.setText(""+o.getOutput());
			
		}
	}
}
