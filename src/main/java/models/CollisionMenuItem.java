package models;

public enum CollisionMenuItem implements MenuItem{
    GO_TO_MENU, TRY_AGAIN;

    @Override
    public String getName() {
        return this.name().replace('_', ' ');
    }
}
