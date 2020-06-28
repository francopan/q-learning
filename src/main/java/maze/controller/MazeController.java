package maze.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import maze.model.Maze;
import maze.model.MovePosition;
import maze.model.QTable;

public class MazeController {
	
	private Maze maze;
	private QTable qTable;
	private Random randomGenerator;
	
	public MazeController(Maze maze, QTable qTable) {
		this.maze = maze;
		this.qTable = qTable;
		this.randomGenerator = new Random();
	}
	
	public void explore(Integer nmEpisodes, Integer startState, Integer targetState) {
		for (Integer episodes = nmEpisodes; episodes > 0; episodes--) {
			Integer currentState = startState;
			while (!currentState.equals(targetState)) {
				
				// Step 1 (Choose a Movement)
				MovePosition movePosition = qTable.getBestReward(currentState, new ArrayList<MovePosition>());
				Integer nextState = null;
				do {
					if (this.randomGenerator.nextDouble() >= 0.7) {
						MovePosition sorted = MovePosition.values()[this.randomGenerator.nextInt(4)];
						while (sorted == movePosition) {
							sorted = MovePosition.values()[this.randomGenerator.nextInt(4)];
						}
						movePosition = sorted;
					}
					nextState = maze.move(currentState, movePosition);
				} while (nextState == -1);

				// Step 2 (Execute the Movement)
				Integer[] targetCoordinates =  maze.getCoordinates(nextState);
				Double targetReward = maze.getMap()[targetCoordinates[0]][targetCoordinates[1]] * 1.0;
				
				// Calculate qTable information
				MovePosition targetBestPosition = null;
				ArrayList<MovePosition> invalidMovements = new ArrayList<MovePosition>();
				Integer[] futureCoordinates = null;
				do {
					if (targetBestPosition != null) {
						invalidMovements.add(targetBestPosition);
					}
					targetBestPosition = qTable.getBestReward(nextState, invalidMovements);
					futureCoordinates = maze.getCoordinates(maze.move(nextState, targetBestPosition));
				} while (maze.validateMovement(futureCoordinates[0], futureCoordinates[1]).equals(Boolean.FALSE));
				
				// Sets Q-Table reward and move
				qTable.setReward(currentState, nextState,movePosition,targetReward, targetBestPosition);
				currentState = nextState;
			}				
		}
	}
	
	public List<Integer> getPath(Integer currentState, Integer targetState) {
		Integer nextState;
		ArrayList<Integer> path = new ArrayList<Integer>();
		while (!currentState.equals(targetState)) {
			path.add(currentState);
			MovePosition bestPosition = null;
			ArrayList<MovePosition> invalidMovements = new ArrayList<MovePosition>();
			do {
				if (bestPosition != null) {
					invalidMovements.add(bestPosition);
				}
				bestPosition = qTable.getBestReward(currentState, invalidMovements);
				nextState = maze.move(currentState, bestPosition);
			} while (nextState == -1);
			currentState = nextState;
		}
		path.add(currentState);
		return path;
	}

}
