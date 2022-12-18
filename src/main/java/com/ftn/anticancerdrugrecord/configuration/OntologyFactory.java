package com.ftn.anticancerdrugrecord.configuration;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.springframework.stereotype.Component;

@Component
public class OntologyFactory {

    public OWLOntologyManager getOntologyManager() {
        return OWLManager.createOWLOntologyManager();
    }

}
