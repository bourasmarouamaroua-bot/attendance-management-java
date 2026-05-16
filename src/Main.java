import gui.LoginPage;
import service.AttendanceManager;
import service.AuthenticationService;
import utils.MockData;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {


            AttendanceManager attendanceManager = new AttendanceManager();
            AuthenticationService authService = new AuthenticationService();

            MockData.loadMockData(attendanceManager, authService);

            new LoginPage(attendanceManager, authService);
        });
    }
}