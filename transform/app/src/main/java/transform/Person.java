package transform;

public class Person {
    public final String cpf;
    public final String name;
    public final int age;

    public Person(String cpf, String name, int age) {
        this.cpf = cpf;
        this.name = name;
        this.age = age;
    }

    public static class Builder {
        private String cpf;
        private String name;
        private int age;

        public Builder withCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Person build() {
            return new Person(cpf, name, age);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (age != other.age)
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + " (" + cpf + "), " + age + " years old";
    }
}
