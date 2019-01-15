package com.moniaa.blog.builder.v1;


public class Employee extends Person {
    private final String employer;
    private final String title;

    protected Employee(final Builder builder) {
        super(builder);
        this.employer = builder.employer;
        this.title = builder.title;
    }

    public String getEmployer() {
        return employer;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", employer='" + employer + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends Person.Builder {
        private String employer;
        private String title;

        public Builder employer(final String employer) {
            this.employer = employer;
            return this;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        @Override
        public Builder firstName(String firstName) {
            super.firstName(firstName);
            return this;
        }

        @Override
        public Builder lastName(String lastName) {
            super.lastName(lastName);
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }

    }
}
