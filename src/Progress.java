import javax.swing.JOptionPane;

public class Progress {
	static int progress = 0;
    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
        if(progress>=150) {
           if(Level.level <=3) {
            JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "Starting next Level");
            Game.cleargame();
            Level.level++;
            Game.begin();
            }  else if(Level.level == 3) {
               JOptionPane.showMessageDialog(null,"Level Completed !!!" + "\n" + "The end of the demo");
               Level.level = 2;
               System.exit(0);
           }
           progress = 0;
        }
    }
}
