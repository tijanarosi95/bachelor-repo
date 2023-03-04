package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.drug.DrugUpdateDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientUpdateDTO;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class UpdateDrugUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";

    private static final String TDB_INSERT_BASE_URL = "http://localhost:3030/ds/update";

    public boolean updateDrug(final DrugUpdateDTO drug) {
        try {
            final String updateQuery = createUpdateQuery(drug);
            save(updateQuery);
            return true;
        } catch (JenaException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String createUpdateQuery(final DrugUpdateDTO drug) {
        return  "";
    }

    private void save(final String updateQuery) {
        System.out.println("UPDATE QUERY: " + updateQuery);
        final UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
        final UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, TDB_INSERT_BASE_URL);
        updateProcessor.execute();
    }
}
