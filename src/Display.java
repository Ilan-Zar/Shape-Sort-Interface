import java.awt.FlowLayout;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel; 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Display extends JPanel{
	private boolean toPaint = false;
	private Color[] clrs = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow};
	private Shapes[] shapes = new Shapes[6];
	private int[] locations = {15, 103, 191, 279, 367, 455};
	
	public void sortShapes(boolean buttonPressed) {
		toPaint = buttonPressed;
		bubbleSort bsort = new bubbleSort();
		boolean isEmpty = true;
		for(Shapes sh : shapes) {
			if(sh != null) {
				isEmpty = false;
				break;
			}
		}
		if(!isEmpty) {
			bsort.sort(shapes);
			for(int i = 0; i < shapes.length; i++) {
				shapes[i].x = locations[i];
				shapes[i].y = locations[i];
			}
			repaint();
		}
	}
	public void loadShapes(boolean buttonPressed) {
		toPaint = buttonPressed;
		for(int i = 0; i < shapes.length; i++) {
			int clr = (int)(Math.random()* 9);
			int shapeType = (int)(Math.random()* 3);
			int shapeWidth;
			int shapeLength;
			Shapes shp;
			if(shapeType == 0) {
				shapeWidth = (int)(Math.random()* 79) + 10; //87 comes from the max-min +1; where the max is 95 and the min is 10;
				shapeLength = shapeWidth;
				shp = new Circle(locations[i], locations[i], shapeWidth, shapeLength, clrs[clr]);
				shapes[i] = shp;
			}
			if(shapeType == 1) {
				shapeWidth = (int)(Math.random()* 79) + 10;
				shapeLength = (int)(Math.random()* 79) + 10;
				shp = new Rectangle(locations[i], locations[i], shapeWidth, shapeLength, clrs[clr]);
				shapes[i] = shp;
			}
			else if(shapeType == 2){
				shapeWidth = (int)(Math.random()* 79) + 10;
				shapeLength = shapeWidth;
				shp = new Square(locations[i], locations[i], shapeWidth, shapeLength, clrs[clr]);
				shapes[i] = shp;
			}
		}
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(toPaint) {
			Graphics g2d = (Graphics2D) g;
			for(int i = 0; i < shapes.length; i++) {
				g2d.setColor(shapes[i].color);
				if(shapes[i].type == 0)
					g2d.fillOval(shapes[i].x, shapes[i].y, shapes[i].width, shapes[i].height);
				else
					g2d.fillRect(shapes[i].x, shapes[i].y, shapes[i].width, shapes[i].height);
			}
		}
	}
	public class Shapes implements Comparable<Shapes>{
		protected int x;
		protected int y;
		protected int width;
		protected int height;
		protected Color color;
		protected int type;
		public Shapes(int x, int y, int width, int height, Color color) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.color = color;
		}
		public double getSurface() {return -1;} //Will only ever be called if Shape is not defined as a child of Shapes. Return -1 as an indicator for such
		@Override
		public int compareTo(Shapes o) {
			int res = 0;
			if(getSurface() > o.getSurface())
				res = 1;
			else if(getSurface() < o.getSurface())
				res = -1;
			return res;
		}
	}
		
		public class Circle extends Shapes{
			Circle(int x, int y, int width, int height, Color color) {
				super(x, y, width, height, color);
				type = 0;
			}
			public double getSurface() {
				return Math.PI * width/2 * height/2;
			}
		}
		public class Rectangle extends Shapes{
			public Rectangle(int x, int y, int width, int height, Color color) {
				super(x, y, width, height, color);
				type = 1;
			}
			public double getSurface() {
				return width * height; 
			}
		}
		public class Square extends Shapes{
			public Square(int x, int y, int width, int height, Color color) {
				super(x, y, width, height, color);
				type = 2;
			}
			public double getSurface() {
					return width * height;
			}
		}
	public class bubbleSort {
		private void sort(Shapes shapes[]) {
			for(int j = 0; j < shapes.length - 1; j++) {
				for(int k = 0; k < shapes.length - j - 1; k++) {
					if(shapes[k].compareTo(shapes[k+1]) == 1) {
						Shapes temp = shapes[k];
						shapes[k] = shapes[k+1];
						shapes[k+1] = temp;
					}
				}
			}
		}
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Display Shapes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setResizable(false);
		Display shapes = new Display();
		shapes.setLayout(new FlowLayout());
		JButton loadB = new JButton();
		JButton sortB = new JButton();
		loadB.setText("Load Shapes");
		sortB.setText("Sort Shapes");
		loadB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shapes.loadShapes(true);
			}
		});
		sortB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shapes.sortShapes(true);
			}
		});
		shapes.add(loadB);
		shapes.add(sortB);
		frame.add(shapes);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
	
