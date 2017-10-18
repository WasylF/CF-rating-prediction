package com.wslfinc.servlets;

import com.wslfinc.cf.sdk.CodeForcesSDK;
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
            List<Integer> ids = CodeForcesSDK.getCachedIds();
            out.write("Cached ids count: " + ids.size() + "\n");
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
