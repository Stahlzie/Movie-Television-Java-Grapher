/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panelPackage;

import com.omertron.themoviedbapi.model.credits.CreditMovieBasic;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author PATTERSONSW1, GRAPH WILL BE DRAWN IN HERE
 */
public class GraphPanel extends JPanel {

    Map<String, JLabel> movieLabelLocations = new HashMap<>();
    Map<String, JLabel> actorLabelLocations = new HashMap<>();
    public List<String> queriedMovies = new ArrayList<>();
    public Map<String, Set<String>> movieToActorListMap = new HashMap<>();
    public int castSize = 0;

    Rectangle rect = this.getBounds();

    //Grapher myGraph;
    //data structure which holds info to be graphed goes here
    public GraphPanel() { //pass in the data structure to this constructor
        //set the member data structure to be equal to the one that was passed in. 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        rect = this.getBounds();
        Point center = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
        g.setColor(Color.BLACK);

        if (queriedMovies.size() < 2) {
            g.drawString("Please Add At Least Two Movies", 15, 15); //note 15, 15 means the left bottom corner of the label is at 15, 15
            //so heres how to draw a label. you can do a line with g.drawLine(x1, y1, x2, y2)
            //you can use rect.x, y, width, and height to help place stuff
            //also you can use center.x, center.y to help place stuff.
        } else {
            //Draw stuff
            for (Map.Entry<String, Set<String>> entry : movieToActorListMap.entrySet()) {
                String movie = entry.getKey();
                JLabel movieLabel = movieLabelLocations.get(movie);
                HashSet<String> actorSet = (HashSet<String>) entry.getValue();

                int movieLabelX = movieLabel.getX() + movieLabel.getWidth()/2;
                int movieLabelY = movieLabel.getY() + movieLabel.getHeight()/2;
                
                for (String actor : actorSet) {
                    JLabel actorLabel = actorLabelLocations.get(actor);
                    //draw line
                    int actorLabelX = actorLabel.getX() + actorLabel.getWidth()/2;
                    int actorLabelY = actorLabel.getY() + actorLabel.getHeight()/2;
                    g.drawLine(actorLabelX, actorLabelY, movieLabelX, movieLabelY);
                    //draw actor JLabel
                    //g.drawString(actor, actorPoint.getX(), actorPoint.getY());
                }
                //draw movie JLabel
                //g.drawString(movie, moviePoint.getX(), moviePoint.getY());
            }
        }
    }

    // call this everytime you update MoveToActorListMap
    public void updateLocations() {
        List<String> moviesToRemove = new ArrayList<>();
        //remove movies that only have 1 cast member
        for (Map.Entry<String, Set<String>> entry : movieToActorListMap.entrySet()) {
            if (entry.getValue().size() < 3) { // if movie only has one cast member from queried movies
                moviesToRemove.add(entry.getKey());
            }
        }

        for (String movie : moviesToRemove) {
            movieToActorListMap.remove(movie);
        }

        movieLabelLocations.clear();
        actorLabelLocations.clear();

        int queriedMoviesAdded = 0;

        for (Map.Entry<String, Set<String>> entry : movieToActorListMap.entrySet()) {
            String movie = entry.getKey();
            HashSet<String> actorSet = (HashSet<String>) entry.getValue();
            //check to see if movie added, if not update
            if (!movieLabelLocations.containsKey(movie)) {
                //check to see if a query movie
                if (queriedMovies.contains(movie)) {
                    double x = (1.0 / 4.0) * rect.width;
                    double y = (queriedMoviesAdded + 1.0) / (queriedMovies.size() + 1.0) * rect.height;
                    JLabel label = new JLabel(movie);
                    label.setBounds((int)x,(int)y,label.getPreferredSize().width,label.getPreferredSize().height);
                    label.setOpaque(true);
                    label.setBackground(Color.white);
                    movieLabelLocations.put(movie, label);
                    this.add(label);
                    queriedMoviesAdded++;
                } else { //
                    double x = (3.0 / 4.0) * rect.width;
                    double y = (movieLabelLocations.size() + 1.0) / (movieToActorListMap.size() - queriedMovies.size() + 1.0) * rect.height;
                    JLabel label = new JLabel(movie);
                    label.setBounds((int)x,(int)y,label.getPreferredSize().width,label.getPreferredSize().height);
                    label.setOpaque(true);
                    label.setBackground(Color.white);
                    movieLabelLocations.put(movie, label);
                    this.add(label);
                }
            }
            //check to see if actor added, if not add
            for (String actor : actorSet) {
                //check to see if actor added, if not update
                if (!actorLabelLocations.containsKey(actor)) {
                    double x = (1.0 / 2.0) * rect.width;
                    double y = (actorLabelLocations.size() + 1.0) / (castSize + 1.0) * rect.height;
                    Point myPoint = new Point((int)x,(int)y);
                    JLabel label = new JLabel(actor);
                    label.setBounds((int)x,(int)y,label.getPreferredSize().width,label.getPreferredSize().height);
                    label.setOpaque(true);
                    label.setBackground(Color.white);
                    actorLabelLocations.put(actor, label);
                    this.add(label);
                }
            }
        }
        System.out.println("Finished Update");
    }
}
