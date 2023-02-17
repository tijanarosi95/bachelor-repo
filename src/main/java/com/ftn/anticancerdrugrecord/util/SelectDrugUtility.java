package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
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
public class SelectDrugUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";

    private static final String TDB_SELECT_BASE_URL = "http://localhost:3030/ds/query";

    public Optional<Drug> loadDrugById(final int id) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        "SELECT ?drugID ?name ?activeIngredient ?isDoseRanged ?hasEfficacy " +
        " ?hasToxicity ?hasSideEffects ?hasTherapeuticEffect ?hasApproved " +
        " WHERE { " +
        "?x drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
        "?y drg:name ?name . " +
        "?z drg:activeIngredient ?activeIngredient . " +
        "?e drg:isDoseRanged ?isDoseRanged . " +
        "?f drg:hasEfficacy ?hasEfficacy . " +
        "?d drg:hasToxicity ?hasToxicity . " +
        "?g drg:hasSideEffects ?hasSideEffects . " +
        "?h drg:hasTherapeuticEffect ?hasTherapeuticEffect . " +
        "?i drg:hasApproved ?hasApproved . " +
        " } ";
        final String formattedQueryString = String.format(queryString, id);

        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal name = solution.getLiteral("name");
                final Literal activeIngredient = solution.getLiteral("activeIngredient");
                final Literal isDoseRanged = solution.getLiteral("isDoseRanged");
                final Literal hasEfficacy = solution.getLiteral("hasEfficacy");
                final Literal hasToxicity = solution.getLiteral("hasToxicity");
                final Literal hasSideEffects = solution.getLiteral("hasSideEffects");
                final Literal hasTherapeuticEffect = solution.getLiteral("hasTherapeuticEffect");
                final Literal hasApproved = solution.getLiteral("hasApproved");
                var drug = Drug.builder()
                .drugId(Integer.toString(id))
                .activeIngredient(activeIngredient.getString())
                .name(name.getString())
                .isDoseRanged(isDoseRanged.getBoolean())
                .hasEfficacy(hasEfficacy.getBoolean())
                .hasSideEffects(hasSideEffects.getBoolean())
                .hasTherapeuticEffect(hasTherapeuticEffect.getBoolean())
                .hasToxicity(hasToxicity.getBoolean())
                .isApproved(hasApproved.getBoolean())
                .build();
                return Optional.of(drug);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Drug> loadDrugsByDiseaseType(final String type) {
        List<Drug> loadedDrugs = new ArrayList<>();
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT DISTINCT ?drugID ?activeIngredient ?isDoseRanged ?hasEfficacy " +
        " ?hasToxicity ?hasSideEffects ?hasTherapeuticEffect ?hasApproved ?mayTreat " +
        " WHERE { " +
        "?x drg:mayTreat ?mayTreat FILTER ( str(?mayTreat) = '%s' ) . " +
        "?p drg:drugID ?drugID . " +
        "?z drg:activeIngredient ?activeIngredient . " +
        "?e drg:isDoseRanged ?isDoseRanged . " +
        "?f drg:hasEfficacy ?hasEfficacy . " +
        "?d drg:hasToxicity ?hasToxicity . " +
        "?g drg:hasSideEffects ?hasSideEffects . " +
        "?h drg:hasTherapeuticEffect ?hasTherapeuticEffect . " +
        "?i drg:hasApproved ?hasApproved . " +
        " } ";
        final String formattedQueryString = String.format(queryString, type);

        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query);
        try {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                Literal id = solution.getLiteral("drugID");
                Literal activeIngredient = solution.getLiteral("activeIngredient");
                Literal isDoseRanged = solution.getLiteral("isDoseRanged");
                Literal hasEfficacy = solution.getLiteral("hasEfficacy");
                Literal hasToxicity = solution.getLiteral("hasToxicity");
                Literal hasSideEffects = solution.getLiteral("hasSideEffects");
                Literal hasTherapeuticEffect = solution.getLiteral("hasTherapeuticEffect");
                Literal hasApproved = solution.getLiteral("hasApproved");

                Drug drug = new Drug();
                drug.setDrugId(Integer.toString(id.getInt()));
                drug.setActiveIngredient(activeIngredient.getString());
                drug.setDoseRanged(isDoseRanged.getBoolean());
                drug.setHasEfficacy(hasEfficacy.getBoolean());
                drug.setHasEfficacy(hasSideEffects.getBoolean());
                drug.setHasTherapeuticEffect(hasTherapeuticEffect.getBoolean());
                drug.setHasToxicity(hasToxicity.getBoolean());
                drug.setApproved(hasApproved.getBoolean());

                loadedDrugs.add(drug);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        } finally {
            queryExecution.close();
        }
        return loadedDrugs;
    }

    public List<Drug> loadDrugsByPhaseType(final String phase) {
        return null;
    }

}
