package com.wslfinc.servlets;

import com.wslfinc.cf.RatingGetter;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wsl_F
 */
public class GetCachedIdsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            List<Integer> ids = RatingGetter.getCachedIds();
            out.write("There are " + ids.size() + " cached contests: \n");
            for (int id : ids) {
                out.write(id + "\n");
            }
            out.flush();
            response.setStatus(200);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

}
