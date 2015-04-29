/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panelPackage;

import com.mycompany.movie.television.java.grapher.Grapher;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author PATTERSONSW1, GRAPH WILL BE DRAWN IN HERE
 */
public class GraphPanel extends JPanel {
    
    //Grapher myGraph;
    //data structure which holds info to be graphed goes here
    
    public GraphPanel() { //pass in the data structure to this constructor
        //set the member data structure to be equal to the one that was passed in. 
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Rectangle rect = g.getClipBounds();
        Point center = new Point(rect.x + rect.width/2, rect.y + rect.height/2);
        g.drawString("Label", 15, 15); //note 15, 15 means the left bottom corner of the label is at 15, 15
        //so heres how to draw a label. you can do a line with g.drawLine(x1, y1, x2, y2)
        //you can use rect.x, y, width, and height to help place stuff
        //also you can use center.x, center.y to help place stuff.
        
    }
}
