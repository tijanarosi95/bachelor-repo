package com.ftn.anticancerdrugrecord.model.person;

public enum PatientWithDiseaseType {

    PATIENT_WITH_DISEASE_PROGRESSION("PatientWithDiseaseProgression"),
    PATIENT_WITH_DISEASE_RECURRENCE("PatientWithDiseaseRecurrence"),
    PATIENT_WITH_DISEASE_REMISSION("PatientWithDiseaseRemission");

    private String type;

    public String type() {
        return this.type;
    }

    PatientWithDiseaseType(String type) {
        this.type = type;
    }
}
