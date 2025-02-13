package com.yintp.valid.hibernate;

public class PersonServiceImpl implements PersonService {

    @Override
    public void create(PersonReqDTO person, String channel) {
        System.out.println("crearte person...");
    }

    @Override
    public PersonReqDTO create() {
        PersonReqDTO personReqDTO = new PersonReqDTO();
        personReqDTO.setSex("ssss");
        return personReqDTO;
    }
}
