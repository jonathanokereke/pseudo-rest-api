package de.uniklinik.medic.pseudo.dto;

/**
 * Created by Jonathan on 26.Apr.2020 . 8:53 PM
 */
public class PseudonymResponse {

    private String idType;
    private String idString;
    private String tentative;
    private String uri;

    public PseudonymResponse() {
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getTentative() {
        return tentative;
    }

    public void setTentative(String tentative) {
        this.tentative = tentative;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
