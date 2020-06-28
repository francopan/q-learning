package maze;

import java.util.ArrayList;
import java.util.Random;

public class Main {

	private static Integer[][] map = { { 500, 1, 1, 1, -100, 1, 1, 1, 1, 1, 1, -100 },
			{ 1, -100, 1, 1, 1, 1, 1, 1, 1, 1, 1, -100 }, { -100, 1, -100, 1, 1, 1, -100, 1, -100, -100, 1, 1 },
			{ 1, -100, 1, 1, 1, 1, 1, 1, -100, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100 },
			{ 0, 0, 0, 0, 1, 1, -100, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, -100, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0 }, };

	public static void main(String[] args) {
		// Create Maze and Q-Table
		Maze maze = new Maze(map);
		QTable qTable = new QTable(120);

		// Set start and target Positions
		Integer startState = 112;
		Integer targetState = 59;

		// Make Episodes
		Random randomGenerator = new Random();
		for (Integer episodes = 1000; episodes > 0; episodes--) {
			Integer currentState = startState;
			//qTable.printTable();
			System.out.println(episodes);
			while (currentState != targetState) {
				
				// Step 1 (Choose a Movement)
				MovePosition movePosition = qTable.getBestReward(currentState, new ArrayList<MovePosition>());
				Integer nextState = null;
				do {
					if (randomGenerator.nextDouble() >= 0.7) {
						MovePosition sorted = MovePosition.values()[randomGenerator.nextInt(4)];
						while (sorted == movePosition) {
							sorted = MovePosition.values()[randomGenerator.nextInt(4)];
						}
						movePosition = sorted;
					}
					nextState = maze.move(currentState, movePosition);
				} while (nextState == -1);

				// Step 2 (Execute the Movement)
				Integer[] currentCoordinates =  maze.getCoordinates(currentState);
				Integer[] targetCoordinates =  maze.getCoordinates(nextState);
				Double currentReward = map[currentCoordinates[0]][currentCoordinates[1]] * 1.0;
				Double targetReward = map[targetCoordinates[0]][targetCoordinates[1]] * 1.0;
				
				// Future Step based on current movement
				MovePosition targetBestPosition = null;
				ArrayList<MovePosition> invalidMovements = new ArrayList<MovePosition>();
				Integer[] futureCoordinates = null;
				do {
					if (targetBestPosition != null) {
						invalidMovements.add(targetBestPosition);
					}
					targetBestPosition = qTable.getBestReward(nextState, invalidMovements);
					futureCoordinates = maze.getCoordinates(maze.move(nextState, targetBestPosition));
				} while (!maze.validateMovement(futureCoordinates[0], futureCoordinates[1]));
				
				// Sets Q-Table reward and move
				qTable.setReward(currentState, nextState,movePosition, currentReward,targetReward, targetBestPosition);
				currentState = nextState;
			}
						
		//	System.out.println(currentState);
		//	System.out.println("---------------");
		}
		
		// Print out best path
		Integer currentState = startState;
		Integer nextState;
		//qTable.printTable();
		while (currentState != targetState) {
			System.out.println(currentState);
			MovePosition bestPosition = null;
			ArrayList<MovePosition> invalidMovements = new ArrayList<MovePosition>();
			Integer[] coordinates = null;
			do {
				if (bestPosition != null) {
					invalidMovements.add(bestPosition);
				}
				bestPosition = qTable.getBestReward(currentState, invalidMovements);
				coordinates = maze.getCoordinates(maze.move(currentState, bestPosition));
				nextState = maze.move(currentState, bestPosition);
			} while (nextState == -1 || maze.validateMovement(coordinates[0], coordinates[1]) == false);
			
			currentState = nextState;
		}
		System.out.println(currentState);
		
		
	}

}
