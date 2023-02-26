package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import com.ftn.anticancerdrugrecord.dto.disease.DiseaseDTO;
import com.ftn.anticancerdrugrecord.dto.drug.ApprovedDrug;
import com.ftn.anticancerdrugrecord.dto.drug.ClinicallyTestedDrugPhase1;
import com.ftn.anticancerdrugrecord.dto.drug.ClinicallyTestedDrugPhase2;
import com.ftn.anticancerdrugrecord.dto.drug.ClinicallyTestedDrugPhase3;
import com.ftn.anticancerdrugrecord.dto.drug.DrugDTO;
import com.ftn.anticancerdrugrecord.dto.drug.DrugEffectsDTO;
import com.ftn.anticancerdrugrecord.dto.drug.PreclinicallyTestedDrug;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiagnosisCarcinoma;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiagnosisMetastaticCancer;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiagnosisStageICancer;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiagnosisStageIICancer;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiseaseProgression;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiseaseRecurrence;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiseaseRemission;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptomExacerbated;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptomImproved;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptomUnchanged;
import com.ftn.anticancerdrugrecord.model.person.Person;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.util.PrintUtil;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

@Component
public class InferOntologyFacts implements OntologyUtilityInterface {

    private static final String ONTOLOGY_PATH = "file:src/main/resources/drugs.owl";
    private static final String NS = "http://www.ftn.uns.ac.rs/drugs";
    private static final String DISEASE_RECURRENCE = "PatientWithDiseaseRecurrence";
    private static final String DISEASE_PROGRESSION = "PatientWithDiseaseProgression";
    private static final String DISEASE_REMISSION = "PatientWithDiseaseRemission";

    private static final String SYMPTOM_UNCHANGED = "PatientWithSymptomUnchanged";
    private static final String SYMPTOM_EXACERBATED = "PatientWithSymptomExacerbated";
    private static final String SYMPTOM_IMPROVED = "PatientWithSymptomImproved";

    private static final String DIAGNOSIS_METASTATIC_CANCER = "PatientWithDiagnosisMetastaticCancer";
    private static final String DIAGNOSIS_CARCINOMA = "PatientWithDiagnosisCarcinoma";
    private static final String DIAGNOSIS_STAGE_I_CANCER = "PatientWithDiagnosisStageICancer";
    private static final String DIAGNOSIS_STAGE_II_CANCER = "PatientWithDiagnosisStageIICancer";

    private static final String PRECLINICALLY_TESTED_DRUG = "PreclinicallyTestedDrug";
    private static final String CLINICALLY_PHASE_I_TESTED_DRUG = "ClinicallyTestedDrugPhase1";
    private static final String CLINICALLY_PHASE_II_TESTED_DRUG = "ClinicallyTestedDrugPhase2";
    private static final String CLINICALLY_PHASE_III_TESTED_DRUG = "ClinicallyTestedDrugPhase3";
    private static final String APPROVED_DRUG = "ApprovedDrug";

    private static final String PERSON_OWL_CLASS = "#Person";
    private static final String HAS_DISEASE = "#hasDisease";
    private static final String WEIGHT_LOSS = "#weightLoss";
    private static final String STRONG_PAIN = "#strongPain";
    private static final String CANCER_SPREAD_TO_ORGANS = "#hasCancerSpreadToOrgans";
    private static final String CANCER_SPREAD = "#hasCancerSpread";
    private static final String CANCER_REAPPEARED = "#hasCancerReappear";
    private static final String CANCER_DETECTABLE = "#hasCancerDetectable";
    private static final String LIFE_QUALITY = "#lifeQuality";
    private static final String CANCER_GROWN = "#hasCancerGrown";

    private static final String DRUG_OWL_CLASS = "#Drug";
    private static final String DOSE_RANGED = "#isDoseRanged";
    private static final String HAS_EFFICACY = "#hasEfficacy";
    private static final String HAS_TOXICITY = "#hasToxicity";
    private static final String HAS_SIDE_EFFECTS = "#hasSideEffects";
    private static final String HAS_THERAPEUTIC_EFFECT = "#hasTherapeuticEffect";
    private static final String HAS_APPROVED = "#isApproved";

    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    @Autowired
    private OntologyFactory ontologyFactory;

    @Autowired
    private ReasonerFactory reasonerFactory;

