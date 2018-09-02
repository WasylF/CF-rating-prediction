<%-- 
        Document   : roundResults
        Created on : Feb 7, 2017, 8:01:12 PM
        Author     : Wsl_F
--%>
<%@page import="com.wslfinc.cf.ContestProcessor" %>
<%@page import="com.wslfinc.cf.RatingGetter" %>
<%@page import="org.json.JSONArray" %>
<%@page import="org.json.JSONObject" %>

<%
    JSONArray newRating;
    int contestId;
    try {
        contestId = Integer.valueOf(request.getParameter("contestId"));
        newRating = RatingGetter.getNewRatingJSON(contestId).getJSONArray("result");
    } catch (NumberFormatException ex) {
        contestId = -1;
        newRating = new JSONArray();
    } catch (Exception exc) {
        contestId = -2;
        newRating = new JSONArray();
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Round results</title>

    <link rel="stylesheet" type="text/css" href="roundResults.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="JS/sorttable.js"></script>

</head>
<body>
<% if (contestId > 0) {%>
<header>
    <h1>CF-Predictor</h1>
</header>
<article>
    <h2>Unofficial Results of <%=ContestProcessor.getName(contestId)%>
    </h2>

    <table class="sortable" id="table">
        <thead>
        <tr>
            <th>Rank</th>
            <th>Handle</th>
            <th>Delta</th>
            <th>Seed</th>
            <th>Previous rating</th>
            <th>Expecting new rating</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Object contestanRes : newRating) {
                JSONObject cr = (JSONObject) contestanRes;
                int delta = cr.getInt("newRating") - cr.getInt("oldRating");
                double x = Math.min(delta, 142);
                double opacity = Math.abs(x) / 150. + 0.05;
        %>
        <tr>
            <td><%= cr.getInt("rank")%>
            </td>
            <td><%= cr.getString("handle")%>
            </td>
            <td style='background-color:rgba(<%= (delta >= 0 ? "0,255,0" : "255,0,0")%>, <%= opacity%>)'><%= delta%>
            </td>
            <td><%= (int) cr.getDouble("seed")%>
            </td>
            <td><%= cr.getInt("oldRating")%>
            </td>
            <td><%= cr.getInt("newRating")%>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>

    <% } else { %>
    <% if (contestId == -1) { %>
    <h1> Wrong contestId parameter </h1>
    <%} else {%>
    <h1> Contest has not started yet </h1>
    <h4> or something else getting wrong (:</h4>
    <%}%>
    <%}%>
</article>
</body>
</html>
