package de.uniklinik.medic.pseudo.repository;

import de.uniklinik.medic.pseudo.entity.Pseudonym;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jonathan on 26.Apr.2020 . 9:06 PM
 */
public interface PseudonymRepository extends JpaRepository<Pseudonym, Long> {
}
