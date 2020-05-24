package de.uniklinik.medic.pseudo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jonathan on 25.Apr.2020 . 6:23 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequest {

    private String type;
    private TokenRequestData data;

    public TokenRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TokenRequestData getData() {
        return data;
    }

    public void setData(TokenRequestData data) {
        this.data = data;
    }
}
