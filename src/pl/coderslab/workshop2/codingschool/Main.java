package pl.coderslab.workshop2.codingschool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.workshop2.codingschool.model.*;

public class Main {
    private static String dbUsername = "root";
    private static String dbPassword = "coderslab";
    private static String dbName = "coding_school";

    public static void main(String[] args) {


        try (Connection conn = ConnectionFactory.getConnection(dbUsername, dbPassword, dbName)) {

//            User user = new User("Krystian", "krys@gmail.com", "haslomaslo");
//            user.saveToDB(conn);

//            User user1 = User.loadUserById(conn, 1);
//            System.out.println(user1);

            User newUser = new User("Krzychu", "krzych@gmail.com", "haslohaslo123");
            newUser.saveToDB(conn);


            ArrayList<User> users = User.loadAllUsers(conn);
            for (User user : users) {
                System.out.println(user);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
