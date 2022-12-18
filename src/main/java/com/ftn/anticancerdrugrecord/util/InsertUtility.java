package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.model.person.Person;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InsertUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";

    private static final String TDB_BASE_URL = "http://localhost:3030/ds/update";

    public void insertPerson(final Person person) {
        try {
            final String insertQuery = createInsertQuery(person);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    public void insertDisease(final Disease disease) {
        try {
            final String insertQuery = createInsertQuery(disease);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    public void insertDrug(final Drug drug) {
        try {
            final String insertQuery = createInsertQuery(drug);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    private String createInsertQuery(final Person person) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                            + " drg:" + getPersonInitials(person) + " drg:firstName '" + person.getFirstName() + "';"
                            + " drg:lastName '" + person.getLastName() + "';"
                            + " drg:jmbg '" +  person.getJmbg() + "';"
                            + " drg:age '" + person.getAge() + "';"
                            + " drg:gender " + person.getGender().toString() + "';";
    }

    private String createInsertQuery(final Disease disease) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " drg:" + disease.getId() + " drg:name '" + disease.getName() + "';";
    }

    private String createInsertQuery(final Drug drug) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " drg:" + drug.getDrugId() + " drg:activeIngredient '" + drug.getActiveIngredient() + "';"
                + " drg:isDoseRanged '" + drug.isDoseRanged() + "';"
                + " drg:hasEfficacy '" + drug.isHasEfficacy() + "';"
                + " drg:hasToxicity '" + drug.isHasToxicity() + "';"
                + " drg:hasSideEffects '" + drug.isHasSideEffects() + "';"
                + " drg:hasTherapeuticEffect '" + drug.isHasTherapeuticEffect() + "';"
                + " drg:hasApproved '" + drug.isApproved() + "';"
                + createMayTreatStatements(drug.getMayTreat());
    }

    private String createMayTreatStatements(final Set<Disease> diseases) {
        return diseases.stream()
                .map(Disease::getName)
                .collect(Collectors.joining("", " drg:mayTreat '", "';"));
    }

    private String getPersonInitials(final Person person) {
        final char firstNameInitial = person.getFirstName().charAt(0);
        final char lastNameInitial = person.getLastName().charAt(0);
        return new StringBuilder().append(firstNameInitial + lastNameInitial).toString();
    }

    private void save(final String insertQuery) {
        final UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
        final UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, TDB_BASE_URL);
        updateProcessor.execute();
    }

}
