package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;


public class Main {
    private static RobotsWorld mRobotsWorld;
    private static int mSize;
    private static final int DIMENSION = 50;

    public static void main(String[] args) {
        int size = 0;
        try {
            size = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Text",
                    JOptionPane.INFORMATION_MESSAGE));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Not A Number", "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        if(size <= 0){
            JOptionPane.showMessageDialog(null, "Size should be greater than 0", "ERROR", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        new Main(size);
    }

    public Main(int size) {
        mRobotsWorld = new RobotsWorld(size);
        mSize = size;

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {

            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            for (int row = 0; row < mSize; row++) {
                for (int col = 0; col < mSize; col++) {
                    gbc.gridx = row;
                    gbc.gridy = col;

                    CellPane cellPane = new CellPane(new Position(row,col),this);
                    Border border = null;
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
                    cellPane.setBorder(border);
                    add(cellPane, gbc);
                }
            }
        }
    }

    public class CellPane extends JPanel {

        //private fields
        private Color defaultBackground;
        private Position mPosition;


        //cellpane constructor
        public CellPane(Position position, final TestPane testPane) {

            //TODO: add null check
            mPosition = position;
            defaultBackground = getBackground();

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


                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(Color.BLUE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    resetDefaultBackground();
                }
            });

        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if(mRobotsWorld.getRobot(mPosition) == null) {
                return;
            }
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

        public Position getPosition(){
            return mPosition;
        }
    }

}