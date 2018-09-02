<%-- 
	Document   : contestSelector
	Created on : Feb 13, 2017, 11:45:40 AM
	Author     : Wsl_F
--%>

<%@page import="com.wslfinc.cf.ContestProcessor" %>
<%@page import="org.json.JSONArray" %>
<%@page import="org.json.JSONObject" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contest selector</title>

    <link rel="stylesheet" type="text/css" href="contestSelector.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
<div class="container">
    <header>
        <h1>CF-Predictor</h1>
    </header>

    <article>
        <h2>Select Contest:</h2>
        <%
            JSONArray contests = ContestProcessor.getNamesIds();
        %>
        <form name="contestSelection" action="${pageContext.request.contextPath}/roundResults.jsp" method="POST">
            <select name="contestId" width="30" size="1">
                <%
                    for (Object contest : contests) {
                        JSONObject c = (JSONObject) contest;
                        String name = c.getString("name");
                        if (!name.isEmpty()) {
                            int contestId = c.getInt("contestId");
                %>
                <option value=<%=contestId%>><%=name%>
                </option>
                <%
                        }
                    }
                %>
            </select>
            <input type="submit" value="Get rating prediction" name="sbm"/>
        </form>
    </article>
</div>

</body>
</html>
