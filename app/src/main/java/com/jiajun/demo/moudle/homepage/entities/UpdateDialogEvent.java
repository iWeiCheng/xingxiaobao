package com.jiajun.demo.moudle.homepage.entities;

/**
 * Created by dan on 2017/11/28/028.
 */

public class UpdateDialogEvent {
   private int position;

    public UpdateDialogEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
