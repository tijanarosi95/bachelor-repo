package com.ftn.anticancerdrugrecord.util;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

import java.io.File;

@Component
public class LoadOntologyUtility {

    private static final String ONTOLOGY_PATH = "src/main/resources/ontology/drugs.owl";
    private static final String URI = "http://www.ftn.uns.ac.rs/drugs#";
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    @Autowired
    private OntologyFactory factory;

    public void testOntology() throws OWLOntologyCreationException {
        final OWLOntologyManager manager = factory.getOntologyManager();
        final OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(ONTOLOGY_PATH));
        final OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();

        final OWLReasoner reasoner = reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
        final Version v = reasoner.getReasonerVersion();
        System.out.println("reasoner " + reasoner.getReasonerName()+ " " + v.getMajor()+ "." + v.getMinor() + "." + v.getPatch() + " build " + v.getBuild());

        final OWLDataFactory factory = manager.getOWLDataFactory();

        final PrefixManager pm = new DefaultPrefixManager(URI);

        final OWLClass personClass = factory.getOWLClass(":Person", pm);

        for (OWLNamedIndividual person : reasoner.getInstances(personClass, false).getFlattened()) {
            System.out.println("person : " + renderer.render(person));
        }
    }
}
