/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;

/**
 *
 * @author aHLai
 */
@WebServlet(name = "Validation", urlPatterns = {"/Validation"})
public class Validation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try 
        {
            HttpSession session = request.getSession();
            
            if(session.getAttribute("consumerManager")==null)
            {
                ConsumerManager newManager = new ConsumerManager();
                newManager.setAssociations(new InMemoryConsumerAssociationStore());
                newManager.setNonceVerifier(new InMemoryNonceVerifier(5000));
                session.setAttribute("consumerManager", newManager);
            }
            
            ConsumerManager manager = (ConsumerManager) session.getAttribute("consumerManager");
            String userOpenID = request.getParameter("openIDName");
            
            List discoveries = manager.discover(userOpenID);
            
            DiscoveryInformation discovered = manager.associate(discoveries);
            session.setAttribute("openID-discovery", discovered);
            
            String returnToUrl = "http://localhost:8080/WmodeBackEndChallenge/ConsumerReturnUrl";
            
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
            
            response.sendRedirect(authReq.getDestinationUrl(true));
            
        } 
        catch (DiscoveryException ex) 
        {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (MessageException ex) 
        {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ConsumerException ex) 
        {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
