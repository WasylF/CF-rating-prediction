package com.wslfinc.servlets;

import com.wslfinc.cf.RatingGetter;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wsl_F
 */
public class RemoveFromCacheServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String result;
        try {
            int contestId = Integer.valueOf(request.getParameter("contestId"));
            RatingGetter.removeFromCache(contestId);
            result = "done";
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
