package org.example;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{
    private static final String url="jdbc:postgresql://localhost:5432/hospital_appoinment";
    private static final String user="postgres";
    private static final String password="test123";
    static
    {
        try
        {
            Class.forName("org.postgresql.Driver");

        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws Exception
    {
        return
                DriverManager.getConnection(url,user,password);
    }
}
