package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

/**
 * Created by gis on 10.12.2016.
 */
public class DbConnectionTest {

    @Test
    public void testDbConnection() {
        Connection conn = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?" +
                            "user=root&password=&serverTimezone=UTC");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
            Groups groups = new Groups();
            while (result.next()) {
                groups.add(new GroupData()
                        .withId(result.getInt("group_id"))
                        .withName(result.getString("group_name"))
                        .withHeader(result.getString("group_header"))
                        .withFooter(result.getString("group_footer")));
            }

            result.close();
            statement.close();
            conn.close();

            System.out.println("sssssss " + groups);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
