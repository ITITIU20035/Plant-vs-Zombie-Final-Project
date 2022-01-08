import java.awt.event.ActionEvent;
import javax.swing.*;

public class Pea extends Plant {

    public Timer shootTimer;


    public Pea(GameBG instance,int x,int y) {
        super(instance,x,y);
        shootTimer = new Timer(2000,(ActionEvent e) -> {
            //System.out.println("SHOOT");
            if(gp.laneZombies.get(y).size() > 0) {
                gp.lanePeas.get(y).add(new PeaAmmo(gp, y, 103 + this.x * 100));
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop(){
        shootTimer.stop();
    }

}

