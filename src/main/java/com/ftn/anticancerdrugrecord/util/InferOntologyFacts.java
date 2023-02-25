package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDTO;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiseaseProgression;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiseaseRecurrence;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithDiseaseRemission;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptom;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptomExacerbated;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptomImproved;
import com.ftn.anticancerdrugrecord.dto.patient.PatientWithSymptomUnchanged;
import com.ftn.anticancerdrugrecord.model.person.PatientWithDiseaseType;
import com.ftn.anticancerdrugrecord.model.person.PatientWithSymptomType;
import com.ftn.anticancerdrugrecord.model.person.Person;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
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


import static com.ftn.anticancerdrugrecord.model.person.PatientWithDiseaseType.PATIENT_WITH_DISEASE_RECURRENCE;

@Component
public class InferOntologyFacts implements OntologyUtilityInterface {

    private static final String ONTOLOGY_PATH = "file:src/main/resources/drugs.owl";
    private static final String NS = "http://www.ftn.uns.ac.rs/drugs";
    private static final String PERSON_OWL_CLASS = "#Person";
    private static final String HAS_DISEASE = "#hasDisease";
    private static final String WEIGHT_LOSS = "#weightLoss";
    private static final String STRONG_PAIN = "#strongPain";
    private static final String CANCER_SPREAD_TO_ORGANS = "#hasCancerSpreadToOrgans";
    private static final String CANCER_SPREAD = "#hasCancerSpread";
    private static final String CANCER_REAPPEARED = "#hasCancerReappear";
    private static final String CANCER_DETECTABLE = "#hasCancerDetectable";
    private static final String LIFE_QUALITY = "#lifeQuality";

    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    @Autowired
    private OntologyFactory ontologyFactory;

    @Autowired
    private ReasonerFactory reasonerFactory;

    @Override
    public PatientDTO inferPersonFacts(final Person person){
        OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
        model.read(ONTOLOGY_PATH);

        System.out.println("Person 1. " + person.isCancerSpreadToOrgans());
        System.out.println("Person 2. " + person.isCancerReappear());
        System.out.println("Person 3. " + person.isCancerDetectable());


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
        personResource.addProperty(model.getProperty(NS + LIFE_QUALITY), model.getResource(NS + "#" + person.getLifeQuality()));
        StmtIterator iter = model.listStatements(personResource, (Property) null, (RDFNode) null);
        var patient = new PatientDTO();

        while (iter.hasNext()) {
            var triple = iter.next();
            var subject = triple.getSubject();
            var object = triple.getObject();
            var predicate = triple.getPredicate();

            createPatientDtoFromInferredFacts(patient, triple);

            System.out.println(" Subject- " + PrintUtil.print(subject));
            System.out.println(" Predicate- " + PrintUtil.print(predicate));
            System.out.println(" Object- " + PrintUtil.print(object));
        }
        return null;
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

        switch (PatientWithDiseaseType.valueOf(objectValue)) {
            case PATIENT_WITH_DISEASE_RECURRENCE:
                var patientWithRecurrence = new PatientWithDiseaseRecurrence();
                patient.setDiseaseCourse(patientWithRecurrence);
            case PATIENT_WITH_DISEASE_PROGRESSION:
                var patientWithProgression = new PatientWithDiseaseProgression();
                patient.setDiseaseCourse(patientWithProgression);
            case PATIENT_WITH_DISEASE_REMISSION:
                var patientWithRemission = new PatientWithDiseaseRemission();
                patient.setDiseaseCourse(patientWithRemission);
        }
        switch (PatientWithSymptomType.valueOf(objectValue)) {
            case PATIENT_WITH_SYMPTOM_UNCHANGED:
                var patientWithChangedSymptom = new PatientWithSymptomUnchanged();
                patient.setSymptoms(patientWithChangedSymptom);
            case PATIENT_WITH_SYMPTOM_EXACERBATED:
                var patientWithExacerbatedSymptom = new PatientWithSymptomExacerbated();
                patient.setSymptoms(patientWithExacerbatedSymptom);
            case PATIENT_WITH_SYMPTOM_IMPROVED:
                var patientWithImprovedSymptom = new PatientWithSymptomImproved();
                patient.setSymptoms(patientWithImprovedSymptom);
        }
    }

    private Literal getResourceLiteral(final boolean value, final OntModel model) {
        Resource trueResource = model.createResource();
        trueResource.addLiteral(RDF.value, Boolean.TRUE);
        Resource falseResource = model.createResource();
        falseResource.addLiteral(RDF.value, Boolean.FALSE);
        return value ? trueResource.getProperty(RDF.value).getLiteral() : falseResource.getProperty(RDF.value).getLiteral();
    }
}
