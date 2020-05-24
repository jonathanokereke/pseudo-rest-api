package de.uniklinik.medic.pseudo.dto;


/**
 * Created by Jonathan on 12.Apr.2020 . 11:44 AM
 */

public class SessionResponse{

    private String sessionId;

    public SessionResponse() {
    }

    public SessionResponse(String sessionId) {
        this.sessionId = sessionId;

    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
