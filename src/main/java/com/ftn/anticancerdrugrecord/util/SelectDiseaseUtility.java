package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
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
        "?x drg:id ?id FILTER ( ?id = %s ) . " +
        "?y drg:name ?name . }";
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
}
