package game.text;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import models.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VariableDistanceRenderer {
    private int refDistance;
    private int totalDistance;
    private SwingTerminalFrame terminal;

    private static final int SCALE = 32;
    private static final int CAR_PIXELS = 4;

    public VariableDistanceRenderer(SwingTerminalFrame terminal) {
        this.refDistance = 0;
        this.terminal = terminal;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public void updateReferenceDistance(int currentDistance) {
        this.refDistance = currentDistance;
    }

    public void renderCars(List<OtherCar> cars) {
        for(Car car: cars) {
            renderCar(car);
        }
    }

    public void renderPlayerCar(PlayerCar playerCar) {
        renderCar(playerCar);
    }

    private void renderCar(Car car) {
        CarColor color = car.getCarColor();
        CarDirection direction = car.getCarDirection();

        terminal.setForegroundColor(convertCarColor(color));

        if(car.isDestroyed())
            renderDestroyedCar(car);
        else {
            if (direction == CarDirection.NORTH)
                renderForwardsCar(car);
            else
                renderBackwardsCar(car);
        }
//        System.out.println("out from rendering car");
    }

    private void renderDestroyedCar(Car car) {
        int y = translateIntoPixelPosition(calculateRelativeY(car));
        int x = getXForCarLane(car.getCarLane());
        terminal.setForegroundColor(new TextColor.RGB(255,165,0));

        if(y >=0 && y<= howManyRows() + 3) {
            terminal.setCursorPosition(x, y);
            terminal.putCharacter('|');
            terminal.setCursorPosition(x - 1, y + 1);
            terminal.putCharacter('-');
            terminal.setCursorPosition(x, y + 1);
            terminal.putCharacter('*');
            terminal.setCursorPosition(x + 1, y + 1);
            terminal.putCharacter('-');
            terminal.setCursorPosition(x, y + 2);
            terminal.putCharacter('|');
            terminal.setCursorPosition(x, y + 3);
            terminal.putCharacter('|');
        }

        terminal.setForegroundColor(new TextColor.RGB(255,255,255));
    }

    private void renderForwardsCar(Car car) {
        int y = translateIntoPixelPosition(calculateRelativeY(car));
        int x = getXForCarLane(car.getCarLane());
//        System.out.println("y_car_right="+y);

        if(y >=0 && y<= howManyRows() + 3) {
            terminal.setCursorPosition(x - 1, y);
            terminal.putCharacter('[');
            terminal.setCursorPosition(x, y);
            terminal.putCharacter('*');
            terminal.setCursorPosition(x + 1, y);
            terminal.putCharacter(']');
            terminal.setCursorPosition(x, y + 1);
            terminal.putCharacter('I');
            terminal.setCursorPosition(x, y + 2);
            terminal.putCharacter('O');
            terminal.setCursorPosition(x - 1, y + 3);
            terminal.putCharacter('[');
            terminal.setCursorPosition(x, y + 3);
            terminal.putCharacter('-');
            terminal.setCursorPosition(x + 1, y + 3);
            terminal.putCharacter(']');
        }
    }

    private void renderBackwardsCar(Car car) {
        int y = translateIntoPixelPosition(calculateRelativeY(car));
        int x = getXForCarLane(car.getCarLane());
//        System.out.println("y_car_left="+y);

        if(y >=0 && y<= howManyRows() + 3) {
            terminal.setCursorPosition(x - 1, y);
            terminal.putCharacter('[');
            terminal.setCursorPosition(x, y);
            terminal.putCharacter('*');
            terminal.setCursorPosition(x + 1, y);
            terminal.putCharacter(']');
            terminal.setCursorPosition(x, y - 1);
            terminal.putCharacter('I');
            terminal.setCursorPosition(x, y - 2);
            terminal.putCharacter('O');
            terminal.setCursorPosition(x - 1, y - 3);
            terminal.putCharacter('[');
            terminal.setCursorPosition(x, y - 3);
            terminal.putCharacter('-');
            terminal.setCursorPosition(x + 1, y - 3);
            terminal.putCharacter(']');
        }
    }

    public void renderRoadSigns(Map<Integer, String> roadsigns) throws IOException {
        for(Map.Entry<Integer, String> entry: roadsigns.entrySet()) {
            RoadSign roadSign = new RoadSign(entry.getKey(), entry.getValue());
            int y = translateIntoPixelPosition(calculateRelativeY(roadSign));
            int x = (int)(howManyColumns()*0.65);
            renderRoadSign(roadSign, x, y);
        }
    }

    private void renderRoadSign(RoadSign roadSign, int x, int y) {
        if(y >=0 && y<= howManyRows() + 3) {
            terminal.resetColorAndSGR();
            terminal.newTextGraphics().setBackgroundColor(new TextColor.RGB(25,100,70))
                    .putString(new TerminalPosition(x,y),roadSign.getText());
            terminal.setBackgroundColor(new TextColor.RGB(0,0,0));
            terminal.setCursorPosition(x + roadSign.getText().length()/2, y+1);
            terminal.putCharacter('|');
            terminal.setCursorPosition(x + roadSign.getText().length()/2, y+2);
            terminal.putCharacter('|');
            terminal.setCursorPosition(x + roadSign.getText().length()/2, y+3);
            terminal.putCharacter('|');
        }
    }

    public void renderInterruptedLine(int currentDistance) {
        int y = translateIntoPixelPosition((totalDistance - currentDistance)*2/3);
        int x = howManyColumns()/2;

        y = y % 8;

        for(int i=y; i!=howManyRows(); ++i) {
            terminal.setCursorPosition((int)(howManyColumns()/2), i);
            if((i-y)/4%2 == 0)
                terminal.putCharacter('|');
        }

        if(y > 4) {
            for(int i=0; i!=(y-4); ++i) {
                terminal.setCursorPosition((int)(howManyColumns()/2), i);
                terminal.putCharacter('|');
            }
        }

    }

    private int calculateRelativeY(RenderableGameObject object) {
        return (int)Math.ceil((double)(object.getMetersFromStart() - refDistance)/SCALE);
    }

    private int getXForCarLane(CarLane carLane) {
        if(carLane==CarLane.LEFT)
            return howManyColumns()/2 - 5;
        else
            return howManyColumns()/2 + 5;
    }

    private int translateIntoPixelPosition(int relativePosition) {
        return howManyRows() - 6 - relativePosition;
    }

    private TextColor convertCarColor(CarColor carColor) {
        switch(carColor) {
            case RED:
                return new TextColor.RGB(255,100,100);
            case BLUE:
                return new TextColor.RGB(100,100,255);
            case GREEN:
                return new TextColor.RGB(100,255,100);
            case YELLOW:
                return new TextColor.RGB(200,200,50);
            case PINK:
                return new TextColor.RGB(200,50,200);
            default:
                return null;
        }
    }

    private int howManyColumns() {
        return terminal.getTerminalSize().getColumns();
    }

    private int howManyRows() {
        return terminal.getTerminalSize().getRows();
    }
}
