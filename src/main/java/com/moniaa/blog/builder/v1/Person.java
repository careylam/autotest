package com.moniaa.blog.builder.v1;

public class Person {
    private final String firstName;
    private final String lastName;

    protected Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    protected static class Builder {
        private String firstName;
        private String lastName;

        public Person build() {
            return new Person(this);
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }
}
