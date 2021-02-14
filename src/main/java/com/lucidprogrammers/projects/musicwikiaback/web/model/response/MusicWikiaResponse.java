package com.lucidprogrammers.projects.musicwikiaback.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MusicWikiaResponse<T> {

    private String message;

    private String type;

    private Integer code;

    private T t;

}
