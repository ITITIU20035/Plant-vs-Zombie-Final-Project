import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
public class GameBG extends JLayeredPane implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	Image bgImage;
    Image peashooterImage;
    Image freezePeashooterImage;
    Image sunflowerImage;
    Image peaImage;
    Image freezePeaImage;
    Image wallnutImage;

    Image normalZombieImage;
    Image TrafficConeZombieImage;
    Image FootballzombieImage;
    Collider[] colliders;
    
    Integer integer = Integer.valueOf(0);
    Integer integera = Integer.valueOf(1);
    
    ArrayList<ArrayList<Zombie>> laneZombies;
    ArrayList<ArrayList<PeaAmmo>> lanePeas;
    ArrayList<Sun> activeSuns;

    Timer redrawTimer;
    Timer advancerTimer;
    Timer sunProducer;
    Timer zombieProducer;
    JLabel sunScoreboard;

    Game.PlantType activePlantingBrush = Game.PlantType.None;

    int mouseX , mouseY;

    private static int sunScore;

    public static int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        GameBG.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
    }

    public GameBG(JLabel sunScoreboard){
        setSize(1000,752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);

        bgImage  = new ImageIcon(this.getClass().getResource("resource/mainBG.png")).getImage();

        peashooterImage = new ImageIcon(this.getClass().getResource("resource/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("resource/plants/freezepeashooter.gif")).getImage();
        sunflowerImage = new ImageIcon(this.getClass().getResource("resource/plants/sunflower.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("resource/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("resource/freezepea.png")).getImage();
        wallnutImage = new ImageIcon(this.getClass().getResource("resource/plants/wallnut.gif")).getImage();
        
        normalZombieImage = new ImageIcon(this.getClass().getResource("resource/zombies/normalzombie.gif")).getImage();
        TrafficConeZombieImage = new ImageIcon(this.getClass().getResource("resource/zombies/coneheadzombie.gif")).getImage();
        FootballzombieImage = new ImageIcon(this.getClass().getResource("resource/zombies/footballzombie.gif")).getImage();
        
        laneZombies = new ArrayList<>();
        lanePeas = new ArrayList<>();
        for(int i = 0; i <=4 ; i++) {
        lanePeas.add(new ArrayList<>()); //lane 1 to 5
        laneZombies.add(new ArrayList<>()); //lane 1 to 5
        }
        
        
        Integer integer = Integer.valueOf(0);
        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i%9)*100,109 + (i/9)*120);
            a.setAction(new PlantActionListener((i%9),(i/9)));
            colliders[i] = a;
            add(a,integer);
        }
        activeSuns = new ArrayList<>();

        redrawTimer = new Timer(25,(ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60,(ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(4500,(ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this,rnd.nextInt(800)+100,0,rnd.nextInt(300)+200);
            activeSuns.add(sta);
            add(sta,integera);
        });
        sunProducer.start();

        zombieProducer = new Timer(10000,(ActionEvent e) -> {
            Random rnd = new Random();
            Level lvl = new Level();
            String [] Level = lvl.ZombieType[lvl.level-1];
            int [][] LevelValue = lvl.LevelValue[lvl.level-1];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(100);
            Zombie z = null;
            for(int i = 0;i<LevelValue.length;i++) {
                if(t >= LevelValue[i][0] && t<=LevelValue[i][1]) {
                    z = Zombie.getZombie(Level[i],GameBG.this,l);
                }
            }
            laneZombies.get(l).add(z);
        });
        zombieProducer.start();
    }
    private void advance(){
        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                z.move();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                PeaAmmo p = lanePeas.get(i).get(j);
                p.intersect();
            }
        }
        for (int i = 0; i < activeSuns.size() ; i++) {
            activeSuns.get(i).drop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,null);

        //Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if(c.indexPlant != null){
                Plant p = c.indexPlant;
                if(p instanceof Pea){
                    g.drawImage(peashooterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof FrozenPea){
                    g.drawImage(freezePeashooterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof SunFlower){
                    g.drawImage(sunflowerImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Wallnut) {
                	g.drawImage(wallnutImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
            }
        }
        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                if(z instanceof NormalZombie){
                    g.drawImage(normalZombieImage,z.posX,109+(i*120),null);
                }else if(z instanceof TrafficConeZombie){
                    g.drawImage(TrafficConeZombieImage,z.posX,109+(i*120),null);
                }else if(z instanceof FootballZombie) {
                	g.drawImage(FootballzombieImage,z.posX,109+(i*120),null);
                }
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                PeaAmmo p = lanePeas.get(i).get(j);
                if(p instanceof slowAmmo){
                    g.drawImage(freezePeaImage, p.posX, 130 + (i * 120), null);
                }else {
                    g.drawImage(peaImage, p.posX, 130 + (i * 120), null);
                }
            }
        }
    }
    public class PlantActionListener implements ActionListener {

        int x,y;

        public PlantActionListener(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void actionPerformed(ActionEvent e) {
            if(activePlantingBrush == Game.PlantType.Sunflower){
                if(getSunScore()>=50) {
                    colliders[x + y * 9].setPlant(new SunFlower(GameBG.this, x, y));
                    setSunScore(getSunScore()-50);
                }
            }
            if(activePlantingBrush == Game.PlantType.PeaPlant){
                if(getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Pea(GameBG.this, x, y));
                    setSunScore(getSunScore()-100);
                }
            }

            if(activePlantingBrush == Game.PlantType.FreezePlant){
                if(getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FrozenPea(GameBG.this, x, y));
                    setSunScore(getSunScore()-175);
                }
            }
            if(activePlantingBrush == Game.PlantType.Wallnut) {
            	if(getSunScore()>= 50) {
            		colliders[x+y*9].setPlant(new Wallnut(GameBG.this,x,y));
            		setSunScore(getSunScore()-50);
            	}
            }
            activePlantingBrush = Game.PlantType.None;
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}