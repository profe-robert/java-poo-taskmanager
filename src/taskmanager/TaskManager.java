package taskmanager;

// import a dependencia de java swing
import javax.swing.SwingUtilities;

// import a vista en que se inicia el proyecto
import views.TaskView;

public class TaskManager {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskView().setVisible(true));
    }
    
}
