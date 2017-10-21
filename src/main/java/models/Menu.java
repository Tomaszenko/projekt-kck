package models;

import java.util.List;

public class Menu<T extends MenuItem> {
    private int position;
    private List<T> menuItems;

    public Menu(List<T> menuItems) {
        this.position = 0;
        this.menuItems = menuItems;
    }

    public T getSelected() {
        return menuItems.get(position);
    }

    public int getPosition() {
        return this.position;
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

    public List<T> getGameMenuItems() {
        return menuItems;
    }

    private int menuSize() {
        return menuItems.size();
    }
}
