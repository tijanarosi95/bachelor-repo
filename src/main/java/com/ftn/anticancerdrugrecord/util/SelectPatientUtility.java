package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
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
import org.apache.jena.rdf.model.Literal;
import org.springframework.stereotype.Component;

@Component
public class SelectPatientUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";

    private static final String TDB_SELECT_BASE_URL = "http://localhost:3030/ds/query";

    public Optional<Person> loadPatientById(final String jmbg) {
        final String queryString =
                "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                    " SELECT ?s ?jmbg ?firstName ?lastName ?gender ?age " +
                    " ?isCancerSpread ?isCancerGrown ?isCancerSpreadToOrgans ?strongPain " +
                    " ?isCancerReappear ?isCancerDetectable ?lifeQuality ?weightLoss " +
                    " WHERE { " +
                        "?s drg:jmbg ?jmbg FILTER ( str(?jmbg) = '%s' ) . " +
                        "?s drg:firstName ?firstName . " +
                        "?s drg:lastName ?lastName . " +
                        "?s drg:gender ?gender . " +
                        "?s drg:age ?age . " +
                        "?s drg:isCancerSpread ?isCancerSpread . " +
                        "?s drg:isCancerGrown ?isCancerGrown . " +
                        "?s drg:isCancerSpreadToOrgans ?isCancerSpreadToOrgans . " +
                        "?s drg:isCancerReappear ?isCancerReappear . " +
                        "?s drg:isCancerDetectable ?isCancerDetectable . " +
                        "?s drg:lifeQuality ?lifeQuality . " +
                        "?s drg:weightLoss ?weightLoss . }";
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
                final Literal weightLoss = solution.getLiteral("weightLoss");
                var patient = Person.builder()
                                    .jmbg(jmbg)
                                    .firstName(firstName.getString())
                                    .lastName(lastName.getString())
                                    .age(age.getInt())
                                    .gender(Gender.valueOf(gender.getString()))
                                    .isCancerSpread(isCancerSpread.getBoolean())
                                    .isCancerGrown(isCancerGrown.getBoolean())
                                    .isCancerDetectable(isCancerDetectable.getBoolean())
                                    .isCancerReappear(isCancerReappear.getBoolean())
                                    .isCancerSpreadToOrgans(isCancerSpreadToOrgans.getBoolean())
                                    .lifeQuality(LifeQuality.valueOf(lifeQuality.getString()))
                                    .weightLoss(weightLoss.getBoolean())
                                    .build();
                return Optional.of(patient);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Person> loadPatientsByTreatedDrug(final String drugName) {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
             " SELECT DISTINCT ?s ?isTreatedWith ?jmbg ?firstName ?lastName ?gender ?age " +
             " ?isCancerSpread ?isCancerGrown ?isCancerSpreadToOrgans ?strongPain " +
             " ?isCancerReappear ?isCancerDetectable ?lifeQuality ?hasDisease ?weightLoss " +
             " WHERE { " +
             "?s drg:isTreatedWith ?isTreatedWith FILTER ( str(?isTreatedWith) = '%s') . " +
             "?s drg:jmbg ?jmbg . " +
             "?s drg:firstName ?firstName . " +
             "?s drg:lastName ?lastName . " +
             "?s drg:gender ?gender . " +
             "?s drg:age ?age . " +
             "?s drg:isCancerSpread ?isCancerSpread . " +
             "?s drg:isCancerGrown ?isCancerGrown . " +
             "?s drg:isCancerSpreadToOrgans ?isCancerSpreadToOrgans . " +
             "?s drg:isCancerReappear ?isCancerReappear . " +
             "?s drg:isCancerDetectable ?isCancerDetectable . " +
             "?s drg:lifeQuality ?lifeQuality . " +
             "?s drg:hasDisease ?hasDisease . " +
             "?s drg:weightLoss ?weightLoss . }";
        final String formattedQueryString = String.format(queryString, drugName);
        System.out.println("Person query str: " + formattedQueryString);
        final Query query = QueryFactory.create(formattedQueryString);

        List<Person> personList = new ArrayList<>();

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal jmbg = solution.getLiteral("jmbg");
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
                final Literal weightLoss = solution.getLiteral("weightLoss");
                final Literal disease = solution.getLiteral("hasDisease");
                var patient = new Person();

                patient.setJmbg(jmbg.getString());
                patient.setFirstName(firstName.getString());
                patient.setLastName(lastName.getString());
                patient.setAge(age.getInt());
                patient.setGender(Gender.valueOf(gender.getString()));
                patient.setCancerSpread(isCancerSpread.getBoolean());
                patient.setCancerGrown(isCancerGrown.getBoolean());
                patient.setCancerReappear(isCancerReappear.getBoolean());
                patient.setCancerDetectable(isCancerDetectable.getBoolean());
                patient.setCancerSpreadToOrgans(isCancerSpreadToOrgans.getBoolean());
                patient.setLifeQuality(LifeQuality.valueOf(lifeQuality.getString()));
                patient.setHasDisease(new Disease(disease.getString()));
                patient.setWeightLoss(weightLoss.getBoolean());

                personList.add(patient);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return personList;
    }

    public List<Person> loadAllPersons() {
        final String queryString =
        "PREFIX drg:" + DRUGS_URI + " " +
        "PREFIX rdf:" + RDF_URI + " " +
        " SELECT * WHERE { " +
        "?s rdf:type drg:Person ;" +
        " drg:jmbg ?jmbg ;" +
        " drg:firstName ?firstName ;" +
        " drg:lastName ?lastName ;" +
        " drg:gender ?gender ;" +
        " drg:age ?age ;" +
        " drg:isCancerSpread ?isCancerSpread ;" +
        " drg:isCancerGrown ?isCancerGrown ;" +
        " drg:isCancerSpreadToOrgans ?isCancerSpreadToOrgans ;" +
        " drg:isCancerReappear ?isCancerReappear ;" +
        " drg:isCancerDetectable ?isCancerDetectable ;" +
        " drg:lifeQuality ?lifeQuality ;" +
        " drg:weightLoss ?weightLoss ; }";

        System.out.println("Person query str: " + queryString);
        final Query query = QueryFactory.create(queryString);

        List<Person> personList = new ArrayList<>();

        // Execute the query and obtain the results
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query)) {
            final ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                final QuerySolution solution = resultSet.next();
                final Literal jmbg = solution.getLiteral("jmbg");
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
                final Literal weightLoss = solution.getLiteral("weightLoss");
                var patient = new Person();

                patient.setJmbg(jmbg.getString());
                patient.setFirstName(firstName.getString());
                patient.setLastName(lastName.getString());
                patient.setAge(age.getInt());
                patient.setGender(Gender.valueOf(gender.getString()));
                patient.setCancerSpread(isCancerSpread.getBoolean());
                patient.setCancerDetectable(isCancerDetectable.getBoolean());
                patient.setCancerReappear(isCancerReappear.getBoolean());
                patient.setCancerGrown(isCancerGrown.getBoolean());
                patient.setCancerSpreadToOrgans(isCancerSpreadToOrgans.getBoolean());
                patient.setLifeQuality(LifeQuality.valueOf(lifeQuality.getString()));
                patient.setWeightLoss(weightLoss.getBoolean());

                personList.add(patient);
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
            exception.printStackTrace();
        }
        return personList;

    }

}
