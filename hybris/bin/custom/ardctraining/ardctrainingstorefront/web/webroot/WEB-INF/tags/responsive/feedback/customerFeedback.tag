<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="customerFeedbacks" required="true" type="java.util.List<com.ardctraining.facades.feedback.CustomerFeedbackData>" %>

<div class="customer-feedbacks">
    <table class="card=body table table-striped">
        <thead>
            <tr>
                <th class="card-title" scope="col">Subject</th>
                <th class="card-title" scope="col">SubmittedDate</th>
                <th class="card-title" scope="col">Status</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${customerFeedbacks}" var="feedback">
        <tr>
        <td> ${feedback.subject}</td>
        <td> ${feedback.submittedDate}</td>
        <td> ${feedback.status}</tdd>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>