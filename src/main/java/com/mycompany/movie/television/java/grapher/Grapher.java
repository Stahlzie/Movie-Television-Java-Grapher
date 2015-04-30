/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movie.television.java.grapher;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.model.credits.CreditMovieBasic;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.person.PersonCreditList;
import com.omertron.themoviedbapi.results.ResultList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import panelPackage.GraphPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import java.awt.FileDialog;

/**
 *
 * @author STAHLZD1
 */
public class Grapher extends javax.swing.JFrame implements ActionListener {

    public static final String APIKey = "4478254429838ff2f8bef88ec1097909";
    private TheMovieDbApi TMDb = null;
    public Map<String, Set<String>> movieToActorListMap = new HashMap<>();
    public Map<String, List<String>> actorToMovieListMap = new HashMap<>();
    private List<String> moviesQueried = new ArrayList<>();
    JButton addButton = new JButton("Add Movie");
    JButton graphButton = new JButton("Graph!");
    JTextField movieInput = new JTextField("Movie Title...");
    JLabel moviesList = new JLabel();
    GraphPanel graphPanel = new GraphPanel();

    /**
     * Creates new form Grapher
     */
    public Grapher() {
        initializeComponents();

        //Initial Tests Query
        searchMovieByString("The Avengers");
        if ("".equals(moviesList.getText())) {
            moviesList.setText("The Avengers");
            movieInput.setText("");
        } else {
            moviesList.setText(moviesList.getText() + ", " + "The Avengers");
            movieInput.setText("");
        }

        searchMovieByString("Avengers: Age of Ultron");
        if ("".equals(moviesList.getText())) {
            moviesList.setText("Avengers: Age of Ultron");
            movieInput.setText("");
        } else {
            moviesList.setText(moviesList.getText() + ", " + "Avengers: Age of Ultron");
            movieInput.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initializeComponents() {
        //menu stuff
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Save",
                KeyEvent.VK_S);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(
        //        KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This saves a graph");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //put stuff for saving a file here
                FileDialog sfd = new FileDialog(Grapher.this, "Save Graph", FileDialog.SAVE);
                String filename = "untitled.movie";
                sfd.setFile(filename);
                sfd.setVisible(true);
                if (sfd.getDirectory() != null && sfd.getFile() != null && sfd.getFile().length() > 0) {
                    filename = sfd.getDirectory() + sfd.getFile();
                    if (!filename.substring(filename.length()-6).equals(".movie")) {
                        filename += ".movie";
                    }
                    if (saveGraph(filename)) {
                        setTitle(filename);
                    }
                }
            }
        });

