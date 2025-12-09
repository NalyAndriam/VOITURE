<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Planning Voitures</title></head>
<body>
    <h1>Filtre par date</h1>
    <form action="/" method="get">
        Date : <input type="date" name="date" value="${selectedDate}" required/>
        <button type="submit">Filtrer</button>
    </form>

    <c:if test="${not empty voitures}">
        <h2>Planning pour le ${selectedDate}</h2>
        <c:forEach var="v" items="${voitures}">
            <h3>Voiture ${v.numero} (${v.nbplaces} places)</h3>
            <c:if test="${empty v.reservationsAttribuees}">
                <p>Aucune réservation attribuée.</p>
            </c:if>
            <c:if test="${not empty v.reservationsAttribuees}">
                <table border="1">
                    <tr><th>Réservation</th><th>Heure Arrivée</th><th>Hôtel</th><th>Distance AR (km)</th><th>Temps Trajet (h)</th><th>Heure Retour TNR</th></tr>
                    <c:forEach var="r" items="${v.reservationsAttribuees}">
                        <tr>
                            <td>${r.nom} ${r.prenom} (${r.nbPersonne} pers.)</td>
                            <td>${r.heureArrivee}</td>
                            <td>${r.nomHotel}</td>
                            <td>${r.distanceKm * 2}</td>
                            <td>${r.tempsTrajetHeures}</td>
                            <td>${r.heureRetour}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:forEach>
    </c:if>
</body>
</html>