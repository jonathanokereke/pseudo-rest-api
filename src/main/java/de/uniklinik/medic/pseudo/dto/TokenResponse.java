package de.uniklinik.medic.pseudo.dto;

/**
 * Created by Jonathan on 25.Apr.2020 . 6:10 PM
 */
public class TokenResponse {

    private String tokenId;
    private String tokenType;

    public TokenResponse() {
    }

    public TokenResponse(String tokenId, String tokenType) {
        this.tokenId = tokenId;
        this.tokenType = tokenType;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
