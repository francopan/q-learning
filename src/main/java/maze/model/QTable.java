package maze.model;

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
			return this.qTable[source].getRightReward();
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

	public void setReward(Integer source, Integer target, MovePosition move, Double targetReward,
			MovePosition targetBestMove) {
		Double currentQ = this.getReward(source, move);
		Double maxFutureQ = this.getReward(target, targetBestMove);
		Double learningRate = 0.5;
		Double discountFactor = 0.1;
		currentQ = currentQ + learningRate * (targetReward + discountFactor * maxFutureQ - currentQ);
		

		switch (move) {
		case UP: {
			this.qTable[source].setUpReward(currentQ);
			break;
		}
		case DOWN: {
			this.qTable[source].setBelowReward(currentQ);
			break;
		}
		case LEFT: {
			this.qTable[source].setLeftReward(currentQ);
			break;
		}
		case RIGHT: {
			this.qTable[source].setRightReward(currentQ);
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
