import java.awt.*;
public class slowAmmo extends PeaAmmo {
	
    public slowAmmo(GameBG parent,int lane,int startX){
        super(parent,lane,startX);
    }
    
    public void intersect(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gp.laneZombies.get(myLane).size(); i++) {
            Zombie z = gp.laneZombies.get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            if(pRect.intersects(zRect)){
                z.health -= 300;
                z.slow();
                boolean exit = false;
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");
                    Progress.setProgress(10);
                    gp.laneZombies.get(myLane).remove(i);
                    exit = true;
                }
                gp.lanePeas.get(myLane).remove(this);
                if(exit) break;
            }
        }
        posX += 15;
    }
}
