package com.wslfinc.servlets;

import com.wslfinc.cf.sdk.CodeForcesSDK;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wsl_F
 */
public class ChangeTimeToUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String result;
        try {
            int timeToUpdate = Integer.valueOf(request.getParameter("timeToUpdate"));
            CodeForcesSDK.changeTimeToUodate(timeToUpdate);
            result = "Updating time changed to " + timeToUpdate + " milliseconds";
        } catch (Exception ex) {
            result = "exception caused";
        }

        try {
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            response.setStatus(200);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

}
