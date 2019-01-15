package com.moniaa.blog.builder.v1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuilderTest {
    private static final String FIRST_NAME  = "Lin";
    private static final String LAST_NAME = "Carey";
    private static final String EMPLOYER  = "moniaa.com";
    private static final String TITLE = "Engineer";

    @Test
    public void testNewPerson() {
        Person person = Person.newBuilder()
                .firstName("Lin")
                .lastName("Carey")
                .build();

        assertEquals("FirstName", person.getFirstName(), FIRST_NAME);
        assertEquals("LastName", person.getLastName(), LAST_NAME);
    }

    @Test
    public void testNewEmployee() {
        Employee employee = Employee.newBuilder()
                .firstName("Lin")
                .lastName("Carey")
                .employer("Moniaa")
                .title("Engineer")
                .build();

        assertEquals("FirstName", employee.getFirstName(), FIRST_NAME);
        assertEquals("LastName", employee.getLastName(), LAST_NAME);
        assertEquals("Employer", employee.getEmployer(), EMPLOYER);
        assertEquals("Title", employee.getTitle(), TITLE);

    }
}
