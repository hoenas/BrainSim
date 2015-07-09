

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

public class Brain extends Canvas{
	private java.util.List<NeuroCell> neurocells = new ArrayList<NeuroCell>();
	private Color backgroundColor;
	private Color linkColor;
	private double linkRadius;
	private boolean drawLinkLines;
	private boolean drawRectangles;
	private BufferStrategy bufferstrategy;
	
	//public Brain( Color backgroundColor, Color linkColor, int numberOfNeurocells, int numberOfStimuli, double linkRadius, boolean drawLinkLines ) {
	public Brain( Color backgroundColor, Color linkColor, double linkRadius, boolean drawLinkLines ) {
		this.backgroundColor = backgroundColor;
		this.linkColor = linkColor;
		this.linkRadius = linkRadius;
		this.drawLinkLines = drawLinkLines;
		this.drawRectangles = false;
		bufferstrategy = null;
		
		Random ran = new Random();
		
		//for(int i = 0; i < numberOfStimuli; i++) {
		//	neurocells.add(new Stimulus(5, 0, 0, 10, Color.blue));
		//}
	}
	
	public void update() {                               		
		for(int i = 0; i < neurocells.size(); i++) {
			neurocells.get(i).update();
		}
		
		
		if( this.isVisible() && bufferstrategy == null ) {
			this.createBufferStrategy(2);
			bufferstrategy = this.getBufferStrategy();
			
			/*
			Random ran = new Random();
			// Randomise positions
			for(int i = 0; i < neurocells.size(); i++) {
				neurocells.get(i).setX((int)(ran.nextFloat() * this.getWidth()));
				neurocells.get(i).setY((int)(ran.nextFloat() * this.getWidth()));
			}
			
			// create links
			
			// Link Neurocells
			for(int i = 0; i < neurocells.size(); i++) {
				NeuroCell neurocell = neurocells.get(i);
				for(int k = 0; k < neurocells.size(); k++)
				{
					if(i != k && !neurocell.getInputs().contains(neurocells.get(k)) ) {
						double distance = Math.sqrt( Math.pow( neurocell.getX() - neurocells.get(k).getX(), 2 ) + Math.pow( neurocell.getY() - neurocells.get(k).getY(), 2 ) );
						if(distance <= linkRadius) {
							neurocell.addInput(neurocells.get(k));
						}
					}
				}
			}
			*/			
		} else {
		
			Graphics2D g2 = (Graphics2D) bufferstrategy.getDrawGraphics();

			// Zeichnung
			g2.setColor( backgroundColor );
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g2.setColor(Color.black);
			if(drawLinkLines) {
				for(int i = 0; i < neurocells.size(); i++) {
					NeuroCell neurocell = neurocells.get(i);
					for(int k = 0; k < neurocell.getInputs().size(); k++) {
						g2.drawLine(neurocell.getX() + neurocell.getSize() / 2, neurocell.getY() + neurocell.getSize() / 2, neurocell.getInputs().get(k).getX() + neurocell.getSize() / 2, neurocell.getInputs().get(k).getY() + neurocell.getSize() / 2);
					}
				}
			}
			
			for(int i = 0; i < neurocells.size(); i++) {
				NeuroCell neurocell = neurocells.get(i);
				if(neurocell.isOutputTriggered()) {
					g2.setColor( neurocell.getColorTriggered() );
				} else {
					g2.setColor( neurocell.getColor() );
				}
				
				if( !drawRectangles ) {
					g2.fillArc( neurocell.getX(), neurocell.getY(), neurocell.getSize(), neurocell.getSize(), 0, 360 );
				} else {
					g2.fillRect( neurocell.getX(), neurocell.getY(), neurocell.getSize(), neurocell.getSize());
				}
				
			}
			
			bufferstrategy.show();
			Toolkit.getDefaultToolkit().sync();
					
			for(int i = 0; i < neurocells.size(); i++) {
				neurocells.get(i).updateOutput();
			}		
		}
	}
	
	public void addNeurocell( NeuroCell neurocell ) {
		// add neurocell
		neurocells.add(neurocell);
		
		// calculate links
		for(int i = 0; i < neurocells.size()-1; i++) {
			double distance = Math.sqrt( Math.pow( neurocell.getX() - neurocells.get(i).getX(), 2 ) + Math.pow( neurocell.getY() - neurocells.get(i).getY(), 2 ) );
			if(distance <= linkRadius) {
				neurocell.addInput(neurocells.get(i));
				neurocells.get(i).addInput(neurocell);
			}
		}
	}

	public boolean isDrawLinkLines() {
		return drawLinkLines;
	}

	public void setDrawLinkLines(boolean drawLinkLines) {
		this.drawLinkLines = drawLinkLines;
	}

	public java.util.List<NeuroCell> getNeurocells() {
		return neurocells;
	}

	public double getLinkRadius() {
		return linkRadius;
	}

	public void setLinkRadius(double linkRadius) {
		this.linkRadius = linkRadius;
	}

	public boolean isDrawRects() {
		return drawRectangles;
	}

	public void setDrawRects(boolean drawRectangles) {
		this.drawRectangles = drawRectangles;
	}
}

