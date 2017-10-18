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
public class GetNextRatingServlet extends HttpServlet {

    volatile private static int requestCount = 0;
    private static volatile long time1000 = 0;
    private static final int PART_COUNT = 100;

    /*private void incReqCount() {
        int rCount = 0;
        synchronized(requestCount) {
            rCount = requestCount;
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        requestCount++;
        if (requestCount % PART_COUNT == 0) {
            long deltaT = System.currentTimeMillis() - time1000;
            time1000 = System.currentTimeMillis();
            System.out.println("Request count: " + requestCount
                    + " last " + PART_COUNT + " for " + deltaT + " milliseconds");
        }
        //System.out.println("Request received!");
        //System.out.println("Parameter: " + request.getParameter("contestId"));
        String json;
        try {
            int contestId = Integer.valueOf(request.getParameter("contestId"));
            json = RatingGetter.getNewRatingString(contestId);
        } catch (NumberFormatException ex) {
            json = "{ \"status\": \"FAIL\" }";
        }

        try {
            PrintWriter out = response.getWriter();
            out.write(json);
            out.flush();
            response.setStatus(200);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

}
