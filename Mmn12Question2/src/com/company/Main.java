/**
 * A Robots Matrx World game, made with Java Swing
 * @author Roman Smirnov 312914443
 * @since 15/4/2016
 *
 * NOTE: was using mouse clicks instead of required 30 moves for debug, ran out of time before needing to turn in. Sorry.
 * Left click - add robot
 * Left click on robot - move robot
 * Right click on robot - turn robot
 */

package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


/**
 * The main class containing the main method
 */
public class Main {
    private static RobotsWorld mRobotsWorld;
    private static int mSize;
    private static final int DIMENSION = 50;

    /**
     * the main method
     * @param args unused
     */
    public static void main(String[] args) {
        int size = 0;
        try {
            //get the world size from the user
            size = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Input World Size",
                    JOptionPane.INFORMATION_MESSAGE));

            //if user pressed cancel or input wasn't a number
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Bye Bye", "Bye Bye", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        // if input was zero or less
        if(size <= 0){
            JOptionPane.showMessageDialog(null, "Size should be greater than 0", "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        //start the game by instantiating an object of class Main
        new Main(size);
    }

    /**
     * Main class constructor
     * @param size size of the matrix to be created
     */
    public Main(int size) {
        mRobotsWorld = new RobotsWorld(size);
        mSize = size;

        //window/frame set up
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                JFrame frame = new JFrame("Robots World Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /**
     * Inner class TestPane - this one holds all the matrix cells
     */
    public class TestPane extends JPanel {

        /**
         * main empty constructor
         */
        public TestPane() {

            //set a gird layout to hold all the cells
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            //i.e rows are Y coords, cols are X coords
            for (int row = 0; row < mSize; row++) {
                for (int col = 0; col < mSize; col++) {
                    gbc.gridx = row;
                    gbc.gridy = col;
                    //create a CellPane in the current grid position
                    CellPane cellPane = new CellPane(new Position(row,col),this);
                    Border border = null;
                    //add some cool looking borders
                    if (col < 4) {
                        if (row < 4) {
                            border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                        }
                    } else {
                        if (row < 4) {
                            border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                        } else {
                            border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                        }
                    }
                    //add the border and cell to matrix panel
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
    }

    /**
     * inner class CellPane - this one is single cell in the matrix of cells
     */
    public class CellPane extends JPanel {

        //private fields
        private Color defaultBackground;
        private Position mPosition;


        /**
         * main constructor
         * @param position Position object, holds the position in the matrix
         * @param testPane TestPane object, holds the parent matrix - used to call repaint()
         */
        public CellPane(Position position, final TestPane testPane) {

            //TODO: add null check
            mPosition = position;
            defaultBackground = getBackground();

            // a mouse listener to detect clicks and play the game
            /*
             * Left click - add robot
             * Left click on robot - move robot
             * Right click on robot - turn robot
             */
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //move robot if position not empty
                    if(mRobotsWorld.getRobot(mPosition)!=null){
                        //if it was a right click
                        if(SwingUtilities.isRightMouseButton(e)){
                            mRobotsWorld.getRobot(mPosition).turnRight();
                            testPane.repaint();
                            return;
                        }
                        try{
                            mRobotsWorld.moveRobot(mPosition);
                        }catch(IllegalPositionException exception){
                            JOptionPane.showMessageDialog(null, "moveRobot() failure!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        testPane.repaint();
                        return;
                    }

                    //add new robot if empty
                    try {
                        mRobotsWorld.addRobot(mPosition);
                        System.out.println(mPosition.getX());
                        System.out.println(mPosition.getY());
                        testPane.repaint();
                    }catch (IllegalPositionException exception){
                        JOptionPane.showMessageDialog(null, "addRobot() failure!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

                //highlight the cell currently pointed at
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(Color.BLUE);
                }
                //de-highlight the cell currently pointed at
                @Override
                public void mouseExited(MouseEvent e) {
                    resetDefaultBackground();
                }
            });

        }

        /**
         * the main paint method - this one also gets recalled whe repaint() is called
         * @param  g Graphics object
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            //check if there's a robot in the current cell, if not returns
            if(mRobotsWorld.getRobot(mPosition) == null) {
                return;
            }
            //if the check (above) passed, draw a circle and direction marker inside the current cell

            //draw oval
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setColor(Color.magenta);
            graphics2D.fillOval(0, 0, getWidth(), getHeight());

            //calculate center of oval
            int centerXY = DIMENSION / 2;

            //get the direction and assign director indicator character
            String text ="";
            if(mRobotsWorld.getRobot(mPosition).getDirection()==Robot.UP){
                text="▲";
            }else if(mRobotsWorld.getRobot(mPosition).getDirection()==Robot.RIGHT) {
                text = "▶";
            }else if(mRobotsWorld.getRobot(mPosition).getDirection()==Robot.DOWN) {
                text = "▼";
            }else if(mRobotsWorld.getRobot(mPosition).getDirection()==Robot.LEFT) {
                text = "◀";
            }

            // Draw centered text
            FontMetrics fm = g.getFontMetrics();
            double textWidth = fm.getStringBounds(text, g).getWidth();
            g.setColor(Color.WHITE);
            g.drawString(text, (int) (centerXY - textWidth / 2),
                    (int) (centerXY + fm.getMaxAscent() / 2));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(DIMENSION, DIMENSION);
        }

        public void resetDefaultBackground(){

            setBackground(defaultBackground);
        }

    }

}