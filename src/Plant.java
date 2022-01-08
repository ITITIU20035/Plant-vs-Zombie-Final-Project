import java.awt.Rectangle;

public abstract class Plant {

    public int health = 200;

    public int x;
    public int y;
    public int posX;
    protected GameBG gp;
    public int myLane;


    public Plant(GameBG parent,int x,int y){
        this.x = x;
        this.y = y;
        gp = parent;
    }
    public void advance(){
        Rectangle pRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gp.laneZombies.get(myLane).size(); i++) {
            Zombie z = gp.laneZombies.get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            if(pRect.intersects(zRect)){
                z.health -= 300;
                boolean exit = false;
                if(z.health < 0){
                    System.out.println("ZOMBIE DIE");
                    gp.laneZombies.get(myLane).remove(i);
                    Progress.setProgress(10);
                    exit = true;
                }
                gp.lanePeas.get(myLane).remove(this);
                if(exit) break;
            }
        }
        posX += 15;
    }

    public void stop(){
    	
    }

}
