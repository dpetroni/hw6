package edu.elon.math;


/*
 * Copyright October 10, 2016
 *
 * @author David Petroni & Zack Layne
 * @version 1.0
 * 
 * this class contains the main method to start the application
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javafx.scene.control.ComboBox;




/**
 * starts the application, gets the environmental variables, and creates the GUIs
 */
public class FunctionGuiApplication extends JFrame {

  /**
	 * 
	 */
	private static final long serialVersionUID = 3142558297899374758L;
private static String[] envResult;

  public static void main(String[] args){
 
	
    //String preResult = System.getenv("optimizers");
  	


    
    
    
    System.setProperty("java.security.policy", "client.policy");
    System.setSecurityManager(new SecurityManager());
	Dell DellObject;
  	SamsClub samsClubObject; 
  	MinimumAbsoluteSum masObject;
    String url = "rmi://localhost/";
    if (args.length == 1){
    	url="rmi://"+args[0]+"/";
    }
    try{
    	
    	
    	Context namingContext = new InitialContext();
    	
    	DellObject = (Dell) namingContext.lookup(url + "Dell");
    	samsClubObject = (SamsClub) namingContext.lookup(url + "SamsClub");
    	masObject = (MinimumAbsoluteSum) namingContext.lookup(url + "MinimumAbsoluteSum");
    	
        String preResult = "edu.elon.math.NelderMead,edu.elon.math.RandomWalk,edu.elon.math.Powell";
        //String preResult = (String) namingContext.lookup(url + "env");
        envResult = preResult.split(",");
    	
    	
        CreateGui samsClubGui = new CreateGui();
        CreateGui DellGui = new CreateGui();
        CreateGui MAS = new CreateGui();
    	
        samsClubGui.createGui(samsClubObject, envResult, 0,0);
        DellGui.createGui(DellObject, envResult,350,0);
        MAS.createGui(masObject, envResult,175,250);
    }
    catch(Exception e){
    	e.printStackTrace();
    }
//    CreateGui samsClubGui = new CreateGui();
//    CreateGui DellGui = new CreateGui();
//    CreateGui MAS = new CreateGui();
//    samsClubGui.createGui(new SamsClub(), envResult, 0,0);
//    DellGui.createGui(new Dell(), envResult,350,0);
//    MAS.createGui(new MinimumAbsoluteSum(), envResult,175,250);
//	
  }

}