    @Override
    public PatientDTO inferPersonFacts(final Person person){
        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        model.read(ONTOLOGY_PATH);

        final String personInitials = getPersonInitials(person.getFirstName(), person.getLastName());
        Resource personResource = model.createResource(NS + "#" + personInitials);
        Resource diseaseResource = model.createResource(NS + "#" + person.getHasDisease().getName());

        personResource.addProperty(RDF.type, model.getResource(NS + PERSON_OWL_CLASS));
        personResource.addProperty(model.getProperty(NS + HAS_DISEASE), diseaseResource);
        personResource.addProperty(model.getProperty(NS + WEIGHT_LOSS), ResourceFactory.createTypedLiteral(person.isWeightLoss()));
        personResource.addProperty(model.getProperty(NS + STRONG_PAIN), ResourceFactory.createTypedLiteral(person.isStrongPain()));
        personResource.addProperty(model.getProperty(NS + CANCER_SPREAD_TO_ORGANS), ResourceFactory.createTypedLiteral(person.isCancerSpreadToOrgans()));
        personResource.addProperty(model.getProperty(NS + CANCER_SPREAD), ResourceFactory.createTypedLiteral(person.isCancerSpread()));
        personResource.addProperty(model.getProperty(NS + CANCER_REAPPEARED), ResourceFactory.createTypedLiteral(person.isCancerReappear()));
        personResource.addProperty(model.getProperty(NS + CANCER_DETECTABLE), ResourceFactory.createTypedLiteral(person.isCancerDetectable()));
        personResource.addProperty(model.getProperty(NS + CANCER_GROWN), ResourceFactory.createTypedLiteral(person.isCancerGrown()));
        personResource.addProperty(model.getProperty(NS + LIFE_QUALITY), model.getResource(NS + "#" + person.getLifeQuality()));

        StmtIterator iter = model.listStatements(personResource, (Property) null, (RDFNode) null);

        var patient = new PatientDTO(person.getJmbg(), person.getFirstName(), person.getLastName(),
                   person.getGender(), person.getAge(), new DiseaseDTO(person.getHasDisease()));

        while (iter.hasNext()) {
            var triple = iter.next();
            createPatientDtoFromInferredFacts(patient, triple);
        }
        return patient;
    }

    @Override
    public DrugDTO inferDrugFacts(DrugEffectsDTO drugEffects) {
        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        model.read(ONTOLOGY_PATH);

        Resource drugResource = model.createResource(NS + "#" + drugEffects.getDrugId());

        drugResource.addProperty(RDF.type, model.getResource(NS + DRUG_OWL_CLASS));
        drugResource.addProperty(model.getProperty(NS + HAS_TOXICITY), ResourceFactory.createTypedLiteral(drugEffects.isHasToxicity()));
        drugResource.addProperty(model.getProperty(NS + HAS_EFFICACY), ResourceFactory.createTypedLiteral(drugEffects.isHasEfficacy()));
        drugResource.addProperty(model.getProperty(NS + HAS_SIDE_EFFECTS), ResourceFactory.createTypedLiteral(drugEffects.isHasSideEffects()));
        drugResource.addProperty(model.getProperty(NS + HAS_THERAPEUTIC_EFFECT), ResourceFactory.createTypedLiteral(drugEffects.isHasTherapeuticEffect()));
        drugResource.addProperty(model.getProperty(NS + DOSE_RANGED), ResourceFactory.createTypedLiteral(drugEffects.isDoseRanged()));
        drugResource.addProperty(model.getProperty(NS + HAS_APPROVED), ResourceFactory.createTypedLiteral(drugEffects.isApproved()));

        StmtIterator iter = model.listStatements(drugResource, (Property) null, (RDFNode) null);
        var drug = new DrugDTO(drugEffects.getDrugId(), drugEffects.getDrugName(), drugEffects.getActiveIngredient());

        while (iter.hasNext()) {
            var triple = iter.next();
            createDrugDtoFromInferredFacts(drug, triple);
        }
        return drug;
    }

