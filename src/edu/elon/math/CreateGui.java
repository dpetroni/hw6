package edu.elon.math;

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

public class CreateGui implements Observer {
	
	Store store = new Store();
	
	private ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	private ArrayList<Double> newValues;
	private JComboBox box;
	private JTextField result;
	
	private Observable observable;
	
//	public CreateGui(Observable observable){
//		this.observable = observable;
//		observable.addObserver(this);
//	}
//	
	private Function function;
	
	/**
	 * Method used to dynamically create the gui specific for each of the three functions
	 * @param function
	 * @param envResult
	 * @param x
	 * @param y
	 */
	public void createGui(Function function, String[] envResult, int x, int y){
		
		this.observable = function;
		observable.addObserver(this);
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		
		this.function = function;

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
					function.setInputValues(newValues);
					String a = (String) box.getSelectedItem();
						Strategy strategy = store.instantiateStrategy(a);
						function.setStrategy(strategy);
						function.optimize();
//						result.setText(""+strategy.getOptimalValue(function));	
						
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
					function.setInputValues(newValues);
//					function.valuesChanged();
//				  result.setText(""+function.evaluate());
					function.evaluate();
				    
					//newValues.clear();
				}
				});
			t2.start();
		});
	}
	
	
/*
 * 
 * updates the gui with new observed data
 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof Function){
			
			Function function = (Function)o;
			ArrayList<Double> temp = function.getInputValues();
			
			for (int i = 0; i < textFields.size(); i++){
				textFields.get(i).setText(temp.get(i).toString());
			}
			result.setText(""+function.getOutput());
			
		}
	}
}
