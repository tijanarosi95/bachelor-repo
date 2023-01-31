package com.ftn.anticancerdrugrecord.util;

import com.ftn.anticancerdrugrecord.model.drug.Drug;
import com.ftn.anticancerdrugrecord.model.person.Person;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.apache.jena.base.Sys;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectUtility {

    private static final String DRUGS_URI = "<http://www.ftn.uns.ac.rs/drugs#>";
    private static final String RDF_URI = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    private static final String ONTOLOGY_PATH = "src/main/resources/ontology/drugs.owl";

    private static final String TDB_SELECT_BASE_URL = "http://localhost:3030/ds/query";

    @Autowired
    private LoadOntologyUtility ontologyUtility;

    public Optional<Drug> loadDrugById(final String id) {
        final InputStream in = FileManager.get().open(ONTOLOGY_PATH);
        final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, null);
        model.read(in, null);

        final String queryString =
                "PREFIX drg:" + DRUGS_URI + " " +
                "PREFIX rdf:" + RDF_URI + " " +
                "SELECT ?s ?o " +
                    "WHERE { " +
                        "?s drg:drugId ?o FILTER ( str(?o) = %s ) . }";
        final String formattedQueryString = String.format(queryString, id);

        final Query query = QueryFactory.create(formattedQueryString);

        QueryExecution queryExecution = null;
        try {
            // Execute the query and obtain the results
            queryExecution = QueryExecutionFactory.sparqlService(TDB_SELECT_BASE_URL, query);
            //queryExecution.execConstruct(model);

            final ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                System.out.println("Result from TDB: " + resultSet.next());
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred.");
        } finally {
            queryExecution.close();
        }
        return Optional.empty();
    }

    public Optional<Person> loadPatientById(final String id) {
        return Optional.empty();
    }

    public List<Drug> getDrugsByDiseaseType(final String type) {
        return null;
    }

    public List<Drug> getDrugsByPhaseType(final String phase) {
        return null;
    }

    /**Need to implement this example
     *
     * IRI iri = IRI.create(BASE_URL);
     * OntologyManager manager = OntManagers.createManager();
     * // Load an ontology
     * Ontology ontologyWithRules = manager.loadOntologyFromOntologyDocument(iri);
     *
     * // Instantiate a Hermit reasoner:
     * OWLReasonerFactory reasonerFactory = new ReasonerFactory();
     * OWLReasoner reasoner = reasonerFactory.createReasoner(ontologyWithRules);
     *
     * OWLDataFactory df = manager.getOWLDataFactory();
     *
     * // Create an inference generator over Hermit reasoner
     * InferredOntologyGenerator inference = new  InferredOntologyGenerator(reasoner);
     *
     * // Infer
     * inference.fillOntology(df, ontologyWithRules);
     *
     * // Query
     * try (
     *     QueryExecution qexec = QueryExecutionFactory.create(QueryFactory
     *         .create("SELECT ?s ?p ?o WHERE { ?s ?p ?o } "), ontologyWithRules.asGraphModel())
     * ) {
     *     ResultSet res = qexec.execSelect();
     *     while (res.hasNext()) {
     *         System.out.println(res.next());
     *     }
     * }**/

}
