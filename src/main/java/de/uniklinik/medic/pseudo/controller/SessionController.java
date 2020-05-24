package de.uniklinik.medic.pseudo.controller;

import de.uniklinik.medic.pseudo.dto.SessionResponse;
import de.uniklinik.medic.pseudo.entity.Session;
import de.uniklinik.medic.pseudo.handler.MainzellisteHandler;
import de.uniklinik.medic.pseudo.repository.SessionRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by Jonathan on 11.Apr.2020 . 8:54 PM
 */

//TODO Use Gateway or central service to control CORS
//Lets allow access from FrontendApplication, 8081
@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@RequestMapping("/api/v2/pseudo-service")
public class SessionController {

    //Generate serviceId for this Client/Service via Authn Provider
    @Value( "${server.serviceId}" )
    private String serviceId;

    //Server Properties
    @Value( "${server.host}" )
    private String serverHost;

    HttpEntity<String> httpEntity = new MainzellisteHandler().createHttpHeaders();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MessageSource messageSource;

    //CREATE SESSION
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public SessionResponse createSession() {

        String jsonString = restTemplate.exchange(
                serverHost+"/sessions", HttpMethod.POST, httpEntity, String.class
        ).getBody();

        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);

            String sessionId = (String) jsonObject.get("sessionId");

            //Store sessionId in DB
            Session session = new Session();
            session.setServiceId(serviceId);
            session.setSessionId(sessionId);
            session.setCreatedAt(Instant.now().getEpochSecond());

            sessionRepository.save(session);

            //return new ResponseEntity<>(messageSource.getMessage("session.created", null, Locale.ENGLISH), HttpStatus.CREATED);

            return new SessionResponse(sessionId);

        } catch (ParseException ex) {

            //TODO Handle Exception instead of Stacktrace
            ex.printStackTrace();
        }

        return null;

    }

    //READ SESSION
    @RequestMapping(value = "/sessions/{sessionId}", method = RequestMethod.GET)
    public Object getSession(@PathVariable("sessionId") String sessionId) {

        try{

            restTemplate.exchange(
                    serverHost+"/sessions/"+sessionId,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            ).getStatusCode();

            //If no Error is caught, then continue...
        }
        catch (HttpClientErrorException | HttpServerErrorException httpError){

            return new ResponseEntity<>(messageSource.getMessage("session.notFound", null, Locale.ENGLISH), HttpStatus.NOT_FOUND);

        }


        String session = restTemplate.exchange(
                serverHost+"/sessions/" + sessionId, HttpMethod.GET, httpEntity, String.class
        ).getBody();

        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(session);

            String sessionId2 = (String) jsonObject.get("sessionId");

            return new SessionResponse(sessionId2);
        }
        //TODO Create Exception handler instead of stacktrace
        catch (ParseException ex) {

            ex.printStackTrace();
        }

        return null;
    }

    //DELETE SESSION
    @RequestMapping(value = "/sessions/{sessionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSession(@PathVariable("sessionId") String sessionId) {

        //TODO
        //Check if session exist before attempting delete.
        //Return 204 -No Content-, if no available session

        restTemplate.exchange(
                serverHost+"/sessions/"+ sessionId, HttpMethod.DELETE, httpEntity, String.class
        ).getBody();

        return new ResponseEntity<>(messageSource.getMessage("session.deleted", null, Locale.ENGLISH), HttpStatus.OK);

    }



}
