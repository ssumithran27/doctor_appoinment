package org.example.dao;
import org.example.DBConnection;
import org.example.model.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO
{
    public void create(Payment p) throws Exception
    {
        String sql= """
                INSERT INTO payment (appointment_id,amount,payment_type,payment_status)
                VALUES(?,?,?,?)
                """;
        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt(1,p.getAppointmentId());
            ps.setDouble(2,p.getAmount());
            ps.setString(3,p.getPaymentType());
            ps.setString(4,p.getPaymentStatus());
            ps.executeUpdate();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Payment> findAll()
    {
        List<Payment> payments=new ArrayList<>();
        String sql="""
                  select payment_id,appointment_id,amount,payment_type,payment_status from payment
                  """;
        try(Connection con =DBConnection.getConnection();
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery())
        {
            while (rs.next()){
                Payment py=new Payment();
                py.setPaymentId(rs.getInt("paymentId"));
                py.setAppointmentId(rs.getInt("appointmentId"));
                py.setAmount(rs.getDouble("amount"));
                py.setPaymentType(rs.getString("paymentType"));
                py.setPaymentStatus(rs.getString("paymentStatus"));
               payments.add(py);
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("error fetching patients",e);
        }
        return payments;
    }
    public boolean update(Payment py) throws Exception
    {
        String sql= """
    update payment set appointment_id=?,amount=?,payment_type=?,payment_status=? where payment_id=?""";

        try(Connection con= DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql))
        {
            ps.setInt (1,py.getAppointmentId());
            ps.setDouble(2,py.getAmount());
            ps.setString(3,py.getPaymentType());
            ps.setString (4,py.getPaymentStatus());
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) throws Exception
    {
        String sql="delete from payment where payment_id=?";

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
