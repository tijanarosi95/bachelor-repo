package com.ftn.anticancerdrugrecord.service.person.impl;

import com.ftn.anticancerdrugrecord.dto.patient.PatientUpdateDTO;
import com.ftn.anticancerdrugrecord.model.person.Person;
import com.ftn.anticancerdrugrecord.service.person.PersonServiceInterface;
import com.ftn.anticancerdrugrecord.util.InsertUtility;
import com.ftn.anticancerdrugrecord.util.SelectPatientUtility;
import com.ftn.anticancerdrugrecord.util.UpdatePatientUtility;
import java.util.List;
import java.util.Optional;
import org.apache.jena.shared.JenaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements PersonServiceInterface {

    @Autowired
    private InsertUtility insertUtility;

    @Autowired
    private SelectPatientUtility selectUtility;

    @Autowired
    private UpdatePatientUtility updateUtility;

    @Override
    public void createPerson(final Person person) {
        try {
            if (person != null) {
                insertUtility.insertPerson(person);
                if (person.getLifeQuality() != null) {
                    insertUtility.insertLifeQuality(person.getLifeQuality());
                }
            }
        } catch (JenaException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Person> getPersonById(final String id) {
        return selectUtility.loadPatientById(id);
    }

    @Override
    public List<Person> getPersonsTreatedByDrug(final String drugName) {
        return selectUtility.loadPatientsByTreatedDrug(drugName);
    }

    @Override
    public boolean updatePerson(PatientUpdateDTO patient) {
        return updateUtility.updatePatient(patient);
    }

    @Override
    public boolean deletePerson(String jmbg) {
        return updateUtility.removePatient(jmbg);
    }
}
