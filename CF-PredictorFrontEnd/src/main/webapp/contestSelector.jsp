<%-- 
    Document   : contestSelector
    Created on : Feb 13, 2017, 11:45:40 AM
    Author     : Wsl_F
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.wslfinc.cf.ContestProcessor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contest selector</title>
        <style>
            @media screen and (orientation: landscape) { 
                body { 
                    float: left;
                    background-size: cover;
                    background-image: url('images/alpinist_landscape.png');
                    background-position: center;
                    background-repeat: no-repeat;
                    background-size: cover;
                    height: 95vh;
                }
            }
            @media screen and (orientation: portrait) { 
                body {
                    float: left;
                    background-size: cover;
                    background-image: url('images/alpinist_portrait.png');
                    background-position: center;
                    background-repeat: no-repeat;
                    background-size: cover;
                    height: 95vh;
                }
            }
        </style>
    </head>
    <body>
        <h1>Select contest name to view rating prediction</h1>
        <%
            JSONArray contests = ContestProcessor.getNamesIds();
        %>
        <form name="contestSelection"action="${pageContext.request.contextPath}/roundResults.jsp" method="POST">
            <select name="contestId" width="30" size="1">
                <%
                    for (Object contest : contests) {
                        JSONObject c = (JSONObject) contest;
                        String name = c.getString("name");
                        if (!name.isEmpty()) {
                            int contestId = c.getInt("contestId");
                %>
                <option value=<%=contestId%>><%=name%></option>
                <%}
                    }%>
            </select>
            <br>
            <br>
            <input type="submit" value="Get rating prediction" name="sbm" />
        </form>

    </body>
</html>
