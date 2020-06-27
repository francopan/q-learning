package maze;

public class QCell {

	private Double belowReward, upReward, leftReward, rightReward;
	
	public QCell() {
		this.belowReward = 0.0;
		this.upReward = 0.0;
		this.leftReward = 0.0;
		this.rightReward = 0.0;
	}

	public Double getBelowReward() {
		return belowReward;
	}

	public void setBelowReward(Double belowReward) {
		this.belowReward = belowReward;
	}

	public Double getUpReward() {
		return upReward;
	}

	public void setUpReward(Double upReward) {
		this.upReward = upReward;
	}

	public Double getLeftReward() {
		return leftReward;
	}

	public void setLeftReward(Double leftReward) {
		this.leftReward = leftReward;
	}

	public Double getRightReward() {
		return rightReward;
	}

	public void setRightReward(Double rightReward) {
		this.rightReward = rightReward;
	}
	
}
