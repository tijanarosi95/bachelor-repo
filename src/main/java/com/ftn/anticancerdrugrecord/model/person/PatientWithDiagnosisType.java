package com.ftn.anticancerdrugrecord.model.person;

public enum PatientWithDiagnosisType {

    PATIENT_WITH_DIAGNOSIS_CARCINOMA("PatientWithDiagnosisCarcinoma"),
    PATIENT_WITH_DIAGNOSIS_METASTATIC_CANCER("PatientWithDiagnosisMetastaticCancer"),
    PATIENT_WITH_DIAGNOSIS_STAGE_I_CANCER("PatientWithDiagnosisStageICancer"),
    PATIENT_WITH_DIAGNOSIS_STAGE_II_CANCER("PatientWithDiagnosisStageIICancer");

    public final String type;

    public String type() {
        return this.type;
    }

    PatientWithDiagnosisType(String type) {
        this.type = type;
    }
}
