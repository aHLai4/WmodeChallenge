/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package response;

import java.net.HttpURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 *
 * @author aHLai
 */
public class GetResponse {
    
    public GetResponse()
    {
    }
    
    @GET
    @Produces({"application/json", "application/xml"})
    public int validateUrl(String url) 
    {
        if(url.equals("valid"))
        {
            return HttpURLConnection.HTTP_ACCEPTED;
        }
        else
        {
            return HttpURLConnection.HTTP_FORBIDDEN;
        }
        
        
    }
    
}
