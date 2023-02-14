package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.model.person.Gender;
import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import com.ftn.anticancerdrugrecord.model.person.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetRewindable;
import org.apache.jena.rdf.model.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    private static final String ONTOLOGY_PATH = "src/main/resources/ontology/drugs.owl";

    private static final String TDB_SELECT_BASE_URL = "http://localhost:3030/ds/query";

    @Autowired
    private LoadOntologyUtility ontologyUtility;

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

    public Optional<Person> loadPatientById(final String jmbg) {
        final String queryString =
                "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                    " SELECT ?jmbg ?firstName ?lastName ?gender ?age " +
                    " ?isCancerSpread ?isCancerGrown ?isCancerSpreadToOrgans ?strongPain " +
                    " ?isCancerReappear ?isCancerDetectable ?lifeQuality" +
                    " WHERE { " +
                        "?x drg:jmbg ?jmbg FILTER ( str(?jmbg) = '%s' ) . " +
                        "?y drg:firstName ?firstName . " +
                        "?z drg:lastName ?lastName . " +
                        "?e drg:gender ?gender . " +
                        "?f drg:age ?age . " +
                        "?d drg:isCancerSpread ?isCancerSpread . " +
                        "?g drg:isCancerGrown ?isCancerGrown . " +
                        "?h drg:isCancerSpreadToOrgans ?isCancerSpreadToOrgans . " +
                        "?i drg:isCancerReappear ?isCancerReappear . " +
                        "?j drg:isCancerDetectable ?isCancerDetectable . " +
                        "?c drg:lifeQuality ?lifeQuality . " +
                    " } ";
        final String formattedQueryString = String.format(queryString, jmbg);
        System.out.println("Person query str: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            if (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal firstName = solution.getLiteral("firstName");
                final Literal lastName = solution.getLiteral("lastName");
                final Literal gender = solution.getLiteral("gender");
                final Literal age = solution.getLiteral("age");
                final Literal isCancerSpread = solution.getLiteral("isCancerSpread");
                final Literal isCancerGrown = solution.getLiteral("isCancerGrown");
                final Literal isCancerSpreadToOrgans = solution.getLiteral("isCancerSpreadToOrgans");
                final Literal isCancerReappear = solution.getLiteral("isCancerReappear");
                final Literal isCancerDetectable = solution.getLiteral("isCancerDetectable");
                final Literal lifeQuality = solution.getLiteral("lifeQuality");
                var patient = Person.builder()
                                    .jmbg(jmbg)
                                    .firstName(firstName.getString())
                                    .lastName(lastName.getString())
                                    .age(age.getInt())
                                    .gender(Gender.valueOf(gender.getString()))
                                    .isCancerSpread(isCancerSpread.getBoolean())
                                    .isCancerGrown(isCancerDetectable.getBoolean())
                                    .isCancerReappear(isCancerReappear.getBoolean())
                                    .isCancerGrown(isCancerGrown.getBoolean())
                                    .isCancerSpreadToOrgans(isCancerSpreadToOrgans.getBoolean())
                                    .lifeQuality(LifeQuality.valueOf(lifeQuality.getString()))
                                    .build();
                return Optional.of(patient);
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

    public List<Drug> getDrugsByPhaseType(final String phase) {
        return null;
    }


}
