/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.movie.television.java.grapher;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.MediaType;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.model.credits.CreditMovieBasic;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.media.MediaBasic;
import com.omertron.themoviedbapi.model.media.MediaCreditList;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.person.PersonCreditList;
import com.omertron.themoviedbapi.model.person.PersonInfo;
import com.omertron.themoviedbapi.results.ResultList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @author STAHLZD1
 */
public class Grapher extends javax.swing.JFrame {

    public static final String APIKey = "4478254429838ff2f8bef88ec1097909";
    private TheMovieDbApi TMDb = null;
    private String queryString = "The Pink Panther";
    private Map<CreditMovieBasic, Set<MediaCreditCast>> movieToActorListMap = new HashMap<CreditMovieBasic, Set<MediaCreditCast>>();

    /**
     * Creates new form Grapher
     */
    public Grapher() {
        initComponents();
        try {
            //init connection to TheMovieDbApi
            TMDb = new TheMovieDbApi(APIKey);
            System.out.println("Connected to TMDb");

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
            System.out.println("Media Results into List");

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
            //Attempt to get movie credita
            int mediaId = mediaResult.getId();
            MediaCreditList movieCredits = TMDb.getMovieCredits(mediaId);
            System.out.println("Initial Movie Credits Retrieved");

            //Get cast of movie
            List<MediaCreditCast> movieCast = movieCredits.getCast();
            System.out.println("Initial Movie Cast converted to List");

            //Iterate through cast of movie
            Iterator<MediaCreditCast> i = movieCast.iterator();
            while (i.hasNext()) {
                MediaCreditCast castMember = i.next();

                System.out.println("  Iterating through cast member: " + castMember.getName());

                // get id of cast member to search for their movie credits
                int castMemberId = castMember.getCastId();
                System.out.print("    (MovieCredits..." + castMemberId + "...");
                try {
                    PersonCreditList<CreditMovieBasic> castMemberMovieCredits = TMDb.getPersonMovieCredits(castMemberId, "en");
                    System.out.println("completed)");

                    List<CreditMovieBasic> castMemberMovieList = castMemberMovieCredits.getCast();

                    //iterate through cast of movie
                    Iterator<CreditMovieBasic> j = castMemberMovieList.iterator();
                    while (j.hasNext()) {
                        CreditMovieBasic castMemberMovie = j.next();
                        //get set of actors for movie is already in our map
                        Set<MediaCreditCast> creditMovieSet = new HashSet<MediaCreditCast>() {
                        };
                        if (movieToActorListMap.containsKey(castMemberMovie)) {
                            creditMovieSet = movieToActorListMap.get(castMemberMovie);
                        }
                        //add cast member to set
                        creditMovieSet.add(castMember);
                        //update set in our map
                        movieToActorListMap.put(castMemberMovie, creditMovieSet);
                    }
                } catch (MovieDbException ex) {
                    System.out.println("...failed");
                    System.out.println("MovieDbException Occurred, Actor not found");
                    java.util.logging.Logger.getLogger(Grapher.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        } catch (MovieDbException ex) {
            System.out.println("MovieDbException Occurred");
            java.util.logging.Logger.getLogger(Grapher.class.getName()).log(Level.SEVERE, null, ex);
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
