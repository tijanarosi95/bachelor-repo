package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.springframework.stereotype.Component;

@Component
public class SelectDiseaseUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";

    private static final String TDB_SELECT_BASE_URL = "http://localhost:3030/ds/query";

    public Optional<Disease> loadDiseaseById(final Integer id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        "SELECT ?s ?id ?name " +
        " WHERE { " +
        "?s drg:id ?id FILTER ( ?id = %s ) . " +
        "?s drg:name ?name . }";
        final String formattedQueryString = String.format(queryString, id);

        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal diseaseId = solution.getLiteral("id");
                final Literal name = solution.getLiteral("name");

                var disease = Disease.builder()
                .id(diseaseId.getInt())
                .name(name.getString())
                .build();

                return Optional.of(disease);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<PatientDiseaseDTO> loadPatientDisease(final String jmbg) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT ?s ?jmbg ?hasDisease" +
        " WHERE { " +
        "?s drg:jmbg ?jmbg FILTER ( str(?jmbg) = '%s') . " +
        "?s drg:hasDisease ?hasDisease . }";
        final String formattedQueryString = String.format(queryString, jmbg);
        System.out.println("Person disease query str: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal disease = solution.getLiteral("hasDisease");
                var patientDisease = new PatientDiseaseDTO();

                patientDisease.setPatientId(jmbg);
                patientDisease.setDisease(new DiseaseDTO(disease.getString()));

                return Optional.of(patientDisease);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Disease> loadAllDiseases() {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        "SELECT * WHERE { " +
        "?s rdf:type drg:Disease ;" +
        " drg:id ?id ; " +
        " drg:name ?name ; }";
        final Query query = QueryFactory.create(queryString);
        System.out.println("Select all diseases: " + queryString);

        final List<Disease> diseases = new ArrayList<>();

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal diseaseId = solution.getLiteral("id");
                final Literal name = solution.getLiteral("name");

                var disease = new Disease(diseaseId.getInt(), name.getString());

                diseases.add(disease);
            }
            return diseases;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return diseases;
    }

    public Optional<Disease> loadDiseaseByName(final String name) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        "SELECT ?s ?id ?name " +
        " WHERE { " +
        "?s drg:name ?name FILTER ( str(?name) = '%s' ) . " +
        "?s drg:id ?id . }";
        final String formattedQueryString = String.format(queryString, name);

        System.out.println("Select patient disease by name: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal diseaseId = solution.getLiteral("id");

                var disease = Disease.builder()
                .id(diseaseId.getInt())
                .name(name)
                .build();

                return Optional.of(disease);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }


}
