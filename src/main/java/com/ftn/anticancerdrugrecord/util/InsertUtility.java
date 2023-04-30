package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDrugDTO;
import com.ftn.anticancerdrugrecord.model.disease.Disease;
import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.model.person.LifeQuality;
import com.ftn.anticancerdrugrecord.model.person.Person;
import java.util.HashSet;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InsertUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    private static final String XSD_URI = "<http://w3.org/2001/XMLSchema#>";

    private static final String TDB_INSERT_BASE_URL = "http://localhost:3030/ds/update";

    @Autowired
    private SelectDiseaseUtility selectUtility;

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
            if(drug.getMayTreat() != null && !drug.getMayTreat().isEmpty()) {
                drug.getMayTreat().forEach(disease -> {
                    final String insertQuery = createInsertQuery(disease);
                    save(insertQuery);
                });
            }
            final String insertQuery = createInsertQuery(drug);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    public void insertLifeQuality(final LifeQuality lifeQuality) {
        try {
            final String insertQuery = createInsertQuery(lifeQuality);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    public void insertPersonDisease(final PatientDiseaseDTO patientDisease) {
        try {
            final String insertQuery = createInsertQuery(patientDisease);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    public void insertPersonDrug(final PatientDrugDTO patientDrug) {
        try {
            final String insertQuery = createInsertQuery(patientDrug);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    public void insertDrugEffects(final DrugEffectsDTO drugEffects) {
        try {
            final String insertQuery = createInsertQuery(drugEffects);
            save(insertQuery);
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    private String createInsertQuery(final Person person) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " <http://www.ftn.uns.ac.rs/drugs#" + getPersonInitials(person.getFirstName(), person.getLastName()) + "> drg:firstName '" + person.getFirstName() + "'; "
                + " drg:lastName '" + person.getLastName() + "'; "
                + " drg:jmbg '" +  person.getJmbg() + "'; "
                + " drg:age " + person.getAge() + "; "
                + " drg:gender '" + person.getGender().toString() + "'; "
                + " drg:isCancerSpread " + person.isCancerSpread() + "; "
                + " drg:isCancerGrown " + person.isCancerGrown() + "; "
                + " drg:isCancerSpreadToOrgans " + person.isCancerSpreadToOrgans() + "; "
                + " drg:isCancerSpread " + person.isCancerSpread() + "; "
                + " drg:strongPain " + person.isStrongPain() + "; "
                + " drg:isCancerReappear " + person.isCancerReappear() + "; "
                + " drg:isCancerDetectable " + person.isCancerDetectable() + "; "
                + " drg:lifeQuality '" + person.getLifeQuality() + "'; "
                + " drg:weightLoss " + person.isWeightLoss() + "; "
                + " rdf:type " + "drg:Person " +
                " }";
    }

    private String createInsertQuery(final Disease disease) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " <http://www.ftn.uns.ac.rs/drugs#" + disease.getName().replaceAll("\\s+", "") + "> drg:name '" + disease.getName() + "'; "
                + " drg:id " + disease.getId() + " ; "
                + " rdf:type " + "drg:Disease "
                + " }";
    }

    private String createInsertQuery(final Drug drug) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " <http://www.ftn.uns.ac.rs/drugs#" + drug.getName().replaceAll("\\s+", "") + "> drg:activeIngredient '" + drug.getActiveIngredient() + "'; "
                + " drg:drugID " + drug.getDrugId() + ";"
                + createMayTreatStatements(drug.getMayTreat())
                + " rdf:type " + "drg:Drug "
                + " }";
    }

    private String createInsertQuery(final DrugEffectsDTO drugEffects) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " <http://www.ftn.uns.ac.rs/drugs#" + drugEffects.getDrugName() + "> drg:isDoseRanged " + drugEffects.isDoseRanged() + ";"
                + " drg:hasEfficacy " + drugEffects.isHasEfficacy() + ";"
                + " drg:hasToxicity " + drugEffects.isHasToxicity() + ";"
                + " drg:hasSideEffects " + drugEffects.isHasSideEffects() + ";"
                + " drg:hasTherapeuticEffect " + drugEffects.isHasTherapeuticEffect() + ";"
                + " drg:hasApproved " + drugEffects.isApproved() + " ; "
                + " }";
    }

    private String createInsertQuery(final LifeQuality lifeQuality) {
        return "PREFIX drg:" + DRUGS_URI + " " +
               "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " <http://www.ftn.uns.ac.rs/drugs#" + lifeQuality + "> rdf:type " + "drg:LifeQuality . "
                + " } ";
    }

    /** Doctor will add disease if disease is not present **/
    private String createInsertQuery(final PatientDiseaseDTO patientDisease) {
        var firstName = patientDisease.getFirstName();
        var lastName = patientDisease.getLastName();
        var diseaseName = patientDisease.getDisease().getName();
        var diseaseId = patientDisease.getDisease().getId();

        if (selectUtility.loadDiseaseById(patientDisease.getDisease().getId()).isPresent()) {
            return createInsertQueryDiseaseExist(firstName, lastName, diseaseName, diseaseId);
        }
        return createInsertQueryDiseaseNotExist(firstName, lastName, diseaseName, diseaseId);
    }

    private String createInsertQueryDiseaseExist(final String firstName, final String lastName, final String diseaseName, final Integer diseaseId) {
        return "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "INSERT DATA { "
                + " <http://www.ftn.uns.ac.rs/drugs#" + getPersonInitials(firstName, lastName) + "> drg:hasDisease '" + diseaseName + "'; "
                + " }";
    }

    private String createInsertQueryDiseaseNotExist(final String firstName, final String lastName, final String diseaseName, final Integer diseaseId) {
        return "PREFIX drg:" + DRUGS_URI + " " +
               "PREFIX rdf:" + RDF_URI + " " +
               "INSERT DATA { "
               + " <http://www.ftn.uns.ac.rs/drugs#" + getPersonInitials(firstName, lastName) + "> drg:hasDisease '" + diseaseName + "'. "
               + " <http://www.ftn.uns.ac.rs/drugs#" + diseaseName.replaceAll("\\s+", "") + "> drg:name '" + diseaseName + "'; "
               + " drg:id " + diseaseId + " ; "
               + " rdf:type " + "drg:Disease "
               + " }";
    }

    /** Doctor will add Drug by whom patient is treated **/
    private String createInsertQuery(PatientDrugDTO patientDrug) {
        var firstName = patientDrug.getFirstName();
        var lastName = patientDrug.getLastName();
        var drugName = patientDrug.getDrugName();
        return "PREFIX drg:" + DRUGS_URI + " " +
            "PREFIX rdf:" + RDF_URI +
            " INSERT DATA { "
            + " <http://www.ftn.uns.ac.rs/drugs#" + getPersonInitials(firstName, lastName) + "> drg:isTreatedWith '" + drugName + "'; "
            + " } ";
    }

    private String createMayTreatStatements(final Set<Disease> diseases) {
        var enteredDiseases = new HashSet<>(diseases);
        return enteredDiseases.stream()
                .map(Disease::getName)
                .map(item -> " drg:mayTreat '" + item + "'; ")
                .reduce(String::concat)
                .orElse("");
    }

    private String getPersonInitials(final String fistName, final String lastName) {
        final String firstNameInitial = String.valueOf(fistName.charAt(0));
        final String lastNameInitial = String.valueOf(lastName.charAt(0));
        return firstNameInitial + lastNameInitial;
    }

    private void save(final String insertQuery) {
        System.out.println("QUERY: " + insertQuery);
        final UpdateRequest updateRequest = UpdateFactory.create(insertQuery);
        final UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, TDB_INSERT_BASE_URL);
        updateProcessor.execute();
    }

}
