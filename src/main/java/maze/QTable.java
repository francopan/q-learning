package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QTable {

	QCell[] qTable;
	Random randomGenerator;

	public QTable(Integer maxPositions) {
		this.qTable = new QCell[maxPositions];
		for (int i = 0; i < maxPositions; i++) {
			qTable[i] = new QCell();
		}
		this.randomGenerator = new Random();
	}

	public Double getReward(Integer source, MovePosition movement) {
		switch (movement) {
		case UP:
			return this.qTable[source].getUpReward();
		case DOWN:
			return this.qTable[source].getBelowReward();
		case LEFT:
			return this.qTable[source].getLeftReward();
		case RIGHT:
			return this.qTable[source].getUpReward();
		}
		return null;
	}

	public MovePosition getBestReward(Integer source, List<MovePosition> blackList) {
		MovePosition bestPosition = MovePosition.values()[this.randomGenerator.nextInt(4)];
		Double bestReward = this.getReward(source, bestPosition);

		if (this.qTable[source].getUpReward() > bestReward && !blackList.contains(MovePosition.UP)) {
			bestPosition = MovePosition.UP;
			bestReward = this.qTable[source].getUpReward();
		}
		if (this.qTable[source].getBelowReward() > bestReward && !blackList.contains(MovePosition.DOWN)) {
			bestPosition = MovePosition.DOWN;
			bestReward = this.qTable[source].getBelowReward();
		}
		if (this.qTable[source].getLeftReward() > bestReward && !blackList.contains(MovePosition.LEFT)) {
			bestPosition = MovePosition.LEFT;
			bestReward = this.qTable[source].getLeftReward();
		}
		if (this.qTable[source].getRightReward() > bestReward && !blackList.contains(MovePosition.RIGHT)) {
			bestPosition = MovePosition.RIGHT;
		}
		return bestPosition;
	}

	public void setReward(Integer source, Integer target, MovePosition move, Double currentReward, Double targetReward,
			MovePosition targetBestMove) {
		Double current_q = this.getReward(source, move);
		Double learningRate = 0.1;
		Double discountFactor = 0.3;
		
		switch (targetBestMove) {
		case UP: {
			current_q = (1 - learningRate) * current_q + learningRate * (targetReward + discountFactor * qTable[target].getUpReward());
			//current_q = current_q + learningRate * (targetReward + discountFactor * qTable[target].getUpReward() - current_q);
			break;
		}
		case DOWN: {
			current_q = (1 - learningRate) * current_q + learningRate * (targetReward + discountFactor * qTable[target].getBelowReward());
			//current_q = current_q + learningRate * (targetReward + discountFactor * qTable[target].getBelowReward() - current_q);
			break;
		}
		case LEFT: {
			current_q = (1 - learningRate) * current_q + learningRate * (targetReward + discountFactor * qTable[target].getLeftReward());
			//current_q = current_q + learningRate * (targetReward + discountFactor * qTable[target].getLeftReward() - current_q);
			break;
		}
		case RIGHT: {
			current_q = (1 - learningRate) * current_q + learningRate * (targetReward + discountFactor * qTable[target].getRightReward());
			//current_q = current_q + learningRate * (targetReward + discountFactor * qTable[target].getRightReward() - current_q);
			break;
		}
		}

		switch (move) {
		case UP: {
			this.qTable[source].setUpReward(current_q);
			break;
		}
		case DOWN: {
			this.qTable[source].setBelowReward(current_q);
			break;
		}
		case LEFT: {
			this.qTable[source].setLeftReward(current_q);
			break;
		}
		case RIGHT: {
			this.qTable[source].setRightReward(current_q);
			break;
		}
		}

	}

	public void printTable() {
		for (int i = 0; i < this.qTable.length; i++) {
			System.out.println(
					"POSITION " + i + " , TOP: " + qTable[i].getUpReward() + " , DOWN: " + qTable[i].getBelowReward()
							+ " , LEFT: " + qTable[i].getLeftReward() + " , RIGHT: " + qTable[i].getRightReward());
		}
	}
	
	

}
