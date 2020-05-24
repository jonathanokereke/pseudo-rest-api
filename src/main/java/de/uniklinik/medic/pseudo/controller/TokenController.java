package de.uniklinik.medic.pseudo.controller;

import de.uniklinik.medic.pseudo.dto.TokenRequest;
import de.uniklinik.medic.pseudo.dto.TokenRequestData;
import de.uniklinik.medic.pseudo.dto.TokenResponse;
import de.uniklinik.medic.pseudo.entity.Token;
import de.uniklinik.medic.pseudo.repository.TokenRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Jonathan on 25.Apr.2020 . 6:17 PM
 */

@RestController
@RequestMapping("/api/v2/pseudo-service")
public class TokenController {


    private static final String GET_SESSION_ENDPOINT_URL = "http://localhost:8080/sessions/";
    private static final String CREATE_TOKEN_ENDPOINT_URL = "http://localhost:8080/sessions/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MessageSource messageSource;

    //CREATE TOKEN
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/sessions/{sessionId}/tokens", method = RequestMethod.POST)
    public Object createToken(@PathVariable("sessionId") String sessionId) {

        //TODO only for testing - refactor with Handler
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set("mainzellisteApiKey", "X3TVeiOPMjw3ZZrh5Y6ZAm76XhAv2wGg");

        String[] values = {"pid", "intid"};
        String tokenType = "addPatient";

        TokenRequestData data = new TokenRequestData();
        data.setIdtypes(values);

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setType(tokenType);
        tokenRequest.setData(data);

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(tokenRequest, httpHeaders);

        //Check if session is valid
        try{

            restTemplate.exchange(
                    GET_SESSION_ENDPOINT_URL+sessionId,
                    HttpMethod.GET,
                    httpEntity,
                    String.class
            ).getStatusCode();

            //If no Error is caught, then continue...

            String jsonToken = restTemplate.exchange(
                    CREATE_TOKEN_ENDPOINT_URL + sessionId + "/tokens",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            ).getBody();

            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonToken);

                String tokenId = (String) jsonObject.get("tokenId");

                //Persist token in DB
                Token token = new Token();
                token.setTokenId(tokenId);
                token.setType(tokenType);
                token.setCreatedAt(Instant.now().getEpochSecond());

                tokenRepository.save(token);

                return new TokenResponse(tokenId, tokenType);
            }
            //TODO Handle exception here better
            catch (ParseException ex) {
                ex.printStackTrace();
            }

        }
        catch (HttpClientErrorException | HttpServerErrorException HttpError)
        {
            return new ResponseEntity<>(messageSource.getMessage("session.notFound", null, Locale.ENGLISH), HttpStatus.NOT_FOUND);
        }

        return null;

    }

}