    public void inferPersonFactsByReasoner(final Person person) {
        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        model.read(ONTOLOGY_PATH);

        Resource johnDoe = model.createResource(NS + "#JD");
        johnDoe.addProperty(RDF.type, model.getResource(NS + "#Person"));
        johnDoe.addProperty(model.getProperty(NS + "#hasDisease"), model.getResource(NS + "#InvasiveDuctalCarcinoma"));

        StmtIterator iter = model.listStatements(johnDoe, (Property) null, (RDFNode) null);
        while (iter.hasNext()) {
            var triple = iter.next();
            var subject = triple.getSubject();
            var object = triple.getObject();
            var predicate = triple.getPredicate();

            System.out.println(" Subject- " + PrintUtil.print(subject));
            System.out.println(" Predicate- " + PrintUtil.print(predicate));
            System.out.println(" Object- " + PrintUtil.print(object));
        }
    }

    private String getPersonInitials(final String fistName, final String lastName) {
        final String firstNameInitial = String.valueOf(fistName.charAt(0));
        final String lastNameInitial = String.valueOf(lastName.charAt(0));
        return firstNameInitial + lastNameInitial;
    }

    private void createPatientDtoFromInferredFacts(final PatientDTO patient, final Statement stmt) {
        var object = stmt.getObject();
        if (!object.isURIResource()) return;
        var objectValue = StringUtils.substringAfterLast(object.asResource().getURI(), "#");

        setPatientDiagnosis(patient, objectValue);
        setPatientDiseaseCourse(patient, objectValue);
        setPatientSymptoms(patient, objectValue);
    }

    private void createDrugDtoFromInferredFacts(final DrugDTO drug, final Statement stmt) {
        var object = stmt.getObject();
        if (!object.isURIResource()) return;
        var objectValue = StringUtils.substringAfterLast(object.asResource().getURI(), "#");

        setDrugType(drug, objectValue);
    }

    private void setPatientSymptoms(final PatientDTO patient, final String objectValue) {
        switch (objectValue) {
            case SYMPTOM_UNCHANGED:
                patient.setSymptoms(new PatientWithSymptomUnchanged());
                break;
            case SYMPTOM_EXACERBATED:
                patient.setSymptoms(new PatientWithSymptomImproved());
                break;
            case SYMPTOM_IMPROVED:
                patient.setSymptoms(new PatientWithSymptomExacerbated());
                break;
        }
    }

    private void setPatientDiseaseCourse(final PatientDTO patient, final String objectValue) {
        switch (objectValue) {
            case DISEASE_RECURRENCE:
                patient.setDiseaseCourse(new PatientWithDiseaseRecurrence());
                break;
            case DISEASE_PROGRESSION:
                patient.setDiseaseCourse(new PatientWithDiseaseProgression());
                break;
            case DISEASE_REMISSION:
                patient.setDiseaseCourse(new PatientWithDiseaseRemission());
                break;
        }
    }

    private void setPatientDiagnosis(final PatientDTO patient, final String objectValue) {
        switch (objectValue) {
            case DIAGNOSIS_CARCINOMA:
                patient.setDiagnosis(new PatientWithDiagnosisCarcinoma());
                break;
            case DIAGNOSIS_METASTATIC_CANCER:
                patient.setDiagnosis(new PatientWithDiagnosisMetastaticCancer());
                break;
            case DIAGNOSIS_STAGE_I_CANCER:
                patient.setDiagnosis(new PatientWithDiagnosisStageICancer());
                break;
            case DIAGNOSIS_STAGE_II_CANCER:
                patient.setDiagnosis(new PatientWithDiagnosisStageIICancer());
        }
    }

    private void setDrugType(final DrugDTO drug, final String objectValue) {
        switch (objectValue) {
            case APPROVED_DRUG:
                drug.setApprovedDrug(new ApprovedDrug());
            case CLINICALLY_PHASE_III_TESTED_DRUG:
                drug.setClinicalTestedDrugPhase3(new ClinicallyTestedDrugPhase3("PHASE_3"));
            case CLINICALLY_PHASE_II_TESTED_DRUG:
                drug.setClinicalTestedDrugPhase2(new ClinicallyTestedDrugPhase2("PHASE_2"));
            case CLINICALLY_PHASE_I_TESTED_DRUG:
                drug.setClinicalTestedDrugPhase1(new ClinicallyTestedDrugPhase1("PHASE_1"));
            case PRECLINICALLY_TESTED_DRUG:
                drug.setPreclinicalTestedDrug(new PreclinicallyTestedDrug("PRECLINICAL_PHASE"));
        }
    }
}
