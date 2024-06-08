package org.example.lab6_20216256.daos;

import org.example.lab6_20216256.beans.Employees;
import org.example.lab6_20216256.beans.Jobs;

import java.sql.*;
import java.util.ArrayList;

public class DaoJobs {
    //-----------------------------------------------------------------------------------------------------------
    public ArrayList<Jobs> listarJobs() {

        ArrayList<Jobs> listJob = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";

        // Definimos el query mediante el cual obtendremos los datos que necesitamos de la base de datos
        String query = "SELECT * FROM jobs";

        try (Connection conn = DriverManager.getConnection(url, username, password);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            //Rellenamos la lista con las películas mientras .next no se quede sin filas y retorne false
            while (rs.next()) {

                Jobs job = new Jobs();

                job.setJobId(rs.getInt(1));
                job.setJobTitle(rs.getString(2));
                job.setMinSalary(rs.getInt(3));
                job.setMaxSalary(rs.getInt(4));

                listJob.add(job);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listJob;
    }
    //-----------------------------------------------------------------------------------------------------------
    public Jobs getJobId(int id) {

        Jobs job = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";

        String query = "SELECT * FROM jobs WHERE job_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                job = new Jobs();
                job.setJobId(rs.getInt("job_id"));
                job.setJobTitle(rs.getString("job_title"));
                job.setMinSalary(rs.getInt("min_salary"));
                job.setMaxSalary(rs.getInt("max_salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }
    //-----------------------------------------------------------------------------------------------------------
    public String returnJob(String id){
        String name="";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "PoCoYo137F";

        String query = "SELECT job_title FROM jobs WHERE job_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("job_title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
