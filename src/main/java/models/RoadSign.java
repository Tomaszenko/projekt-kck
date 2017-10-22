package models;

public class RoadSign implements RenderableGameObject {

    private int metersFromStart;
    private String text;

    public RoadSign(int metersFromStart, String text) {
        this.metersFromStart = metersFromStart;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getMetersFromStart() {
        return metersFromStart;
    }
}
