package ru.internet.sergeevss90.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestBuilder {
    private String name;
    private String job;
}