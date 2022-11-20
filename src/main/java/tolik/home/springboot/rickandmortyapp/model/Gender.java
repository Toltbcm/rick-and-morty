package tolik.home.springboot.rickandmortyapp.model;

enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    GENDERLESS("Genderless"),
    UNKNOWN("unknown");

    private String value;

    Gender(String value) {
        this.value = value;
    }
}
