package de.uniklinik.medic.pseudo.repository;

import de.uniklinik.medic.pseudo.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jonathan on 25.Apr.2020 . 6:17 PM
 */
public interface TokenRepository extends JpaRepository<Token, Long> {


}
