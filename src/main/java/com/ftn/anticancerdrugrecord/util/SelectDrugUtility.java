package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
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
             "SELECT ?s ?drugID ?name ?activeIngredient ?isDoseRanged ?hasEfficacy " +
             " ?hasToxicity ?hasSideEffects ?hasTherapeuticEffect ?hasApproved " +
             " WHERE { " +
             "?s drg:drugID ?drugID FILTER ( ?drugID = %s ) . " +
             "?s drg:activeIngredient ?activeIngredient . " +
             "?s drg:name ?name . " +
             "?s drg:isDoseRanged ?isDoseRanged . " +
             "?s drg:hasEfficacy ?hasEfficacy . " +
             "?s drg:hasToxicity ?hasToxicity . " +
             "?s drg:hasSideEffects ?hasSideEffects . " +
             "?s drg:hasTherapeuticEffect ?hasTherapeuticEffect . " +
             "?s drg:hasApproved ?hasApproved . }";
        final String formattedQueryString = String.format(queryString, id);
        System.out.println("QUERY: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal activeIngredient = solution.getLiteral("activeIngredient");
                final Literal name = solution.getLiteral("name");
                final Literal isDoseRanged = solution.getLiteral("isDoseRanged");
                final Literal hasEfficacy = solution.getLiteral("hasEfficacy");
                final Literal hasToxicity = solution.getLiteral("hasToxicity");
                final Literal hasSideEffects = solution.getLiteral("hasSideEffects");
                final Literal hasTherapeuticEffect = solution.getLiteral("hasTherapeuticEffect");
                final Literal hasApproved = solution.getLiteral("hasApproved");
                var drug = Drug.builder()
                .drugId(id)
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
             " SELECT DISTINCT ?s ?mayTreat ?drugID ?activeIngredient ?isDoseRanged ?hasEfficacy " +
             " ?hasToxicity ?hasSideEffects ?hasTherapeuticEffect ?hasApproved " +
             " WHERE { " +
             "?s drg:mayTreat ?mayTreat FILTER ( str(?mayTreat) = '%s' ) . " +
             "?s drg:drugID ?drugID . " +
             "?s drg:activeIngredient ?activeIngredient . " +
             "?s drg:isDoseRanged ?isDoseRanged . " +
             "?s drg:hasEfficacy ?hasEfficacy . " +
             "?s drg:hasToxicity ?hasToxicity . " +
             "?s drg:hasSideEffects ?hasSideEffects . " +
             "?s drg:hasTherapeuticEffect ?hasTherapeuticEffect . " +
             "?s drg:hasApproved ?hasApproved . }";
        final String formattedQueryString = String.format(queryString, type);

        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
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
                drug.setDrugId(id.getInt());
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
        }
        return loadedDrugs;
    }

    public List<Drug> loadAllDrugs() {
        List<Drug> loadedDrugs = new ArrayList<>();
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
              " SELECT DISTINCT ?s ?drugID ?name ?activeIngredient ?isDoseRanged ?hasEfficacy " +
              " ?hasToxicity ?hasSideEffects ?hasTherapeuticEffect ?hasApproved " +
              " WHERE { " +
              "?s drg:drugID ?drugID . " +
              "?s drg:name ?name . " +
              "?s drg:activeIngredient ?activeIngredient . " +
              "?s drg:isDoseRanged ?isDoseRanged . " +
              "?s drg:hasEfficacy ?hasEfficacy . " +
              "?s drg:hasToxicity ?hasToxicity . " +
              "?s drg:hasSideEffects ?hasSideEffects . " +
              "?s drg:hasTherapeuticEffect ?hasTherapeuticEffect . " +
              "?s drg:hasApproved ?hasApproved . }";

        final Query query = QueryFactory.create(queryString);
        System.out.println("Select all drugs: " + query);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                Resource s = solution.getResource("s");
                Literal id = solution.getLiteral("drugID");
                Literal name = solution.getLiteral("name");
                Literal activeIngredient = solution.getLiteral("activeIngredient");
                Literal isDoseRanged = solution.getLiteral("isDoseRanged");
                Literal hasEfficacy = solution.getLiteral("hasEfficacy");
                Literal hasToxicity = solution.getLiteral("hasToxicity");
                Literal hasSideEffects = solution.getLiteral("hasSideEffects");
                Literal hasTherapeuticEffect = solution.getLiteral("hasTherapeuticEffect");
                Literal hasApproved = solution.getLiteral("hasApproved");

                Drug drug = new Drug();
                drug.setDrugId(id.getInt());
                drug.setName(name.getString());
                drug.setActiveIngredient(activeIngredient.getString());
                drug.setDoseRanged(isDoseRanged.getBoolean());
                drug.setHasEfficacy(hasEfficacy.getBoolean());
                drug.setHasSideEffects(hasSideEffects.getBoolean());
                drug.setHasTherapeuticEffect(hasTherapeuticEffect.getBoolean());
                drug.setHasToxicity(hasToxicity.getBoolean());
                drug.setApproved(hasApproved.getBoolean());

                loadedDrugs.add(drug);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return loadedDrugs;
    }

    public Optional<PatientDrugDTO> loadPatientTreatedDrug(String jmbg) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT ?s ?jmbg ?isTreatedWith" +
        " WHERE { " +
        "?s drg:jmbg ?jmbg FILTER ( str(?jmbg) = '%s') . " +
        "?s drg:isTreatedWith ?isTreatedWith . }";
        final String formattedQueryString = String.format(queryString, jmbg);
        System.out.println("Person treated drug query str: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal drug = solution.getLiteral("isTreatedWith");
                var patientDrug = new PatientDrugDTO();

                patientDrug.setPatientId(jmbg);
                patientDrug.setDrugName(drug.getString());

                return Optional.of(patientDrug);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Drug> loadDrugByName(String drugName) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT ?s ?drugID ?name" +
        " WHERE { " +
        "?s drg:name ?name FILTER ( str(?name) = '%s') . " +
        "?s drg:drugID ?drugID . }";
        final String formattedQueryString = String.format(queryString, drugName);
        System.out.println("Query get drug by drugName: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal id = solution.getLiteral("drugID");

                Drug drug = new Drug();
                drug.setDrugId(id.getInt());

                return Optional.of(drug);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return Optional.empty();
    }
}
