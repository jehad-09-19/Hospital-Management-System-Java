import gui.LoginFrame;
import service.HospitalManager;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[]a){
        SwingUtilities.invokeLater(()->new LoginFrame(new HospitalManager()));
    }
}
