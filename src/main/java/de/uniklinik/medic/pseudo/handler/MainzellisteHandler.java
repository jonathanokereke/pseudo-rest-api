package de.uniklinik.medic.pseudo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Jonathan on 11.Apr.2020 . 9:16 PM
 */
public class MainzellisteHandler {

    @Autowired
    RestTemplate restTemplate;

    public HttpEntity<String> createHttpHeaders(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set("mainzellisteApiKey", "X3TVeiOPMjw3ZZrh5Y6ZAm76XhAv2wGg");

        HttpEntity<String> headers = new HttpEntity<String>(httpHeaders);

        return headers;
    }



}
