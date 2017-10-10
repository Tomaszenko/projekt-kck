package models;

import java.util.List;

public class GameMenu {
    private int position;
    private List<MenuItem> gameMenuItems;

    public GameMenu(List<MenuItem> gameMenuItems) {
        this.position = 0;
        this.gameMenuItems = gameMenuItems;
    }

    public MenuItem getSelected() {
        return gameMenuItems.get(position);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPreviousItem() {
        this.position = position > 0 ? position - 1 : menuSize() - 1;
    }

    public void setNextItem() {
        this.position = ++position < menuSize() ? position : 0;
    }

    public List<MenuItem> getGameMenuItems() {
        return gameMenuItems;
    }

    private int menuSize() {
        return gameMenuItems.size();
    }
}
