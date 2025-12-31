//package org.example.servlet;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.dao.AppointmentDAO;
//import org.example.model.Appointment;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Date;
//import java.util.logging.Logger;
//
//@WebServlet("/appointment")
//public class AppointmentServlet extends HttpServlet {
//    private static final Logger logger= Logger.getLogger(AppointmentServlet.class.getName());
//    private final AppointmentDAO ad = new AppointmentDAO();
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        logger.info("appointmentServlet-doPost Started");
//        try {
//            Appointment a=new Appointment();
//            String patientId= request.getParameter("patientId");
//            String doctorId=request.getParameter("doctorId");
//            String appointmentDate=request.getParameter("appointmentDate");
//            String appointmentTime=request.getParameter("appointmentTime");
//
//            if((patientId==null || patientId.isEmpty()) || (doctorId==null ||doctorId.isEmpty()) ||(appointmentDate==null || appointmentDate.isEmpty())
//
//                    || (appointmentTime==null || appointmentTime.isEmpty()))
//            {
//                logger.warning("Missing form data ....");
//               response.getWriter().println("incomplete form submission");
//               return;
//
//            }
//            a.setPatientId(Integer.parseInt(patientId));
//            a.setDoctorId(Integer.parseInt(doctorId));
//            a.setAppointmentDate(Date.valueOf(appointmentDate));
//            a.setAppointmentTime(appointmentTime);
//
//            ad.create(a);
//            logger.info("appointment saved successfully:"+ a.getAppointmentId());
//            response.sendRedirect("appointment.html");
//        } catch (Exception e) {
//            logger.severe("Error saving appointment:"+e.getMessage());
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//
//
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
//    {
//       logger.info("AppointmentServlet-doGet started");
//       response.setContentType("text/html");
//       try(PrintWriter out=response.getWriter())
//       {
//           out.println("<h2> Appointment List </h2>");
//           ad.findAll().forEach(Appointment->{
//               out.println(
//                   Appointment.getAppointmentId()+"|" +Appointment.getPatientId()+"|"+Appointment.getDoctorId()+"|"
//                   +Appointment.getAppointmentDate()+"|"+Appointment.getAppointmentTime()
//               );
//           });
//       }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request,HttpServletResponse response) throws IOException
//    {
//        logger.info("AppointmentServlet-doPut started");
//        ObjectMapper mapper=new ObjectMapper();
//        Appointment a=mapper.readValue(request.getInputStream(),Appointment.class);
//        try
//        {
//            boolean updated=ad.update(a);
//
//            response.setContentType("application/json");
//
//            if (updated)
//            {
//                response.getWriter().write("{message:Appointment updated successfully}");
//            }else{
//                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//                response.getWriter().write("{message:Appointment not found}");
//            }
//        } catch(Exception e)
//        {
//            logger.severe("Error While Updating Appointment:"+e.getMessage());
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }
//    @Override
//    public void doDelete(HttpServletRequest request,HttpServletResponse response) throws IOException
//    {
//        logger.info("AppointmentServlet-doDelete started");
//        int id= Integer.parseInt(request.getParameter("id"));
//        try
//        {
//            boolean deleted=ad.delete(id);
//            response.setContentType("application/json");
//            if(deleted) {
//                response.getWriter().write("{message:Appointment deleted successfully}");
//            }
//            else{
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            }
//        } catch (Exception e) {
//            logger.severe(e.getMessage());
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//}
