package com.teste.contactsapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContactsApiApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ContactsApiApplication.main(null));
    }

}
