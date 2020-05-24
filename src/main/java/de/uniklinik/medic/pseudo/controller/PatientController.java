package de.uniklinik.medic.pseudo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniklinik.medic.pseudo.dto.PseudonymResponse;
import de.uniklinik.medic.pseudo.entity.Patient;
import de.uniklinik.medic.pseudo.entity.Pseudonym;
import de.uniklinik.medic.pseudo.repository.PatientRepository;
import de.uniklinik.medic.pseudo.repository.PseudonymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jonathan on 25.Apr.2020 . 8:26 PM
 */

@RestController
@RequestMapping("/api/v2/pseudo-service")
public class PatientController {

    //TODO add to Application property file/bean for all App propertieso
    private static final double mainzellisteApiVersion = 3.0;
    private static final String CREATE_PATIENT_ENDPOINT_URL = "http://localhost:8080/patients/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PseudonymRepository pseudonymRepository;

    @Autowired
    private MessageSource messageSource;

    //CREATE PATIENT
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/tokens/{tokenId}/patients", method = RequestMethod.POST)
    public ResponseEntity<Object> createPatient(
                                    @RequestParam String vorname,
                                                String nachname,
                                                String geburtsname,
                                                String geburtstag,
                                                String geburtsmonat,
                                                String geburtsjahr,
                                                String plz,
                                                String ort,
                                    @PathVariable("tokenId") String tokenId){
        Patient patient = new Patient();
        patient.setVorname(vorname);
        patient.setNachname(nachname);
        patient.setGeburtsname(geburtsname);
        patient.setGeburtstag(geburtstag);
        patient.setGeburtsmonat(geburtsmonat);
        patient.setGeburtsjahr(geburtsjahr);
        patient.setPlz(plz);
        patient.setOrt(ort);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("vorname", vorname);
        parameters.add("nachname", nachname);
        parameters.add("geburtsname", geburtsname);
        parameters.add("geburtstag", geburtstag);
        parameters.add("geburtsmonat", geburtsmonat);
        parameters.add("geburtsjahr", geburtsjahr);
        parameters.add("plz", plz);
        parameters.add("ort", ort);

        HttpMessageConverter<?> formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter<?> stringHttpMessageConverter = new StringHttpMessageConverter();
        List<HttpMessageConverter> msgConverters = new ArrayList<HttpMessageConverter>();
        msgConverters.add(formHttpMessageConverter);
        msgConverters.add(stringHttpMessageConverter);

        // Prepare acceptable media type
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.ALL);

        //TODO Fix duplicate codeblock
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("mainzellisteApiKey", "X3TVeiOPMjw3ZZrh5Y6ZAm76XhAv2wGg");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, headers);

        //Check if HTTP 409 - Conflict was returned, indicating existing patient
        try{
            //Send patient to Mainzelliste service
            String jsonPatient = restTemplate.exchange(
                    CREATE_PATIENT_ENDPOINT_URL + "?tokenId=" + tokenId + "&mainzellisteApiVersion=" + mainzellisteApiVersion, HttpMethod.POST, httpEntity, String.class
            ).getBody();

            //If no error, continue with creating new patient...

            //Add Patient to MeDIC DB.
            patientRepository.save(patient);

            //Get Generated Pseudonym
            try{
                //Using ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                List<PseudonymResponse> pseudonymResponse = objectMapper.reader()
                        .forType(new TypeReference<List<PseudonymResponse>>() {
                        })
                        .readValue(jsonPatient);

                //TODO: Decide on what value internal id, intid will give.
                String intidCreated = pseudonymResponse.get(0).getIdString();
                String pseudonymCreated = pseudonymResponse.get(1).getIdString();

                //Persist pseudonym in MeDIC DB (Pseudonym table)
                Pseudonym pseudonym = new Pseudonym();
                pseudonym.setPseudonym(pseudonymCreated);
                pseudonym.setCreatedAt(Instant.now().getEpochSecond());

                pseudonymRepository.save(pseudonym);

                return new ResponseEntity<>(messageSource.getMessage("patient.created", null, Locale.ENGLISH), HttpStatus.CREATED);

            }
            //TODO Handle exception instead of thowing stacktrace
            catch (IOException ex){
                ex.printStackTrace();
            }





        }
        catch (HttpClientErrorException | HttpServerErrorException httpError){

            //Returns HTTP 409
            return new ResponseEntity<>(messageSource.getMessage("patient.similarPatient", null, Locale.ENGLISH), HttpStatus.CONFLICT);

        }

        return null;



    }




}
