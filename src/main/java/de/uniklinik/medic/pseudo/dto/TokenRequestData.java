package de.uniklinik.medic.pseudo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jonathan on 25.Apr.2020 . 6:30 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequestData {

    private String[] idtypes;

    public TokenRequestData() {
    }

    public String[] getIdtypes() {
        return idtypes;
    }

    public void setIdtypes(String[] idtypes) {
        this.idtypes = idtypes;
    }
}
