package com.environment;

import javax.swing.*;
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

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		drawingPanel = new JPanel();
		drawingPanel.setLayout(null);		
		
		TextBox editor = new TextBox();		
		drawingPanel.add(editor);
		editor.setVisible(false);		
		
		canvasControl = new DrawableCanvas(editor);						
		drawingPanel.add(canvasControl);
		canvasControl.setBounds(0, 0, width, height);
		
		getContentPane().add(drawingPanel);

		drawingSurface = canvasControl.getDrawingSurface();
		
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
		getContentPane().add(freehandButton);
		
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
		getContentPane().add(textButton);
		
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
		getContentPane().add(polylineButton);
		
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
		getContentPane().add(aerographButton);
		
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
		getContentPane().add(selectionButton);
		
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
		getContentPane().add(floodFillButton);
		
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
		getContentPane().add(rectangleButton);
		
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
		getContentPane().add(ellipseButton);
		
		//Clear.
		JButton clearButton = new JButton("Clean");
		clearButton.setMnemonic('c');
		clearButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				drawingSurface.erase();
			}
			
		});
		getContentPane().add(clearButton);
		
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
		getContentPane().add(saveButton);
				
		pack();
		setBounds(0,0, 320, 525);
		drawingSurface.erase();
	}
	
	private void replaceLastCommand(Command actualCommand){
		if(lastCommand != null){
			lastCommand.disable();			
		}
		
		lastCommand = actualCommand;
	}

}
