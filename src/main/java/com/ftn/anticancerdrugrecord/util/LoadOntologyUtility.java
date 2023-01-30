package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import com.ftn.anticancerdrugrecord.model.person.CustomPerson;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.OntModel;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;

import java.io.File;
import java.util.Set;

@Component
public class LoadOntologyUtility implements LoadOntologyInterface {

    private static final String ONTOLOGY_PATH = "src/main/resources/ontology/drugs.owl";
    private static final String URI = "http://www.ftn.uns.ac.rs/drugs#";
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    @Autowired
    private OntologyFactory ontologyFactory;

    @Autowired
    private ReasonerFactory reasonerFactory;

    @Override
    public void loadPersons() throws OWLOntologyCreationException {
        final OWLOntology ontology = loadOntologyFromFile();
        final OWLReasoner reasoner = loadReasoner(ontology);
        final OWLDataFactory factory = loadOntologyDataFactory();
        final OWLClass personClass = loadIndividualByType(":Person", factory);

        final List<CustomPerson> loadedPersons = new ArrayList<>();

        final Set<OWLNamedIndividual> personIndividuals = reasoner.getInstances(personClass, Boolean.FALSE).getFlattened();

        personIndividuals.forEach(person -> {
            final CustomPerson individual = new CustomPerson();
            individual.setInitials(renderer.render(person));
            individual.setDataProperties(person.getDataPropertyValues(ontology));
            individual.setObjectProperties(person.getObjectPropertyValues(ontology));
            loadedPersons.add(individual);
        });
        System.out.println("Loaded Person individuals : " + loadedPersons);
    }

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

    private OWLClass loadIndividualByType(final String type, final OWLDataFactory factory) {
        final PrefixManager pm = new DefaultPrefixManager(URI);
        return factory.getOWLClass(type, pm);
    }
}
