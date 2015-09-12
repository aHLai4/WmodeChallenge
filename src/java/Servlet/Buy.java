/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author aHLai
 */
@WebServlet(name = "Buy", urlPatterns = {"/Buy"})
public class Buy extends HttpServlet {

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
            String oauth_consumer_key = request.getParameter("oauth_consumer_key");
            String oauth_nonce = request.getParameter("oauth_nonce");
            String oauth_signature = request.getParameter("oauth_signature");
            String oauth_signature_method = request.getParameter("oauth_signature_method");
            String oauth_timestamp = request.getParameter("oauth_timestamp");
            String oauth_version = request.getParameter("oauth_version");
            
            String eventUrl = request.getParameter("eventUrl");
            
            OAuthConsumer consumer = new DefaultOAuthConsumer("test6-40942", "oQ4q4oBiv9y4jPr7");
            URL url = new URL(eventUrl);
            HttpURLConnection requestUrl = (HttpURLConnection) url.openConnection();
            consumer.sign(requestUrl);
            requestUrl.connect();
            
            String result = null;
            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(requestUrl.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) 
            {
                sb.append(inputLine);
            }
            
            result = sb.toString();
            
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(result));

            Document doc = builder.parse(src);
            String creatorName = doc.getElementsByTagName("fullName").item(0).getTextContent();
            String companyName = doc.getElementsByTagName("name").item(0).getTextContent();
            String edition = doc.getElementsByTagName("editionCode").item(0).getTextContent();
            String returnUrl = doc.getElementsByTagName("returnUrl").item(0).getTextContent();
            
            System.out.print(creatorName);
            System.out.print(companyName);
            System.out.print(edition);
            System.out.print(returnUrl);
            
            response.sendRedirect("displayinfo.jsp?fullName=" + creatorName + "&companyName=" + companyName + "&edition=" + edition + "&returnUrl=" + returnUrl);
           
        } catch (OAuthMessageSignerException ex) {
            Logger.getLogger(Buy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthExpectationFailedException ex) {
            Logger.getLogger(Buy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthCommunicationException ex) {
            Logger.getLogger(Buy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Buy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Buy.class.getName()).log(Level.SEVERE, null, ex);
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
