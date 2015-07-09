import java.awt.Color;
import java.util.Random;


public class Stimulus extends NeuroCell {

	private Random ran;
	private float[] buffer;
	
	public Stimulus( int filterBufferSize, int x, int y, int size, Color color) {
		super(0.0f, 0, 0.0f, 0.0f, x, y, size, color, null);
		ran = new Random();
		buffer = new float[filterBufferSize];
		
		for(int i = 0; i < buffer.length; i++) {
			buffer[i] = 0.0f;
		}
		
		// TODO Auto-generated constructor stub
	}

	@Override public void update() {
		float temp = 0;
		for(int i = 0; i < buffer.length - 1; i++) {
			// neu sortieren
			buffer[i] = buffer[i+1];
			// dabei aufaddieren
			temp += buffer[i+1];
		}
		// Moving Average
		if(ran.nextBoolean()) {
			buffer[buffer.length - 1] = ran.nextFloat();
		} else {
			buffer[buffer.length - 1] = -1.0f * ran.nextFloat();
		}
		
		temp += buffer[buffer.length - 1];
		temp /= buffer.length;
		
		
		super.setOutput( temp );
	}
	
	@Override public void updateOutput() {
		
	}
	
}
