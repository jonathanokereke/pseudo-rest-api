package de.uniklinik.medic.pseudo.repository;

import de.uniklinik.medic.pseudo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jonathan on 26.Apr.2020 . 8:08 PM
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {


}
