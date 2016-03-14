package forms;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Date;
import java.util.TimerTask;

public class UpdateTime extends TimerTask {
    private long elapsedTime;
    private long startTime = new Date().getTime();
    Thread t;
    Label l;

    UpdateTime(Label l) {
        this.l = l;
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                l.setText("dfasd");
            }
        });
    }
}
