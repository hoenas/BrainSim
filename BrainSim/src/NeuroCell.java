import java.awt.Color;
import java.util.ArrayList;

public class NeuroCell {

	private ArrayList<NeuroCell> inputs;
	private float output;
	private float futureOutput;
	private boolean outputTriggered;
	private float threshold;
	private int cooldown;
	private int cooldownCounter;
	private int x;
	private int y;
	private int size;
	private Color color;
	private Color colorTriggered;

	public NeuroCell(float threshold, int cooldown, int x, int y, int size, Color color, Color colorTriggered) {
		super();
		inputs = new ArrayList<NeuroCell>();
		this.threshold = threshold;
		this.cooldown = cooldown;
		this.cooldownCounter = 0;
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
		this.colorTriggered = colorTriggered;
		output = 0.0f;
		futureOutput = 0.0f;
		outputTriggered = false;
	}

	public void updateOutput() {
		if (outputTriggered) {
			output = futureOutput;
			outputTriggered = false;
			cooldownCounter = 1;
			System.out.println(output);
		} else if( cooldownCounter != 0) {
			cooldownCounter++;
			if(cooldownCounter == cooldown) {
				cooldownCounter = 0;
			}
		} else {
			output = 0.0f;
		}
	}
	
	public void update() {
		float sum = 0;

		for (int i = 0; i < inputs.size(); i++) {
			sum += inputs.get(i).output;
		}

		if (sum >= threshold) {
			sum /= (float) inputs.size();
			futureOutput = sum;
			outputTriggered = true;
		}
	}

	public float getOutput() {
		return output;
	}

	public void setOutput(float output) {
		this.output = output;
	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	public float getFutureOutput() {
		return futureOutput;
	}

	public void setFutureOutput(float futureOutput) {
		this.futureOutput = futureOutput;
	}

	public ArrayList<NeuroCell> getInputs() {
		return inputs;
	}

	public void addInput(NeuroCell neuroCell) {
		inputs.add(neuroCell);
	}

	public boolean isOutputTriggered() {
		return outputTriggered;
	}

	public void setOutputTriggered(boolean outputTriggered) {
		this.outputTriggered = outputTriggered;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Color getColorTriggered() {
		return colorTriggered;
	}

	public void setColorTriggered(Color colorTriggered) {
		this.colorTriggered = colorTriggered;
	}
}
