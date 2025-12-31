//package org.example.servlet;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.dao.PaymentDAO;
//import org.example.model.Payment;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.logging.Logger;
//
//@WebServlet("/payment")
//public class PaymentServlet extends HttpServlet {
//    private static final Logger logger= Logger.getLogger(org.example.servlet.PaymentServlet.class.getName());
//    private final PaymentDAO pyd = new PaymentDAO();
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        logger.info("PaymentServlet-doPost Started");
//        try {
//            Payment py=new Payment();
//            String appointmentId=request.getParameter("appointmentId");
//            String amount=request.getParameter("amount");
//            String paymentType=request.getParameter("paymentType");
//            String paymentStatus=request.getParameter("paymentStatus");
//
//
//            if((appointmentId==null || appointmentId.isEmpty()) ||(amount==null || amount.isEmpty()) || (paymentType==null ||paymentType.isEmpty()) ||
//                    (paymentStatus==null || paymentStatus.isEmpty()))
//            {
//                logger.info("Missing form data ....");
//                response.getWriter().println("incomplete form submission");
//
//            }
//            py.setAppointmentId(Integer.parseInt(appointmentId));
//            py.setAmount(Double.parseDouble(amount));
//            py.setPaymentType(paymentType);
//            py.setPaymentStatus(paymentStatus);
//            pyd.create(py);
//            logger.info("payment saved successfully:"+ py.getPaymentStatus());
//            response.sendRedirect("payment.html");
//        }
//        catch (Exception e)
//        {
//            logger.severe("Error saving payment:"+e.getMessage());
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//
//
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
//    {
//        logger.info("PaymentServlet-doGet started");
//        response.setContentType("text/html");
//        try(PrintWriter out=response.getWriter())
//        {
//            out.println("<h2> Payment List </h2>");
//            pyd.findAll().forEach(Payment->{
//                out.println(
//                        Payment.getPaymentId()+"|"+Payment.getAppointmentId()+"|"+Payment.getAmount()+"|"+Payment.getPaymentType()+"|"+Payment.getPaymentStatus()
//
//                );
//            });
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest request,HttpServletResponse response) throws IOException
//    {
//        logger.info("PaymentServlet-doPut started");
//        ObjectMapper mapper=new ObjectMapper();
//        Payment py=mapper.readValue(request.getInputStream(),Payment.class);
//        try
//        {
//            boolean updated=pyd.update(py);
//
//            response.setContentType("application/json");
//
//            if (updated)
//            {
//                response.getWriter().write("{message:Payment updated successfully}");
//            }else{
//                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//                response.getWriter().write("{message:Payment not found}");
//            }
//        } catch(Exception e)
//        {
//            logger.severe(e.getMessage());
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }
//    @Override
//    public void doDelete(HttpServletRequest request,HttpServletResponse response) throws IOException
//    {
//        logger.info("PaymentServlet-doDelete started");
//        int id= Integer.parseInt(request.getParameter("id"));
//        try
//        {
//            boolean deleted=pyd.delete(id);
//            response.setContentType("application/json");
//            if(deleted) {
//                response.getWriter().write("{message:patient deleted successfully}");
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
//}
