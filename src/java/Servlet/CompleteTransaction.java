/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.QueryStringSigningStrategy;

/**
 *
 * @author aHLai
 */
@WebServlet(name = "CompleteTransaction", urlPatterns = {"/CompleteTransaction"})
public class CompleteTransaction extends HttpServlet {

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
        
        
        try {
            
            String returnUrl = request.getParameter("returnUrl");
            String successString = "&success=true";
            String accountIdentifier = "&accountIdentifier=new-account-identifier";
            String message = "&message=Account Creation successful";
            
            
            
            OAuthConsumer consumer = new DefaultOAuthConsumer("test6-40942", "oQ4q4oBiv9y4jPr7");
            consumer.setSigningStrategy( new QueryStringSigningStrategy());
            String url = returnUrl + message + successString + accountIdentifier;
            String signedUrl = consumer.sign(url);
            
            response.sendRedirect(signedUrl);
            
        } 
        catch (OAuthMessageSignerException ex) 
        {
            Logger.getLogger(CompleteTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (OAuthExpectationFailedException ex) 
        {
            Logger.getLogger(CompleteTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (OAuthCommunicationException ex) 
        {
            Logger.getLogger(CompleteTransaction.class.getName()).log(Level.SEVERE, null, ex);
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
