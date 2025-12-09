<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Planning Transferts Aéroport TNR</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%); min-height: 100vh; padding: 2rem 0; }
        .main-title { font-weight: 300; color: #0d6efd; }
        .card { border: none; border-radius: 1rem; box-shadow: 0 6px 20px rgba(0,0,0,0.1); }
        .card-header { background: linear-gradient(90deg, #0d6efd, #0dcaf0); color: white; border-radius: 1rem 1rem 0 0 !important; }
        .table thead { background-color: #0d6efd; color: white; }
    </style>
</head>
<body>

<div class="container">

    <!-- Titre -->
    <div class="text-center mb-5">
        <h1 class="display-5" style="font-weight:300;color:#0d6efd">
            Planning Transferts Aéroport TNR
        </h1>
        <p class="lead text-muted">Attribution automatique des voitures</p>
    </div>

    <!-- Formulaire de filtre -->
    <div class="card mb-5">
        <div class="card-body">
            <form method="get" class="row g-3 align-items-end justify-content-center">
                <div class="col-md-4">
                    <label class="form-label fw-bold text-primary">Date</label>
                    <input type="date" name="date" class="form-control form-control-lg" 
                           value="${selectedDate}" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold text-primary">Voiture (facultatif)</label>
                    <select name="voiture" class="form-select form-select-lg">
                        <option value="">Toutes les voitures</option>
                        <c:forEach var="v" items="${voitures}">
                            <option value="${v.numero}" ${param.voiture == v.numero ? 'selected' : ''}>
                                ${v.numero} – ${v.nbplaces} places
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary btn-lg w-100">
                        Afficher
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Message si aucune date -->
    <c:if test="${empty selectedDate}">
        <div class="text-center py-5">
            <i class="bi bi-calendar3 display-1 text-muted"></i>
            <p class="fs-3 text-muted mt-4">Sélectionnez une date pour voir le planning</p>
        </div>
    </c:if>

    <!-- Résultat -->
    <c:if test="${not empty selectedDate}">
        <div class="alert alert-primary text-center rounded-pill shadow-sm mb-4">
            <strong>Planning du <fmt:formatDate value="${selectedDateAsDate}" pattern="EEEE dd MMMM yyyy" /></strong>
        </div>

        <div class="row g-4">
            <c:forEach var="v" items="${voitures}">
                <div class="col-lg-6 col-xxl-4">
                    <div class="card h-100">
                        <div class="card-header text-center py-3">
                            <h4 class="mb-0">
                                ${v.numero}
                                <span class="badge bg-light text-dark ms-2">${v.nbplaces} places</span>
                            </h4>
                        </div>
                        <div class="card-body p-0">
                            <c:choose>
                                <c:when test="${empty v.reservationsAttribuees}">
                                    <div class="text-center py-5 text-muted">
                                        <i class="bi bi-calendar-x fs-1"></i>
                                        <p class="mt-3">Aucune réservation</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <table class="table table-hover align-middle mb-0">
                                        <thead>
                                        <tr class="table-primary">
                                            <th>Client</th>
                                            <th>Arrivée</th>
                                            <th>Hôtel</th>
                                            <th>A/R</th>
                                            <th>Retour TNR</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="r" items="${v.reservationsAttribuees}">
                                            <tr>
                                                <td><strong>${r.nom} ${r.prenom}</strong><br>
                                                    <small class="text-muted">${r.nbPersonne} pers.</small></td>
                                                <td class="text-center">${r.heureArrivee}</td>
                                                <td>${r.nomHotel}</td>
                                                <td class="text-center">${r.distanceKm * 2} km</td>
                                                <td class="text-center">
                                                    <span class="badge bg-success">${r.heureRetour}</span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

</body>
</html>