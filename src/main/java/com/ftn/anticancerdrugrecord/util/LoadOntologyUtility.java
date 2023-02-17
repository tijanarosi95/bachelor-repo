package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDiseaseDTO;
import com.ftn.anticancerdrugrecord.model.person.CustomPerson;
import java.util.ArrayList;
import java.util.List;
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

    public void addPersonsAxiomsTest(final PatientDiseaseDTO patient) throws OWLOntologyCreationException {
        final OWLOntology ontology = loadOntologyFromFile();
        final OWLReasoner reasoner = loadReasoner(ontology);
        final OWLDataFactory factory = loadOntologyDataFactory();

        final OWLOntologyManager manager = ontologyFactory.getOntologyManager();

        final DefaultPrefixManager pm = new DefaultPrefixManager();
        pm.setDefaultPrefix(URI);

        OWLClass personClass = factory.getOWLClass(":Person", pm);
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(personClass));

        final String formattedPersonIndividual = String.format(":%s", patient.getFirstName() + patient.getLastName());
        final String formattedDiseaseIndividual = String.format(":%s", patient.getDisease().getName());

        final OWLNamedIndividual person = createIndividual(ontology, pm, manager, formattedPersonIndividual);
        final OWLNamedIndividual disease = createIndividual(ontology, pm, manager, formattedDiseaseIndividual);

        final OWLObjectProperty hasDiseaseProperty = createObjectProperty(ontology, pm, manager, ":hasDisease");

        //axiom - Added individual is a Person
        manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(personClass, person));
        //axiom - Added individual hasDisease Disease.name
        manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(hasDiseaseProperty, person, disease));

        //somehow need to save the axioms
    }

    protected OWLOntology loadOntologyFromFile() throws OWLOntologyCreationException {
        final OWLOntologyManager manager = ontologyFactory.getOntologyManager();
        return manager.loadOntologyFromOntologyDocument(new File(ONTOLOGY_PATH));
    }

    protected OWLDataFactory loadOntologyDataFactory() {
        final OWLOntologyManager manager = ontologyFactory.getOntologyManager();
        return manager.getOWLDataFactory();
    }

    protected OWLReasoner loadReasoner(final OWLOntology ontology) {
        return reasonerFactory.getReasoner(ontology);
    }

    private OWLClass loadIndividualByType(final String type, final OWLDataFactory factory) {
        final PrefixManager pm = new DefaultPrefixManager(URI);
        return factory.getOWLClass(type, pm);
    }

    private OWLNamedIndividual createIndividual(OWLOntology ontology, DefaultPrefixManager pm, OWLOntologyManager manager, String name) {
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(name, pm);
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(individual));
        return individual;
    }

    private OWLObjectProperty createObjectProperty(OWLOntology ontology, DefaultPrefixManager pm, OWLOntologyManager manager, String name) {
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(name, pm);
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(objectProperty));
        return objectProperty;
    }
}
