package com.tecdatum.iaca_tspolice.Helper;

public class StateVO {
    private String ID,title;
    private boolean selected;

    public StateVO(String ID, String title) {
        this.ID = ID;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}