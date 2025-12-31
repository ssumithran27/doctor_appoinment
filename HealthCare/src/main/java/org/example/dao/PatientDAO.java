package org.example.dao;

import org.example.DBConnection;
import org.example.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    public void create (Patient p)
    {
        String sql= """
                      
                INSERT INTO patient (name,age,gender,date_of_birth,phone,city,blood_group)
               values (?,?,?,?,?,?,?)
                      """;
        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps =con.prepareStatement(sql))
        {
            ps.setString (1,p.getName());
            ps.setInt(2,p.getAge());
            ps.setString(3,p.getGender());
            ps.setDate(4, p.getDateOfBirth());
            ps.setString (5,p.getPhone());
            ps.setString(6,p.getCity());
            ps.setString(7,p.getBloodGroup());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error creating patients",e);
        }
    }
    public List<Patient> findAll()
    {
        List<Patient> patients =new ArrayList<>();
        String sql="""
                  select patient_id,name,age,gender,date_of_birth,phone,city,blood_group from patient
                  """;
        try(Connection con =DBConnection.getConnection();
        PreparedStatement ps =con.prepareStatement(sql);
        ResultSet rs=ps.executeQuery())
        {
            while (rs.next()){
                Patient p=new Patient();
                p.setPatientId(rs.getInt("patient_id"));
                p.setName(rs.getString("name"));
                p.setAge(rs.getInt("age"));
                p.setGender(rs.getString("gender"));
                p.setDateOfBirth(rs.getDate("date_of_birth"));
                p.setPhone(rs.getString("phone"));
                p.setCity(rs.getString("city"));
                p.setBloodGroup(rs.getString("blood_group"));
                patients.add(p);

            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error fetching patients",e);
        }
        return patients;
    }
    public boolean update(Patient p)
    {
        String sql= """
    UPDATE patient SET name=?,age=?,gender=?,date_of_birth=?,phone=?,city=?,blood_group=? where patient_id=?""";

        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {

            ps.setString (1,p.getName());
            ps.setInt(2,p.getAge());
            ps.setString(3,p.getGender());
            ps.setDate(4,(Date) p.getDateOfBirth());
            ps.setString (5,p.getPhone());
            ps.setString(6,p.getCity());
            ps.setString(7,p.getBloodGroup());
            ps.setInt(8,p.getPatientId());
            return ps.executeUpdate()>0;
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error updating patients",e);
        }
    }

    public boolean delete(int id) throws Exception
    {
        String sql="""
                    delete from patient where patient_id=?""";

        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error deleting patients",e);
        }

    }

}
