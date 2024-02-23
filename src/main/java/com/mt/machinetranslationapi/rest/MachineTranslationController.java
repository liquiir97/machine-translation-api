package com.mt.machinetranslationapi.rest;

import com.mt.machinetranslationapi.dto.MtDTO;
import com.mt.machinetranslationapi.error.CustomBadRequestException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class MachineTranslationController {


    private final Logger log = LoggerFactory.getLogger(MachineTranslationController.class);

    List<String> listLanguages = new ArrayList<>();
    List<String> listDomains = new ArrayList<>();
    public MachineTranslationController()
    {
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale locale : availableLocales) {
            if(!locale.getLanguage().isEmpty() && !locale.getCountry().isEmpty())
            {
                if(!listLanguages.contains(locale.getLanguage()+"-"+locale.getCountry()))
                {
                    listLanguages.add(locale.getLanguage()+"-"+locale.getCountry());
                }
            }
            if(!locale.getLanguage().isEmpty() && locale.getCountry().isEmpty())
            {
                if(!listLanguages.contains(locale.getLanguage()))
                {
                    listLanguages.add(locale.getLanguage());
                }
            }
        }

        listDomains.add("academic");
        listDomains.add("business");
        listDomains.add("general");
        listDomains.add("casual");
        listDomains.add("creative");
    }

    @GetMapping("/languages")
    public ResponseEntity<List<String>> getSupportedLanguages() throws Exception
    {
        log.info("getSupportedLanguages");
        return ResponseEntity.ok(listLanguages);
    }

    @GetMapping("/domains")
    public ResponseEntity<List<String>> getDomains()
    {
        log.info("getDomains");
        return ResponseEntity.ok(listDomains);
    }

    @PostMapping(value = "/translate", consumes="application/json")
    public ResponseEntity<Map<String,String>> contentForTranslate(@RequestBody @Valid MtDTO mtDTO ) throws Exception
    {
        log.info("contentForTranslate");
        Map<String,String> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        response.put("code", String.valueOf(httpStatus.value()));
        response.put("status", httpStatus.name());
        String message = "Translating text....";
        response.put("response", message);


        Boolean languageSorceCheck = listLanguages.contains(mtDTO.getSource_language());
        if(!languageSorceCheck)
        {
            log.error("source_language is not supported!");
            throw new CustomBadRequestException("source_language is not supported!");
        }

        Boolean languageTargetCheck = listLanguages.contains(mtDTO.getTarget_language());
        if(!languageTargetCheck)
        {
            log.error("target_language is not supported!");
            throw new CustomBadRequestException("target_language is not supported!");
        }

        Boolean domainCheck = listDomains.contains(mtDTO.getDomain());
        if(!domainCheck)
        {
            log.error("Domain is not supported!");
            throw new CustomBadRequestException("domain is not supported!");
        }

        return ResponseEntity.status(httpStatus).body(response);

    }

    @PostMapping(value = "/validated-translate", consumes="application/json")
    public ResponseEntity<Map<String, String>> validateTranslation(@RequestBody @Valid MtDTO mtDTO) throws Exception
    {

        log.info("validateTranslation");
        Map<String, String> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        response.put("code", String.valueOf(httpStatus.value()));
        response.put("title", httpStatus.name());

        String message = "Validating translation....";
        response.put("message", message);

        Boolean languageSorceCheck = listLanguages.contains(mtDTO.getSource_language());
        if(!languageSorceCheck)
        {
            log.error("source_language is not supported!");
            throw new CustomBadRequestException("source_language is not supported!");
        }

        Boolean languageTargetCheck = listLanguages.contains(mtDTO.getTarget_language());
        if(!languageTargetCheck)
        {
            log.error("target_language is not supported!");
            throw new CustomBadRequestException("target_language is not supported!");
        }

        Boolean domainCheck = listDomains.contains(mtDTO.getDomain());
        if(!domainCheck)
        {
            log.error("Domain is not supported!");
            throw new CustomBadRequestException("Domain is not supported!");
        }

        List<String> countWords = List.of(mtDTO.getContent().split(" "));
        if(countWords.size() > 30)
        {
            log.error("content has more than 30 words!");
            throw new CustomBadRequestException("content has more than 30 words!");
        }

        return ResponseEntity.status(httpStatus).body(response);
    }
}
