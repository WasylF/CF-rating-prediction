<%-- 
    Document   : contestSelector
    Created on : Feb 13, 2017, 11:45:40 AM
    Author     : Wsl_F
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="com.wslfinc.cf.JsonReader"%>
<%@page import="org.json.JSONObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contest selector</title>
    </head>
    <body>
        <h1>Select contest name to view rating prediction</h1>
        <%
            String url = "https://codeforcescontests.github.io/RatingAfterRounds/contests/contestNames.html";
            JSONObject contestNames = JsonReader.read(url);
            JSONArray contests = contestNames.getJSONArray("result");
            int number = contests.length();
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
