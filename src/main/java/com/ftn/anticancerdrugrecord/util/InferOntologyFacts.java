package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.configuration.OntologyFactory;
import com.ftn.anticancerdrugrecord.configuration.ReasonerFactory;
import com.ftn.anticancerdrugrecord.dto.patient.PatientDTO;
import com.ftn.anticancerdrugrecord.model.person.Person;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Property;
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
    private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

    @Autowired
    private OntologyFactory ontologyFactory;

    @Autowired
    private ReasonerFactory reasonerFactory;

    @Override
    public PatientDTO inferPersonFacts(final Person person){
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
}
