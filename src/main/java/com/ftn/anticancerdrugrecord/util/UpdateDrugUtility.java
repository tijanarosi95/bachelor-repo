package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.dto.drug.DrugUpdateDTO;
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

    public boolean removeDrug(final String drugId) {
        try {
            final String deleteQuery = createDeleteQuery(drugId);
            save(deleteQuery);
            return true;
        } catch (JenaException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private String createUpdateQuery(final DrugUpdateDTO drug) {
        return  " PREFIX drg:" + DRUGS_URI +
                " DELETE { ?drug drg:isDoseRanged " + drug.getExistingData().isDoseRanged() + " ;" +
                " drg:hasEfficacy " + drug.getExistingData().isHasEfficacy() + " ;" +
                " drg:hasToxicity " + drug.getExistingData().isHasToxicity() + " ;" +
                " drg:hasSideEffects " + drug.getExistingData().isHasSideEffects() + " ;" +
                " drg:hasTherapeuticEffect " + drug.getExistingData().isHasTherapeuticEffect() + " ;" +
                " drg:hasApproved " + drug.getExistingData().isApproved() + " ;}" +
                " INSERT { ?drug drg:isDoseRanged " + drug.getNewData().isDoseRanged() + " ;" +
                " drg:hasEfficacy " + drug.getNewData().isHasEfficacy() + " ;" +
                " drg:hasToxicity " + drug.getNewData().isHasToxicity() + " ;" +
                " drg:hasSideEffects " + drug.getNewData().isHasSideEffects() + " ;" +
                " drg:hasTherapeuticEffect " + drug.getNewData().isHasTherapeuticEffect() + " ;" +
                " drg:hasApproved " + drug.getNewData().isApproved() + " ;}" +
                " WHERE { ?drug drg:drugID " + drug.getExistingData().getDrugId() + " ;} ";
    }

    private String createDeleteQuery(final String id) {
        return " PREFIX drg:" + DRUGS_URI +
               " DELETE { ?s ?p ?o } " +
               " WHERE { ?s ?p ?o ; drg:drugID " + id  + " ;} ";
    }

    private void save(final String updateQuery) {
        System.out.println("UPDATE QUERY: " + updateQuery);
        final UpdateRequest updateRequest = UpdateFactory.create(updateQuery);
        final UpdateProcessor updateProcessor = UpdateExecutionFactory.createRemote(updateRequest, TDB_INSERT_BASE_URL);
        updateProcessor.execute();
    }
}
