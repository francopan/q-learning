package maze.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.Array;

import javax.swing.JPanel;

public class MapFrame extends JPanel {

	private Integer xSize, ySize;
	private Color[][] mapColors;
	

	public MapFrame(Integer xSize, Integer ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.colorMap();
		setPreferredSize(new Dimension(10,10));
	}
	
	
	private void colorMap() {
		this.mapColors = new Color[xSize][ySize];
		for (Integer i = 0; i< xSize; i++) {
			for (Integer j = 0; j< ySize; j++) {
				mapColors[i][j] = Color.WHITE;
			}	
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / ySize;
        int rectHeight = getHeight() / xSize;

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                // Upper left corner of this terrain rect
                int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = mapColors[i][j];
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
	
}
