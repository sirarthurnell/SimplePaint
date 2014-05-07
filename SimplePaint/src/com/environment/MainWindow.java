package com.environment;

import javax.swing.*;

import com.thirdparty.WrapLayout;

import java.awt.Dimension;
import java.awt.event.*;
import java.io.IOException;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final int width = 320;
	private final int height = 240;
	private JPanel drawingPanel;
	private DrawingSurface drawingSurface;
	private DrawableCanvas canvasControl;
	private Command lastCommand;
	
	public MainWindow(){
		createAndShowGui();
	}
	
	/**
	 * Crea y muestra la interfaz de usuario.
	 */
	public void createAndShowGui(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		setTitle("SimplePaint");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		//Main container layout.
		BoxLayout mainLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		getContentPane().setLayout(mainLayout);
		
		drawingPanel = new JPanel();
		drawingPanel.setLayout(null);
		drawingPanel.setPreferredSize(new Dimension(width, height));
		
		TextBox editor = new TextBox();		
		drawingPanel.add(editor);
		editor.setVisible(false);
		
		canvasControl = new DrawableCanvas(editor);
		drawingPanel.add(canvasControl);
		canvasControl.setBounds(0, 0, width, height);
		
		getContentPane().add(drawingPanel);

		drawingSurface = canvasControl.getDrawingSurface();
		
		//Buttons layout.
		WrapLayout buttonsLayout = new WrapLayout();
		JPanel buttonsPanel = new JPanel(buttonsLayout);
		buttonsPanel.setSize(new Dimension(width, 1));
				
		//Freehand.
		JButton freehandButton = new JButton("Freehand");
		freehandButton.setMnemonic('f');
		freehandButton.addActionListener(new ActionListener(){
			
			private FreehandCommand freehand = new FreehandCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(freehand);
				freehand.enable();				
			}
			
		});
		buttonsPanel.add(freehandButton);
		
		//Text.
		JButton textButton = new JButton("Text");
		textButton.setMnemonic('t');
		textButton.addActionListener(new ActionListener(){
			
			private TextCommand text = new TextCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(text);
				text.enable();				
			}
			
		});
		buttonsPanel.add(textButton);
		
		//Polyline.
		JButton polylineButton = new JButton("Polyline");
		polylineButton.setMnemonic('p');
		polylineButton.addActionListener(new ActionListener(){
			
			private PolylineCommand polyline = new PolylineCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(polyline);
				polyline.enable();				
			}
			
		});
		buttonsPanel.add(polylineButton);
		
		//Aerograph.
		JButton aerographButton = new JButton("Aerograph");
		aerographButton.setMnemonic('a');
		aerographButton.addActionListener(new ActionListener(){
			
			private AerographCommand aerograph = new AerographCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(aerograph);
				aerograph.enable();				
			}
			
		});
		buttonsPanel.add(aerographButton);
		
		//Selection.
		JButton selectionButton = new JButton("Selection");
		selectionButton.setMnemonic('f');
		selectionButton.addActionListener(new ActionListener(){
			
			private SelectionCommand selection = new SelectionCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(selection);
				selection.enable();
			}
			
		});
		buttonsPanel.add(selectionButton);
		
		//FloodFill.
		JButton floodFillButton = new JButton("Flood fill");
		floodFillButton.setMnemonic('l');
		floodFillButton.addActionListener(new ActionListener(){
			
			private FloodFillCommand floodFill = new FloodFillCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(floodFill);
				floodFill.enable();				
			}
			
		});
		buttonsPanel.add(floodFillButton);
		
		//Rectangle.
		JButton rectangleButton = new JButton("Rectangle");
		rectangleButton.setMnemonic('r');
		rectangleButton.addActionListener(new ActionListener(){
			
			private RectangleCommand rectangle = new RectangleCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(rectangle);
				rectangle.enable();				
			}
			
		});
		buttonsPanel.add(rectangleButton);
		
		//Ellipse.
		JButton ellipseButton = new JButton("Ellipse");
		ellipseButton.setMnemonic('e');
		ellipseButton.addActionListener(new ActionListener(){
			
			private EllipseCommand ellipse = new EllipseCommand(drawingSurface);
			
			public void actionPerformed(ActionEvent e){
				replaceLastCommand(ellipse);
				ellipse.enable();				
			}
			
		});
		buttonsPanel.add(ellipseButton);
		
		//Clear.
		JButton clearButton = new JButton("Clean");
		clearButton.setMnemonic('c');
		clearButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				drawingSurface.erase();
			}
			
		});
		buttonsPanel.add(clearButton);
		
		//Save.
		JButton saveButton = new JButton("Save");
		saveButton.setMnemonic('s');
		saveButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				try {
					drawingSurface.save("c:\\saved.png");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		buttonsPanel.add(saveButton);
		
		getContentPane().add(buttonsPanel);
		
		pack();		
		drawingSurface.erase();
	}
	
	private void replaceLastCommand(Command actualCommand){
		if(lastCommand != null){
			lastCommand.disable();			
		}
		
		lastCommand = actualCommand;
	}

}
