package com.lucidprogrammers.projects.musicwikiaback.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * HTTP base response data model
 * for all API responses
 *
 * @author Benjamin Gil FLores
 * @since 1.0.0
 *
 * @param <T> response HTTP data model
 * @see ResponseModel
 */
@Getter
@Setter
@AllArgsConstructor
public class MusicWikiaResponse<T extends ResponseModel> {

    private String message;

    private String type;

    private Integer code;

    private T response;

}
