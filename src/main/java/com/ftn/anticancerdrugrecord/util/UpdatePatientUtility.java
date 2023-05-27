package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.patient.PatientUpdateDTO;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";

    private static final String TDB_INSERT_BASE_URL = "http://localhost:3030/ds/update";

    public boolean updatePatient(final PatientUpdateDTO patient) {
        try {
            final String updateQuery = createUpdateQuery(patient);
            save(updateQuery);
            return true;
        } catch (JenaException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removePatient(final String jmbg) {
        try {
            final String deleteQuery = createDeleteQuery(jmbg);
            save(deleteQuery);
            return true;
        } catch (JenaException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String createUpdateQuery(final PatientUpdateDTO patient) {
        return "PREFIX drg:" + DRUGS_URI +
               " DELETE { ?person drg:age " + patient.getExistingData().getAge()  + " ;" +
               " drg:isCancerSpread " + patient.getExistingData().isCancerSpread() + " ;" +
               " drg:isCancerGrown " + patient.getExistingData().isCancerGrown() + " ;" +
               " drg:isCancerSpreadToOrgans " + patient.getExistingData().isCancerSpreadToOrgans() + " ;" +
               " drg:weightLoss " + patient.getExistingData().isWeightLoss() + " ;" +
               " drg:strongPain " + patient.getExistingData().isStrongPain() + " ;" +
               " drg:isCancerReappear " + patient.getExistingData().isCancerReappear() + " ;" +
               " drg:isCancerDetectable " + patient.getExistingData().isCancerDetectable() + " ;" +
               " drg:lifeQuality '" + patient.getExistingData().getLifeQuality() + "';}" +
               " INSERT { ?person drg:age " + patient.getNewData().getAge()  + " ;" +
               " drg:isCancerSpread " + patient.getNewData().isCancerSpread() + " ;" +
               " drg:isCancerGrown " + patient.getNewData().isCancerGrown() + " ;" +
               " drg:isCancerSpreadToOrgans " + patient.getNewData().isCancerSpreadToOrgans() + " ;" +
               " drg:weightLoss " + patient.getNewData().isWeightLoss() + " ;" +
               " drg:strongPain " + patient.getNewData().isStrongPain() + " ;" +
               " drg:isCancerReappear " + patient.getNewData().isCancerReappear() + " ;" +
               " drg:isCancerDetectable " + patient.getNewData().isCancerDetectable() + " ;" +
               " drg:lifeQuality '" + patient.getNewData().getLifeQuality() + "' ;}" +
               " WHERE" +
               " { ?person drg:jmbg '" + patient.getExistingData().getJmbg() + "';}";
    }

    private String createDeleteQuery(final String jmbg) {
        return "PREFIX drg:" + DRUGS_URI +
        " DELETE { ?s ?p ?o } " +
        " WHERE { ?s ?p ?o ; drg:jmbg '" + jmbg + "' ;}";
    }

    private void save(final String updateQuery) {
        System.out.println("UPDATE QUERY: " + updateQuery);
        final UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
        final UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, TDB_INSERT_BASE_URL);
        updateProcessor.execute();
    }
}
