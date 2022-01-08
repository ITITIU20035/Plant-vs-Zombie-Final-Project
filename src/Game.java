import java.awt.event.ActionEvent;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Game extends JFrame {
	public enum PlantType{
        None,
        Sunflower,
        PeaPlant,
        FreezePlant,
        Wallnut
    }
	Integer integera = Integer.valueOf(0);
	Integer integerb = Integer.valueOf(2);
	Integer integerc = Integer.valueOf(3);
    public Game(){
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37,80);
        sun.setSize(60,20);

        GameBG gp = new GameBG(sun);
        gp.setLocation(0,0);
        getLayeredPane().add(gp,integera);
        
        PlantCard sunflower = new PlantCard(new ImageIcon(this.getClass().getResource("resource/cards/card_sunflower.png")).getImage());
        sunflower.setLocation(110,8);
        sunflower.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Sunflower;
        });
        getLayeredPane().add(sunflower,integerc);

        PlantCard peashooter = new PlantCard(new ImageIcon(this.getClass().getResource("resource/cards/card_peashooter.png")).getImage());
        peashooter.setLocation(175,8);
        peashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.PeaPlant;
        });
        getLayeredPane().add(peashooter,integerc);

        PlantCard freezepeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("resource/cards/card_freezepeashooter.png")).getImage());
        freezepeashooter.setLocation(240,8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.FreezePlant;
        });
        getLayeredPane().add(freezepeashooter,integerc);
        
        PlantCard wallnut = new PlantCard(new ImageIcon(this.getClass().getResource("resource/cards/card_wallnut.png")).getImage());
        wallnut.setLocation(305,8);
        wallnut.setAction((ActionEvent e) ->{
        	gp.activePlantingBrush = PlantType.Wallnut;
        });
        getLayeredPane().add(wallnut,integerc);


        getLayeredPane().add(sun,integerb);
        setResizable(false);
        setVisible(true);
    }
    public Game(boolean b) {
    	Menu menu = new Menu();
        menu.setLocation(0,0);
        setSize(620,465);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu,integerb);
        menu.repaint();
        setResizable(false);
        setVisible(true);
        File MenuAudio = new File("src\\Audio\\menu.wav");
        try {
        	Clip clip = AudioSystem.getClip();
        	clip.open(AudioSystem.getAudioInputStream(MenuAudio));
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        	Thread.sleep(10000);
        	clip.start();
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
        	
    static Game gw;
    public static void cleargame() {
    	System.exit(0);
    	begin();
    	
    }
    public static void begin() {
       gw = new Game();
    }
    public static void main(String[] args) {
          gw = new Game(true);

    }
}
