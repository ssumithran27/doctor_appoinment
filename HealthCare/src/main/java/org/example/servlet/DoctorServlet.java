package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.DoctorDAO;
import org.example.model.Doctor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/doctor")
public class DoctorServlet extends HttpServlet {

    private static final Logger logger= Logger.getLogger(DoctorServlet.class.getName());
    private final DoctorDAO dd = new DoctorDAO();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("DoctorServlet-doPost Started");
        try {

            String name=request.getParameter("name");
            String specialization=request.getParameter("specialization");
            String availability=request.getParameter("availability");
            if((name==null|| name.trim().isEmpty()) || (specialization==null ||specialization.trim().isEmpty()) ||(availability==null ||availability.trim().isEmpty()))
            {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"All fields are required");
                return;
            }
            Doctor d=new Doctor();
            d.setName(name);
            d.setSpecialization(specialization);
            d.setAvailability(availability);
            dd.create(d);
            logger.info("doctor saved successfully:"+ d.getName());
            response.sendRedirect("doctor.html");
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE,"Error saving doctor:",e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("DoctorServlet-doGet started");
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h2> Doctor List </h2>");
            dd.findAll().forEach(Doctor -> {
                out.println(
                        Doctor.getDoctorId()+"|"+Doctor.getName() + "|" + Doctor.getSpecialization() + "|" + Doctor.getAvailability()

                );
            });
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        logger.info("DoctorServlet-doPut started");
        ObjectMapper mapper=new ObjectMapper();
        Doctor d=mapper.readValue(request.getInputStream(), Doctor.class);
        try
        {
            boolean updated=dd.update(d);

            response.setContentType("application/json");

            if (updated)
            {
                response.getWriter().write("{message:Doctor updated successfully}");
            }else{
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{message:Doctor not found}");
            }
        } catch(Exception e)
        {
            logger.severe(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public void doDelete(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        logger.info("DoctorServlet-doDelete started");
        int id= Integer.parseInt(request.getParameter("id"));
        try
        {
            boolean deleted=dd.delete(id);
            response.setContentType("application/json");
            if(deleted) {
                response.getWriter().write("{message:Appointment deleted successfully}");
            }
            else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
