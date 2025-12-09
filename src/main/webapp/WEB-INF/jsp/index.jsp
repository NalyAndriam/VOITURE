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
        body {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            min-height: 100vh;
            padding: 2rem 0;
        }
        .main-title {
            font-weight: 300;
            color: #0d6efd;
        }
        .card {
            border: none;
            border-radius: 1rem;
            box-shadow: 0 6px 20px rgba(0,0,0,0.1);
        }
        .card-header {
            background: linear-gradient(90deg, #0d6efd, #0dcaf0);
            color: white;
            border-radius: 1rem 1rem 0 0 !important;
            font-weight: 500;
        }
        .table thead {
            background-color: #0d6efd;
            color: white;
        }
        .spinner { display: none; text-align: center; padding: 4rem; }
        .badge-retour { font-size: 0.95rem; }
    </style>
</head>
<body>

<div class="container">


    <div class="text-center mb-5">
        <h1 class="main-title display-5">
            <i class="bi bi-airplane-fill me-3"></i>
            Planning Transferts Aéroport TNR
        </h1>
        <p class="lead text-muted">Attribution automatique des voitures selon les arrivées</p>
    </div>

 
    <div class="card mb-5">
        <div class="card-body">
            <div class="row g-3 align-items-end justify-content-center">
                <div class="col-md-4">
                    <label class="form-label fw-bold text-primary">
                        <i class="bi bi-calendar-event"></i> Date
                    </label>
                    <input type="date" id="datePicker" class="form-control form-control-lg" value="${selectedDate}" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label fw-bold text-primary">
                        <i class="bi bi-truck"></i> Voiture (facultatif)
                    </label>
                    <select id="voitureFilter" class="form-select form-select-lg">
                        <option value="">Toutes les voitures</option>
                        <c:forEach var="v" items="${voitures}">
                            <option value="${v.numero}">${v.numero} – ${v.nbplaces} places</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <button id="btnLoad" class="btn btn-primary btn-lg w-100">
                        <i class="bi bi-search"></i> Charger
                    </button>
                </div>
            </div>
        </div>
    </div>


    <div id="loading" class="spinner">
        <div class="spinner-border text-primary" style="width: 4rem; height: 4rem;" role="status">
            <span class="visually-hidden">Chargement…</span>
        </div>
        <p class="mt-4 fs-5 text-muted">Calcul du planning en cours...</p>
    </div>


    <div id="planningResult">
        <c:if test="${not empty selectedDate}">
            <div class="alert alert-primary text-center rounded-pill shadow-sm shadow-sm mb-4">
                <i class="bi bi-calendar-check"></i>
                <strong>Planning du <fmt:formatDate value="${selectedDateAsDate}" pattern="EEEE dd MMMM yyyy" /></strong>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty voitures}">
                <div class="row g-4">
                    <c:forEach var="v" items="${voitures}">
                        <div class="col-lg-6 col-xxl-4">
                            <div class="card h-100">
                                <div class="card-header text-center py- py-3">
                                    <h4 class="mb-0">
                                        <i class="bi bi-truck"></i> ${v.numero}
                                        <span class="badge bg-light text-dark ms-2">${v.nbplaces} places</span>
                                    </h4>
                                </div>
                                <div class="card-body p-0">
                                    <c:choose>
                                        <c:when test="${empty v.reservationsAttribuees}">
                                            <div class="text-center py-5 text-muted">
                                                <i class="bi bi-calendar-x fs-1"></i>
                                                <p class="mt-3 fs- fs-5">Aucune réservation</p>
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
                                                        <td>
                                                            <strong>${r.nom} ${r.prenom}</strong><br>
                                                            <small class="text-muted">${r.nbPersonne} pers.</small>
                                                        </td>
                                                        <td class="text-center">${r.heureArrivee}</td>
                                                        <td>${r.nomHotel}</td>
                                                        <td class="text-center">${r.distanceKm * 2} km</td>
                                                        <td class="text-center">
                                                            <span class="badge bg-success badge-retour">${r.heureRetour}</span>
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
            </c:when>
            <c:otherwise>
                <div class="text-center py-5">
                    <i class="bi bi-calendar3 display-1 text-muted"></i>
                    <p class="fs-3 text-muted mt-4">Choisissez une date pour afficher le planning</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
$(document).ready(function () {
    function loadPlanning() {
        const date = $('#datePicker').val().trim();
        const voiture = $('#voitureFilter').val();

        if (!date) {
            alert("Veuillez sélectionner une date");
            return;
        }

        $('#loading').show();
        $('#planningResult').hide();

        const url = '?date=' + date + (voiture ? '&voiture=' + voiture : '');

        $.get(url, function (data) {
            $('#planningResult').html($(data).find('#planningResult').html());
            $('#loading').hide();
            history.replaceState({}, '', url);
        }).fail(function () {
            alert("Erreur lors du chargement du planning");
            $('#loading').hide();
        });
    }

    $('#btnLoad').on('click', loadPlanning);

   
    $('#datePicker').on('keydown', function(e) {
        if (e.key === 'Enter') loadPlanning();
    });

    
    const params = new URLSearchParams(location.search);
    if (params.has('date')) {
        $('#datePicker').val(params.get('date'));
        if (params.has('voiture')) $('#voitureFilter').val(params.get('voiture'));
        loadPlanning();
    }
});
</script>

</body>
</html>