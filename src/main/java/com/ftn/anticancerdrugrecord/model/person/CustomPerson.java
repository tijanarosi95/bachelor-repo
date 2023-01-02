package com.ftn.anticancerdrugrecord.model.person;

import lombok.Data;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

import java.util.Map;
import java.util.Set;

/** A custom representation of Person entity loaded
 *  from ontology which contains loaded data and
 *  object properties specific to this individual **/
@Data
public class CustomPerson {

    private String initials;
    private Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataProperties;
    private Map<OWLObjectPropertyExpression, Set<OWLIndividual>> objectProperties;
}
