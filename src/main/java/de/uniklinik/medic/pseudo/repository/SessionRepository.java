package de.uniklinik.medic.pseudo.repository;

import de.uniklinik.medic.pseudo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jonathan on 11.Apr.2020 . 10:05 PM
 */
public interface SessionRepository extends JpaRepository<Session, Long> {


}
