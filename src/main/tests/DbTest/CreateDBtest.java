package DbTest;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CreateDBtest {
    private static CreateDB createDB;

    @BeforeAll
    public static void setUp() {
        createDB = new CreateDB();
    }

//    @AfterAll
//    public static void tearDown() {
//        // delete test data
//        try {
//            createDB.executeUpdate("TRUNCATE TABLE users CASCADE;");
//            createDB.executeUpdate("TRUNCATE TABLE product_category CASCADE;");
//            createDB.executeUpdate("TRUNCATE TABLE products CASCADE;");
//            createDB.executeUpdate("TRUNCATE TABLE users CASCADE;");
//        } catch (SQLException e) {
//            System.err.println("ERROR: Data cleanup failed.");
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testSecondColumnOfProductCategoryNotNull(){
        String expected = "Motoric Organ";

        Throwable exception = assertThrows(SQLException.class, () -> createDB.executeUpdate(
                "SELECT name FROM product_category LIMIT 1;"
        ));

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }
}
