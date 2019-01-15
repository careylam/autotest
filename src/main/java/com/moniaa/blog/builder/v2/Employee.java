package com.moniaa.blog.builder.v2;

public class Employee extends Person {
    private final String employer;
    private final String title;

    protected Employee(final Builder<?, ?> builder) {
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

    public static Builder<?, ?> newBuilder() {
        return new BuilderImpl();
    }

    private static final class BuilderImpl extends Builder<Employee, BuilderImpl> {
        private BuilderImpl() {
        }

        protected BuilderImpl self() {
            return this;
        }

        public Employee build() {
            return new Employee(this);
        }
    }

    public abstract static class Builder<T extends Employee, B extends Builder<T, B>> extends Person.Builder<T, B> {
        private String employer;
        private String title;

        public Builder() {
        }

        protected abstract B self();

        public abstract T build();

        public B employer(final String employer) {
            this.employer = employer;
            return this.self();
        }

        public B title(final String title) {
            this.title = title;
            return this.self();
        }

    }
}
