package com.ftn.anticancerdrugrecord.configuration;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.springframework.stereotype.Component;

@Component
public class ReasonerFactory {

    public OWLReasoner getReasoner(final OWLOntology ontology) {
        final OWLReasonerFactory reasonerFactory = PelletReasonerFactory.getInstance();
        return reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
    }
}
