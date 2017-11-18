package models;

public enum MainMenuItem implements MenuItem{
    QUIT, NEW_GAME;

    @Override
    public String getName() {
        return this.name().replace('_', ' ');
    }
}
