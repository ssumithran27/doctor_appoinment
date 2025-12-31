package org.example.dao;

import org.example.DBConnection;
import org.example.model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO
{
    public void create (Doctor d)throws Exception
    {
        String sql="""
                   INSERT INTO doctor(name,specialization,availability)
                    VALUES (?,?,?)""";

        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
           ps.setString(1,d.getName());
           ps.setString(2,d.getSpecialization());
           ps.setString(3,d.getAvailability());
           ps.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Doctor> findAll()
    {
        List<Doctor> doctors =new ArrayList<>();
        String sql="""
                  select doctor_id,name,specialization,availability from doctor
                  """;
        try(Connection con =DBConnection.getConnection();
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery())
        {
            while (rs.next()){
                Doctor d=new Doctor();
                d.setDoctorId((rs.getInt("doctor id")));
                d.setName(rs.getString("name"));
                d.setSpecialization(rs.getString("specialization"));
                d.setAvailability(rs.getString("availability"));
                doctors.add(d);

            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("error fetching doctors",e);
        }
        return doctors;
    }

    public boolean update(Doctor d) throws Exception
    {
        String sql= """
    update doctor set name=?,specialization=?,availability=? where doctor_id=?""";

        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setString (1,d.getName());
            ps.setString(2,d.getSpecialization());
            ps.setString(3,d.getAvailability());
            ps.setInt(4,d.getDoctorId());
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) throws Exception
    {
        String sql="""
                   delete from patient where doctor_id=?""";
        boolean deleted =false;
        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }

    }

}
