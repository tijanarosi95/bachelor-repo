package com.ftn.anticancerdrugrecord.model.person;

public enum PatientWithSymptomType {

    PATIENT_WITH_SYMPTOM_EXACERBATED("PatientWithSymptomExacerbated"),
    PATIENT_WITH_SYMPTOM_IMPROVED("PatientWithSymptomImproved"),
    PATIENT_WITH_SYMPTOM_UNCHANGED("PatientWithSymptomUnchanged");

    private String type;

    public String type() {
        return this.type;
    }

    PatientWithSymptomType(String type) {
        this.type = type;
    }
}
