package org.example.dao;

import org.example.DBConnection;
import org.example.model.Appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO
{
    public void create(Appointment a) throws Exception
    {
        String sql= """
                INSERT INTO appoinment (patient_id,doctor_id,appointment_date,
                appointment_time) VALUES (?,?,?,?)
                """;
        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps= con.prepareStatement(sql))
        {
            ps.setInt(1,a.getPatientId());
            ps.setInt(2,a.getDoctorId());
            ps.setDate(3,(Date) a.getAppointmentDate());
            ps.setString(4,a.getAppointmentTime());
            ps.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> findAll()
    {
        List<Appointment> appointments=new ArrayList<>();
        String sql="""
                  select appointment_id,patient_id,doctor_id,appointment_date,appointment_time from appointment
                  """;
        try(Connection con =DBConnection.getConnection();
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery())
        {
            while (rs.next()){
                Appointment a=new Appointment();
                a.setAppointmentId(rs.getInt("appointmentId"));
                a.setPatientId(rs.getInt("patientId"));
                a.setDoctorId(rs.getInt("doctorId"));
                a.setAppointmentDate(rs.getDate("appointmentDate"));
                a.setAppointmentTime(rs.getString("appointmentTime"));
                appointments.add(a);
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("error fetching patients",e);
        }
        return appointments;
    }

    public boolean update(Appointment a) throws Exception
    {
        String sql= """
         update patient set patient_id=?,doctor_id=?,appointment_date=?,appointment_time=?,status=?where appointment_id=?""";

        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,a.getPatientId());
            ps.setInt(2,a.getDoctorId());
            ps.setDate(3,a.getAppointmentDate());
            ps.setString (4,a.getAppointmentTime());
            ps.setInt(5,a.getAppointmentId());
           return ps.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) throws Exception
    {
        String sql="""
                    delete from appointment where appointment_id=?
                    """;

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
