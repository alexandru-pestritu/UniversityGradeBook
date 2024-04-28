import com.catalog.io.FileHandler;
import com.catalog.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        FileHandler fileHandler = new FileHandler();
        fileHandler.readDataFromCSVFiles();
        mainMenu.displayMenu();
        fileHandler.writeDataToCSVFiles();
    }
}