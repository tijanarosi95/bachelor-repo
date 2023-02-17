package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.person.Gender;
import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import com.ftn.anticancerdrugrecord.model.person.Person;
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

}
