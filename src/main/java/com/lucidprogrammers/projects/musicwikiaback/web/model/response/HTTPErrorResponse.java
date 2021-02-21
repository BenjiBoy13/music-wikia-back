package com.lucidprogrammers.projects.musicwikiaback.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * HTTP response that holds an error information
 *
 * @author Benjamin Gil Flores
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
public class HTTPErrorResponse implements ResponseModel {

    private String message;

}
