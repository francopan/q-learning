package maze.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MapFrame extends JPanel {

	private Integer xSize, ySize;
	private Color[][] mapColors;
	private Integer[][] map;
	private Integer[] currentPosition;

	public MapFrame(Integer[][] map, Integer[] currentPosition) {
		this.xSize = map.length;
		this.ySize = map[0].length;
		this.map = map;
		this.currentPosition = currentPosition;
		this.colorMap();
		//setPreferredSize(new Dimension(10, 10));
	}

	private void colorMap() {
		this.mapColors = new Color[xSize][ySize];
		for (Integer i = 0; i < xSize; i++) {
			for (Integer j = 0; j < ySize; j++) {
				mapColors[i][j] = Color.BLACK;
			}
		}
	}
	
	public void setMap(Integer[][] map) {
		this.map = map;
	}
	
	public void setCurrentPosition(Integer[] currentPosition) {
		this.currentPosition = currentPosition;
	}
	

	@Override
	public void paintComponent(Graphics g) {
		// Important to call super class method
		super.paintComponent(g);
		// Clear the board
		g.clearRect(0, 0, getWidth(), getHeight());
		// Draw the grid
		int rectWidth = getHeight() / xSize;
		int rectHeight = getWidth() / ySize;

		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				// Upper left corner of this terrain rect
				int y = i * rectWidth;
				int x = j * rectHeight;
				Color terrainColor;
				switch (this.map[i][j]) {
					case -1: {
						terrainColor = Color.red;
						break;
					}
					case 1: {
						terrainColor = Color.GRAY;
						break;
					}
					case 100: {
						terrainColor = Color.GREEN;
						break;
					}
					default: {
						terrainColor = Color.BLACK;
					}
				}
				g.setColor(terrainColor);
				g.fillRect(x, y, rectHeight, rectWidth);
			}
		}
		
		
		g.setColor(Color.BLUE);
		g.fillRect(this.currentPosition[1] * rectHeight, this.currentPosition[0] * rectWidth, rectHeight, rectWidth);
		
		
	}

}
