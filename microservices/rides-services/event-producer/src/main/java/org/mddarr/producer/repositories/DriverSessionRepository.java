package org.mddarr.producer.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

public class DriverSessionRepository {
    public static String insertDrivingSession(String driverID, Integer average_session_length) {

        Statement stmt = null;
        Connection c = null;
        String session_id;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");

            session_id = UUID.randomUUID().toString();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO driving_session (\"session_id\",\"driverid\", \"session_length\",\"session_status\") "
                    + "VALUES ('%s', '%s', '%d','%s' );",session_id, driverID, average_session_length, "MATCHING");
            stmt.executeUpdate(sql);
            c.close();
            return session_id;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return "-1";
        }
    }

    public static String insertSession(String session_id, String driverID, Integer session_length, Integer session_start, Integer session_end) {

        Statement stmt = null;
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");

            stmt = c.createStatement();
            String sql = String.format("INSERT INTO sessions (\"session_id\",\"driverid\", \"session_length\",\"session_start\", \"session_end\") "
                    + "VALUES ('%s', '%s', '%d','%d', %d );",session_id, driverID, session_length, session_start, session_end);
            stmt.executeUpdate(sql);
            c.close();
            return session_id;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return "-1";
        }
    }
}
