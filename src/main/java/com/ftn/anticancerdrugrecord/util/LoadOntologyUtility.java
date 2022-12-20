package com.ftn.anticancerdrugrecord.util;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class LoadOntologyUtility {

    private static final String ONTOLOGY_PATH = "src/main/resources/ontology/drugs.owl";
    private static final String URI = "http://www.ftn.uns.ac.rs/drugs#";
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    @Autowired
    private OntologyFactory ontologyFactory;

    @Autowired
    private ReasonerFactory reasonerFactory;

    private OWLOntology loadOntologyFromFile() throws OWLOntologyCreationException {
        final OWLOntologyManager manager = ontologyFactory.getOntologyManager();
        return manager.loadOntologyFromOntologyDocument(new File(ONTOLOGY_PATH));
    }

    private OWLDataFactory loadOntologyDataFactory() {
        final OWLOntologyManager manager = ontologyFactory.getOntologyManager();
        return manager.getOWLDataFactory();
    }

    private OWLReasoner loadReasoner(final OWLOntology ontology) {
        return reasonerFactory.getReasoner(ontology);
    }

    public void testOntology() throws OWLOntologyCreationException {
        final OWLOntologyManager manager = ontologyFactory.getOntologyManager();
        final OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(ONTOLOGY_PATH));
        final OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();

        final OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
        final Version version = reasoner.getReasonerVersion();
        System.out.println("reasoner " + reasoner.getReasonerName()+ " " + version.getMajor()+ "." +
                version.getMinor() + "." +
                version.getPatch() + " build " + version.getBuild());

        final OWLDataFactory factory = manager.getOWLDataFactory();

        final PrefixManager pm = new DefaultPrefixManager(URI);

        final OWLClass personClass = factory.getOWLClass(":Person", pm);

        final Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectProperties = new HashMap<>();
        final Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataProperties = new HashMap<>();

        for (OWLNamedIndividual person : reasoner.getInstances(personClass, false).getFlattened()) {
            System.out.println("person : " + renderer.render(person));
            objectProperties.putAll(person.getObjectPropertyValues(ontology));
            dataProperties.putAll(person.getDataPropertyValues(ontology));
        }

        System.out.println("========== Object properties ==================");
        System.out.println("======================================================");
        objectProperties.forEach((k, v) -> {
            System.out.println("Object Key: " + renderer.render(k));
            v.forEach(i -> System.out.println("Object Value " + renderer.render(i)));
        });
        System.out.println("============== Data properties ===================");
        System.out.println("======================================================");
        dataProperties.forEach((k, v) -> {
            System.out.println("Data Key: " + renderer.render(k));
            v.forEach(i -> System.out.println("Data Value " + renderer.render(i)));
        });
    }
}
