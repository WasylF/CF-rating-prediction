<%-- 
    Document   : roundResults
    Created on : Feb 7, 2017, 8:01:12 PM
    Author     : Wsl_F
--%>
<%@page import="com.wslfinc.cf.sdk.entities.additional.ContestantResult"%>
<%@page import="java.util.List"%>
<%@page import="com.wslfinc.cf.sdk.CodeForcesSDK"%>
<%
    int contestId = Integer.valueOf(request.getParameter("contestId"));
    List<ContestantResult> newRating = CodeForcesSDK.getNewRatings(contestId);
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
                    <th> &nbsp; Seed &nbsp;</th>
                    <th> &nbsp; Previous rating &nbsp;</th>
                    <th> &nbsp; Expecting new rating &nbsp;</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (ContestantResult cr : newRating) {
                        color = color == white ? grey : white;
                %>
                <tr bgcolor= <%=color%> >
                    <td> <%= cr.getRank()%> </td>
                    <td> <%= cr.getHandle()%> </td>
                    <td> <%= (int) cr.getSeed()%> </td>
                    <td> <%= cr.getPrevRating()%> </td>
                    <td> <%= cr.getNextRating()%> </td>
                </tr>
                <%}%>
            </tbody>
        </table>
    </body>
</html>
