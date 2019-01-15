package com.moniaa.blog.builder.v2;

public class Person {
    private final String firstName;
    private final String lastName;

    protected Person(Builder<?, ?> builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static Builder<?, ?> newBuilder() {
        return new BuilderImpl();
    }

    private static final class BuilderImpl extends Builder<Person, BuilderImpl> {
        private BuilderImpl() {
        }

        protected BuilderImpl self() {
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    public abstract static class Builder<T extends Person, B extends Builder<T, B>> {
        private String firstName;
        private String lastName;

        protected Builder() {
        }

        protected abstract B self();

        public abstract T build();

        public B firstName(String firstName) {
            this.firstName = firstName;
            return this.self();
        }

        public B lastName(String lastName) {
            this.lastName = lastName;
            return this.self();
        }
    }
}
