package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchingTest {

    @Test
    void whenDifferentTypesOfElements() {
        Integer[] array = new Integer[]{9, 8, 7, 6, 5};
        String elementToFind = "Wrong";
        Integer expected = -1;
        assertThat(ParallelIndexSearching.toSearch(array, elementToFind))
                .isEqualTo(expected);
    }

    @Test
    public void whenLinearSearching() {
        int result = ParallelIndexSearching.toSearch(new String[]{"Java", "Spring",
                "Maven", "Hibernate", "Developer", "Job", "Valeri"}, "Valeri");
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void whenRecursiveSearching() {
        int result = ParallelIndexSearching.toSearch(new String[]{"Java", "Spring",
                "Maven", "Hibernate", "Developer", "Job", "Valeri", "Tomcat",
                "PostgresSQL", "Web", "Microservices", "MVC", "Perfect",
                "Middle", "Junior", "Trainee", "Salary"}, "Perfect");
        assertThat(result).isEqualTo(12);
    }

    @Test
    public void whenElementIsNotFound() {
        int result = ParallelIndexSearching.toSearch(new Integer[]{
                7, 6, 5, 4, 3, 2, 1},
                77);
        assertThat(result).isEqualTo(-1);
    }
}