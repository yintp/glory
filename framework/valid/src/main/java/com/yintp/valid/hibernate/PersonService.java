package com.yintp.valid.hibernate;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PersonService {

    void create(@NotNull @Valid PersonReqDTO person, @NotBlank String channel);

    @NotNull @Valid PersonReqDTO create();
}
