<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="feedback" tagdir="/WEB-INF/tags/responsive/feedback"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="ale" tagdir="/WEB-INF/tags/responsive/feedback"%>


<template:page pageTitle="Feedback">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
    <div class="page-feedback">
        <div class="header">
            <h3>Feedback</h3>
        </div>
        <div class="feedback-form">
        <form:form id="feedbackForm" action="feedback/save" method="POST" modelAttribute="feedbackForm">
            <form:input cssClass="form-control" name="subject" id="subject" maxlength="100" placeholder="Subject" path="subject"  />
            <form:input cssClass="form-control" name="message" id="message" maxlength="100" placeholder="Message" path="message"  />

            <button type="submit" id="submit" class="btn btn-primary btn-sm">Save</button>
            <a href="/feedback" role="button" id="cancel" class="btn btn-light btn-sm">Cancel</a>
        </form:form>
        </div>
        <c:choose>
                    <c:when test="${not empty newFeedback}">
                        <ale:alert type="success" message="Feedback sent successfully"/>
                    </c:when>
                    <c:when test="${not empty badFeedback}">
                        <ale:alert type="success" message="Feedback did not send successfully"/>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
        <div class="customer-feedbacks">
            <feedback:customerFeedback customerFeedbacks="${feedbacks}"/>
        </div>
    </div>
</template:page>
