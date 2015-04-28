/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panelPackage;

import com.mycompany.movie.television.java.grapher.Grapher;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author PATTERSONSW1, GRAPH WILL BE DRAWN IN HERE
 */
public class GraphPanel extends JPanel {
    
    Grapher myGraph;
    
    public GraphPanel(Grapher gg) {
        myGraph = gg;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        myGraph.drawGraph(g);
    }
}
