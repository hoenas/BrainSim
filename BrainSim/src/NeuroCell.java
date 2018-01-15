import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class NeuroCell {

	private ArrayList<NeuroCell> inputs;
	private float output;
	private float futureOutput;
	private boolean outputTriggered;
	private float threshold;
	private int cooldown;
	private int cooldownCounter;
	private float eruptionPropability;
	private float eruptionOutput;
	private Random ran;
	private int x;
	private int y;
	private int size;
	private Color color;
	private Color colorTriggered;
	private boolean monocrome;

	public NeuroCell(float threshold, int cooldown, float eruptionPropability,
			float eruptionOutput, int x, int y, int size, Color color,
			Color colorTriggered, boolean monocrome) {
		super();
		inputs = new ArrayList<NeuroCell>();
		this.threshold = threshold;
		this.cooldown = cooldown;
		this.cooldownCounter = 0;
		this.eruptionPropability = eruptionPropability;
		this.eruptionOutput = eruptionOutput;
		this.ran = new Random();
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
		this.colorTriggered = colorTriggered;
		this.monocrome = monocrome;
		output = 0.0f;
		futureOutput = 0.0f;
		outputTriggered = false;
	}

	public void updateOutput() {
		
		if (outputTriggered) {
			output = futureOutput;
			outputTriggered = false;
			
			cooldownCounter = 1;
		} else if (cooldownCounter != 0) {
			if (cooldownCounter == cooldown) {
				cooldownCounter = 0;
			} else {
				cooldownCounter++;
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
		} else if (ran.nextFloat() <= eruptionPropability) {
			futureOutput = eruptionOutput + sum;
			outputTriggered = true;
			sum = futureOutput;
		}
		
		if(!monocrome) {
			if(sum > 1.0) {
				sum = 1.0f;
			} else if (sum < 0.0f) {
				sum = 0.0f;
			}
			Color cellColor = new Color(0, sum, 0, 1.0f);
			this.color = cellColor;
			this.colorTriggered = cellColor;
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

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public float getEruptionPropability() {
		return eruptionPropability;
	}

	public void setEruptionPropability(float eruptionPropability) {
		this.eruptionPropability = eruptionPropability;
	}

	public float getEruptionOutput() {
		return eruptionOutput;
	}

	public void setEruptionOutput(float eruptionOutput) {
		this.eruptionOutput = eruptionOutput;
	}
}
