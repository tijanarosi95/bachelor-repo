package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDTO;
import com.ftn.anticancerdrugrecord.model.person.Person;
import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
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
    private static final String PERSON_OWL_CLASS = "#Person";
    private static final String HAS_DISEASE = "#hasDisease";
    private static final String WEIGHT_LOSS = "#weightLoss";
    private static final String STRONG_PAIN = "#strongPain";
    private static final String CANCER_SPREAD_TO_ORGANS = "#hasCancerSpreadToOrgans";
    private static final String CANCER_SPREAD = "#hasCancerSpread";
    private static final String CANCER_REAPPEARED = "#hasCancerReappear";
    private static final String CANCER_DETECTABLE = "#hasCancerDetectable";

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

        StmtIterator iter = model.listStatements(personResource, (Property) null, (RDFNode) null);

        while (iter.hasNext()) {
            var triple = iter.next();
            var subject = triple.getSubject();
            var object = triple.getObject();
            var predicate = triple.getPredicate();

            System.out.println(" Subject- " + PrintUtil.print(subject));
            System.out.println(" Predicate- " + PrintUtil.print(predicate));
            System.out.println(" Object- " + PrintUtil.print(object));
            System.out.println(" --------------------------------- " + StringUtils.substringAfterLast(predicate.getURI(), "#"));
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
            System.out.println(" --------------------------------- " + StringUtils.substringAfterLast(predicate.getURI(), "#"));
        }
    }

    private String getPersonInitials(final String fistName, final String lastName) {
        final String firstNameInitial = String.valueOf(fistName.charAt(0));
        final String lastNameInitial = String.valueOf(lastName.charAt(0));
        return firstNameInitial + lastNameInitial;
    }

    private Literal getResourceLiteral(final boolean value, final OntModel model) {
        Resource trueResource = model.createResource();
        trueResource.addLiteral(RDF.value, Boolean.TRUE);
        Resource falseResource = model.createResource();
        falseResource.addLiteral(RDF.value, Boolean.FALSE);
        return value ? trueResource.getProperty(RDF.value).getLiteral() : falseResource.getProperty(RDF.value).getLiteral();
    }
}
