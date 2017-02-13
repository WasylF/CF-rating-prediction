<%-- 
    Document   : roundResults
    Created on : Feb 7, 2017, 8:01:12 PM
    Author     : Wsl_F
--%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.wslfinc.cf.RatingGetter"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.List"%>
<%
    int contestId = Integer.valueOf(request.getParameter("contestId"));
    JSONArray newRating = RatingGetter.getNewRating(contestId).getJSONArray("result");
    System.out.println("New ratings loaded");
    final String white = "#FFFFFF";
    final String grey = "#D3D3D3";
    String color = grey;
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Round results</title>
    </head>
    <body>
        <h1> Results of contest <%=contestId%></h1>
        <h2> It's unofficial results, service works in beta</h2> 
        
        <table border="5">
            <thead>
                <tr bgcolor= "#808080">>
                    <th> &nbsp; Rank &nbsp; </th>
                    <th> &nbsp; Handle &nbsp;</th>
                    <th> &nbsp; Delta &nbsp;</th>
                    <th> &nbsp; Seed &nbsp;</th>
                    <th> &nbsp; Previous rating &nbsp;</th>
                    <th> &nbsp; Expecting new rating &nbsp;</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Object contestanRes : newRating) {
                        JSONObject cr = (JSONObject) contestanRes;
                        color = color == white ? grey : white;
                %>
                <tr bgcolor= <%=color%> >
                    <td> <%= cr.getInt("rank")%> </td>
                    <td> <%= cr.getString("handle")%> </td>
                    <td> <%= cr.getInt("newRating") - cr.getInt("oldRating")%> </td>
                    <td> <%= (int) cr.getDouble("seed")%> </td>
                    <td> <%= cr.getInt("oldRating")%> </td>
                    <td> <%= cr.getInt("newRating")%> </td>
                </tr>
                <%}%>
            </tbody>
        </table>
    </body>
</html>
