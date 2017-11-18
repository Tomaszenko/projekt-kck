package game.gui;

import game.GameModel;
import models.*;
import services.ImageService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyGameFrame extends JFrame {

    private JLayeredPane mainPane;
    private RoadLeftSide roadLeftSide;
    private Road road;
    private RoadRightSide roadRightSide;
    private GreenLane greenLane;

    private Speedometer speedometer;
    private Distancemeter distancemeter;

    private Robot robot;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private ImageService imageService = new ImageService();
    private VariableDistanceRenderer renderer;
    private GameOverPanel gameOverPanel;
    private TrackCompletedPanel trackCompletedPanel;

    public MyGameFrame( String title )
    {
        super( title );                      // invoke the JFrame constructor
        renderer = new VariableDistanceRenderer(this);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPane = new JLayeredPane();
        setContentPane(mainPane);

//        createBufferStrategy(2);
//        setIgnoreRepaint(true);
//        setLayout(null);
//        getContentPane().setLayout(null);

        try {
            robot = new Robot();
        }catch(AWTException exc) {
            exc.printStackTrace();
        }

        setLayout( new FlowLayout());
        requestFocusInWindow();
    }

    public void drawMainMenu(MainMenu mainMenu) {
        getContentPane().removeAll();
        getContentPane().setLayout(new FlowLayout());

        JButton button1 = new JButton("NOWA GRA");
        JButton button2 = new JButton("WYJŚCIE");

        button1.setSize(200,50);
        button2.setSize(200,50);
        int keyCode = KeyEvent.VK_ENTER;

        button1.addActionListener((l) -> {
            requestFocusInWindow();
            mainMenu.setPosition(0);

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        });

        button2.addActionListener((l) -> {
            requestFocusInWindow();
            mainMenu.setPosition(1);

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        });

        getContentPane().add(button1);
        getContentPane().add(button2);

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void drawTracksMenu(TracksMenu tracksMenu) {
        getContentPane().removeAll();
        getContentPane().setLayout(new FlowLayout());

        int keyCode = KeyEvent.VK_ENTER;

        int numberOfTracks = tracksMenu.getGameMenuItems().size();
        List<TracksMenuItem> items = tracksMenu.getGameMenuItems();

        for(int i=0; i!=numberOfTracks; ++i) {
            final int idx = i;
            JButton button = new JButton(items.get(i).getName());
            button.setSize(200,50);

            button.addActionListener((l) -> {
                requestFocusInWindow();
                tracksMenu.setPosition(idx);

                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            });
            getContentPane().add(button);
        }
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void drawGame(GameModel gameModel) {
        getContentPane().removeAll();
        getContentPane().revalidate();
        getContentPane().setLayout(null);

//        graphics = bufferStrategy.getDrawGraphics();


//        Insets insets = gamePanel.getInsets();
        speedometer = new Speedometer(gameModel.getPlayerCar().getSpeed()*10);
        distancemeter = new Distancemeter(gameModel.getPlayerCar().getMetersFromStart(), gameModel.getCurrentRoute().getDistance());

        Graphics g = getContentPane().getGraphics();
        FontMetrics met = g.getFontMetrics();
        int height = met.getHeight();
        int width1 = 2*met.stringWidth(speedometer.getText());
        int width2 = 2*met.stringWidth(distancemeter.getText());

        speedometer.setSize(width1, height);
        speedometer.setLocation(0,5);

        distancemeter.setSize(width2, height);
        distancemeter.setLocation(0, 5+2*height);

        roadLeftSide = new RoadLeftSide();
        roadLeftSide.setSize(1,getContentPane().getHeight());
        roadLeftSide.setLocation(getContentPane().getWidth()/2 - 60, 0);

        roadRightSide = new RoadRightSide();
        roadRightSide.setSize(1,getContentPane().getHeight());
        roadRightSide.setLocation(getContentPane().getWidth()/2 + 60, 0);

        PlayerCar playerCar = gameModel.getPlayerCar();

        road = new Road(gameModel.getOtherCars().stream().filter(
                car -> { return car.getMetersFromStart() < playerCar.getMetersFromStart() + getHeight() - 60
                        && car.getMetersFromStart() >= playerCar.getMetersFromStart() - 60; })
                .collect(Collectors.toList()), playerCar);
        road.setSize(100, getContentPane().getHeight());
        road.setLocation(getContentPane().getWidth()/2 - 50, 0);

        greenLane = new GreenLane(gameModel.getCurrentRoute().getRoadSigns(), playerCar.getMetersFromStart());
        greenLane.setSize(100, getContentPane().getHeight());
        greenLane.setLocation((int)(0.65*getContentPane().getWidth()), 0);

        getContentPane().add(speedometer);
        getContentPane().add(distancemeter);
        getContentPane().add(roadLeftSide);
        getContentPane().add(roadRightSide);
        getContentPane().add(road);
        getContentPane().add(greenLane);

//        roadLeftSide = new JPanel();
//        roadRightSide = new JPanel();
//
//        roadLeftSide.setSize(5,getContentPane().getHeight());
//        roadLeftSide.setLocation(getWidth() - 50, 0);
//
//        roadRightSide.setSize(5,getContentPane().getHeight());
//        roadRightSide.setLocation(getWidth() + 50, 0);
//
//        getContentPane().add(speedometer);
//        getContentPane().add(distancemeter);
//        getContentPane().add(roadLeft);
//        getContentPane().add(roadRight);

//        ImageIcon iconUp = createImageIcon("car_up.png");
//        ImageIcon iconDown = createImageIcon("car_down.png");
//
//        JLabel playerCar = new JLabel("Gracz", iconUp, JLabel.CENTER);
//        playerCar.setSize(50,50);
//
//        int carX = renderer.getXForCarLane(gameModel.getPlayerCar().getCarLane());
//        int carY = renderer.getY(gameModel.getPlayerCar());
//        System.out.println("x="+carX);
//        System.out.println("y="+carY);
//
//        playerCar.setLocation(carX, carY);
//        movingObjects.add(playerCar);
//        getContentPane().add(playerCar);
//        graphics.drawImage(iconUp.getImage(), carX, carY, 50, 50, null, null);
//        getContentPane().revalidate();

//        for(OtherCar otherCar: gameModel.getOtherCars()) {
//            int currX = renderer.getXForCarLane(gameModel.getPlayerCar().getCarLane());
//            int currY = renderer.getY(gameModel.getPlayerCar());
//
//            if(currY >= 0 && currY <= getHeight() + 3) {
//                JLabel car = new JLabel("Gracz", otherCar.getCarDirection() == CarDirection.NORTH ?
//                        iconUp : iconDown, JLabel.CENTER);
//                car.setSize(15,60);
//                car.setLocation(currX, currY);
//                movingObjects.add(car);
//                getContentPane().add(car);
//                graphics.drawImage(((ImageIcon)car.getIcon()).getImage(), currX, currY, 50, 50, null, null);
//            }
//        }
//        renderRoad();
//        getContentPane().revalidate();
//        getContentPane().repaint();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        renderRoad();
//        graphics.dispose();
//        bufferStrategy.show();

        getContentPane().revalidate();
        getContentPane().repaint();
//        revalidate();
//        repaint();
    }

    public void updateGame(GameModel gameModel) {

        renderer.updateReferenceDistance(gameModel.getPlayerCar().getMetersFromStart());

        speedometer.updateSpeed(gameModel.getPlayerCar().getSpeed());
        distancemeter.updateDistance(gameModel.getPlayerCar().getMetersFromStart());
        speedometer.repaint();
        distancemeter.repaint();


        PlayerCar playerCar = gameModel.getPlayerCar();

        road.updateCars(gameModel.getOtherCars().stream().filter(
                car -> { return (car.getMetersFromStart() < playerCar.getMetersFromStart() + getContentPane().getHeight() - 60)
                            && (car.getMetersFromStart() >= playerCar.getMetersFromStart() - 60); })
                .collect(Collectors.toList()), playerCar);

        greenLane.updateSigns(gameModel.getCurrentRoute().getRoadSigns(), playerCar.getMetersFromStart());


        getContentPane().repaint();
    }

    public void gameOver() {

        gameOverPanel = new GameOverPanel();
        mainPane.add(gameOverPanel, mainPane.highestLayer());
        Graphics g = gameOverPanel.getGraphics();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics met = g.getFontMetrics();
        int height = met.getHeight();
        int width = met.stringWidth("GAME OVER");
        gameOverPanel.setSize(width, height);
        gameOverPanel.setLocation(getContentPane().getWidth()/2 - width/2, (int)(0.2*height));
//        mainPane.setComponentZOrder(gameOverPanel, 1);

        getContentPane().revalidate();
        getContentPane().repaint();
    }


    public void trackCompleted() {

        trackCompletedPanel = new TrackCompletedPanel();
        mainPane.add(trackCompletedPanel, mainPane.highestLayer());
        Graphics g = trackCompletedPanel.getGraphics();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics met = g.getFontMetrics();
        int height = met.getHeight();
        int width = met.stringWidth("WELL DONE");
        trackCompletedPanel.setSize(width, height);
        trackCompletedPanel.setLocation(getContentPane().getWidth()/2 - width/2, (int)(0.2*height));
//        mainPane.setComponentZOrder(gameOverPanel, 1);

        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private int gameHeight() {
        return getHeight();
    }

    private int gameWidth() {
        return getWidth();
    }
}