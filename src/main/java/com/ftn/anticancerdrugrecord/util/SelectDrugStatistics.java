package com.ftn.anticancerdrugrecord.util;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.springframework.stereotype.Component;

@Component
public class SelectDrugStatistics {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";

    private static final String TDB_SELECT_BASE_URL = "http://localhost:3030/ds/query";

    public Integer getHasEfficacyCount(final int id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT (COUNT(?item) AS ?countHasEfficacy)" +
        " WHERE {" +
        "?item drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
        "?item drg:hasEfficacy true .}";

        final String formattedQueryString = String.format(queryString, id);
        System.out.println("Statistics query count: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal count = solution.getLiteral("countHasEfficacy");
                System.out.println("Value: " + count.getInt());

                return count.getInt();
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer getHasToxicityCount(final int id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT (COUNT(?item) AS ?countHasToxicity)" +
        " WHERE {" +
        "?item drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
        "?item drg:hasToxicity true .}";

        final String formattedQueryString = String.format(queryString, id);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal count = solution.getLiteral("countHasToxicity");

                return count.getInt();
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer getHasSideEffectsCount(final int id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT (COUNT(?item) AS ?countHasSideEffects)" +
        " WHERE {" +
        "?item drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
        "?item drg:hasSideEffects true .}";

        final String formattedQueryString = String.format(queryString, id);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal count = solution.getLiteral("countHasSideEffects");

                return count.getInt();
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer getHasTherapeuticEffectCount(final int id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT (COUNT(?item) AS ?countHasTherapeuticEffect)" +
        " WHERE {" +
        "?item drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
        "?item drg:hasTherapeuticEffect true .}";

        final String formattedQueryString = String.format(queryString, id);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal count = solution.getLiteral("countHasTherapeuticEffect");

                return count.getInt();
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer getIsDoseRangedCount(final int id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT (COUNT(?item) AS ?countIsDoseRanged)" +
        " WHERE {" +
        "?item drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
        "?item drg:isDoseRanged true .}";

        final String formattedQueryString = String.format(queryString, id);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal count = solution.getLiteral("countIsDoseRanged");

                return count.getInt();
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return 0;
    }

}
