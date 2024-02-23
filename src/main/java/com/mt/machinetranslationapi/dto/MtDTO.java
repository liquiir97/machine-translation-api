package com.mt.machinetranslationapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class MtDTO {
    @JsonProperty(value = "source_language", required = true)
    @NotNull(message = "source_language is required field")
    public String source_language;

    @JsonProperty(value = "target_language", required = true)
    @NotNull(message = "target_language is required field")
    public String target_language;
    @JsonProperty(value = "domain", required = true)
    @NotNull(message = "domain is required field")
    public String domain;
    @JsonProperty(value = "content", required = true)
    @NotNull(message = "content is required field")
    public String content;

    public String getSource_language() {
        return source_language;
    }

    public void setSource_language(String source_language) {
        this.source_language = source_language;
    }

    public String getTarget_language() {
        return target_language;
    }

    public void setTarget_language(String target_language) {
        this.target_language = target_language;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