        menuItem = new JMenuItem("Load",
                KeyEvent.VK_L);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(
        //        KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This loads a graph");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //put stuff for loading a file here
                FileDialog lfd = new FileDialog(Grapher.this, "Load Graph", FileDialog.LOAD);
                lfd.setFile("*.movie");
                lfd.setVisible(true);
                if (lfd.getDirectory() != null && lfd.getFile() != null && lfd.getFile().length() > 0) {
                    String filename = lfd.getDirectory()+lfd.getFile();
                    if (loadGraph(filename)) {
                        setTitle(filename);
                        graphPanel.removeAll();
                        graphPanel.movieToActorListMap = movieToActorListMap;
                        graphPanel.queriedMovies = moviesQueried;
                        graphPanel.castSize = actorToMovieListMap.size();
                        graphPanel.updateLocations();
                        graphPanel.repaint();
                    }
                }
            }
        });

        this.setJMenuBar(menuBar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 50, 600, 500);

        //graphPanel.setBackground(Color.RED);
        graphPanel.setLayout(null);
        graphPanel.setPreferredSize(new Dimension(800, 600));
        graphPanel.setSize(new Dimension(800,600));
        getContentPane().add(graphPanel, BorderLayout.CENTER);

        addButton.addActionListener(this);
        graphButton.addActionListener(this);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);

        form.add(moviesList);
        form.add(movieInput);
        form.add(addButton);
        form.add(graphButton);
        getContentPane().add(form, BorderLayout.SOUTH);

        pack();
        
        try {
            //init connection to TheMovieDbApi
            TMDb = new TheMovieDbApi(APIKey);
            System.out.println("Connected to TMDb");
        } catch (MovieDbException ex) {
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //updates MoviesToActorListMap
    private void searchMovieByString(String queryString) {
        try {
            // Query Search Term
            ResultList<MediaBasic> resultsList = null;
            resultsList = TMDb.searchMulti(queryString, 1, "en", false);
            System.out.println("Search Completed: " + queryString);

            // Check to make sure there are results media result;
            if (resultsList.isEmpty()) {
                System.out.println("No results for: " + queryString);
                return;
            }

            // Get a list of media results
            List<MediaBasic> mediaList = resultsList.getResults();
            // Create a MediaBasic to hold result
            MediaBasic mediaResult = new MediaBasic();
            //System.out.println("Media Results into List");

            //find first movie in list
            Iterator<MediaBasic> mediaIterator = mediaList.iterator();
            boolean firstResultNotFound = true;
            while (mediaIterator.hasNext() && firstResultNotFound) {
                mediaResult = mediaIterator.next();
                if (mediaResult.getMediaType() == MediaType.MOVIE) {
                    firstResultNotFound = false;
                }
            }

            if (firstResultNotFound) { //No Movies Returned
                System.out.println("No Movies found in results for: " + queryString);
                return;
            }
            System.out.println("Initial Movie Media Result Found: " + mediaResult.toString());

            MovieBasic mb = (MovieBasic) mediaResult;
            moviesQueried.add(mb.getTitle());
            //Attempt to get movie credita
            int mediaId = mediaResult.getId();
            MediaCreditList movieCredits = TMDb.getMovieCredits(mediaId);
            //System.out.println("Initial Movie Credits Retrieved for: " + mediaResult.toString());

            //Get cast of movie
            List<MediaCreditCast> movieCast = movieCredits.getCast();
            //System.out.println("Initial Movie Cast converted to List");

            //Iterate through cast of movie
            Iterator<MediaCreditCast> i = movieCast.iterator();
            while (i.hasNext()) {
                MediaCreditCast castMember = i.next();
                
                //add this movie
                Set<String> set = new HashSet<>();
                if (movieToActorListMap.containsKey(mb.getTitle())) {
                    set = movieToActorListMap.get(mb.getTitle());
                }
                //add cast member to set
                set.add(castMember.getName());
                movieToActorListMap.put(mb.getTitle(), set);
                
                //System.out.println("  Iterating through cast member: " + castMember.getName());
                // get id of cast member to search for their movie credits
                int castMemberId = castMember.getCastId();
                //System.out.print("    (MovieCredits..." + castMemberId + "...");
                try {
                    PersonCreditList<CreditMovieBasic> castMemberMovieCredits = TMDb.getPersonMovieCredits(castMemberId, "en");
                    //System.out.println("completed)");

                    List<CreditMovieBasic> castMemberMovieList = castMemberMovieCredits.getCast();
                    List<String> castMemberMovieTitleList = new ArrayList<>();
                    for (CreditMovieBasic movie : castMemberMovieList) {
                        castMemberMovieTitleList.add(movie.getTitle());
                    }
                    actorToMovieListMap.put(castMember.getName(), castMemberMovieTitleList);

                    //iterate through cast of movie
                    Iterator<CreditMovieBasic> j = castMemberMovieList.iterator();
                    while (j.hasNext()) {
                        CreditMovieBasic castMemberMovie = j.next();
                        
                        //get set of actors for movie is already in our map
                        Set<String> creditsMovieSet = new HashSet<>();
                        if (movieToActorListMap.containsKey(castMemberMovie.getTitle())) {
                            creditsMovieSet = movieToActorListMap.get(castMemberMovie.getTitle());
                        }
                        //add cast member to set
                        creditsMovieSet.add(castMember.getName());
                        //update set in our map
                        movieToActorListMap.put(castMemberMovie.getTitle(), creditsMovieSet);
                    }
                } catch (MovieDbException ex) {
                    //System.out.println("...failed");
                    //System.out.println("MovieDbException Occurred, Actor not found");
                    java.util.logging.Logger.getLogger(Grapher.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        } catch (MovieDbException ex) {
            System.out.println("MovieDbException Occurred");
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Finished results for: " + queryString);
    }
    
    public boolean loadGraph(String filename) {
        try {
            ObjectInputStream oiStream = 
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream(filename)));
            movieToActorListMap = (Map<String, Set<String>>)oiStream.readObject();
            moviesQueried = (List<String>)oiStream.readObject();
            String moviesListString = "";
            for (String movie : moviesQueried) {
                moviesListString += movie + ", ";
            }
            moviesListString = moviesListString.substring(0, moviesListString.length()-2);
            moviesList.setText(moviesListString);
            return true;
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(this, "couldnt load the file");
            exc.printStackTrace();
            return false;
        }
    }
    
    public boolean saveGraph(String filename) {
        try {
            ObjectOutputStream ooStream = 
                    new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(filename)));
            ooStream.writeObject(movieToActorListMap);
            ooStream.writeObject(moviesQueried);
            ooStream.flush();
            ooStream.close();
            return true;
        } catch(Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(this, "couldnt save the file");
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (addButton.equals(e.getSource())) {
            //do stuff for the add button
            if (!"".equals(movieInput.getText())) {
                searchMovieByString(movieInput.getText());

                if ("".equals(moviesList.getText())) {
                    moviesList.setText(movieInput.getText());
                    movieInput.setText("");
                } else {
                    moviesList.setText(moviesList.getText() + ", " + movieInput.getText());
                    movieInput.setText("");
                }
            }
        } else {
            if (moviesQueried.size() < 2) {
                graphPanel.add(new JLabel("Please add at least two movies"));
                graphPanel.removeAll();
            } else {
                // do stuff for the graph button
                graphPanel.removeAll();
                graphPanel.movieToActorListMap = this.movieToActorListMap;
                graphPanel.queriedMovies = this.moviesQueried;
                graphPanel.castSize = this.actorToMovieListMap.size();
                graphPanel.updateLocations();
            }
        }
        graphPanel.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Grapher().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
