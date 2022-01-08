import javax.swing.*;
import java.awt.event.ActionEvent;

public class SunFlower extends Plant {
	Integer integer = Integer.valueOf(1);
    Timer sunProduceTimer;

    public SunFlower(GameBG parent,int x,int y) {
        super(parent, x, y);
        sunProduceTimer = new Timer(15000,(ActionEvent e) -> {
            Sun sta = new Sun(gp,60 + x*100,110 + y*120,130 + y*120);
            gp.activeSuns.add(sta);
            gp.add(sta,integer);
        });
        sunProduceTimer.start();
    }

}
