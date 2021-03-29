package countries;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.*;
import java.util.function.*;

import java.time.ZoneId;

public class Homework2 {

    static int charCount(String s, char c) { int count=0; for (int i=0; i<s.length(); i++) {if (s.charAt(i)=='c') count++;} return count;}

    static int vowelCount(String s) {int count=0; for (int i=0; i<s.length(); i++) {if (s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u') count++;} return count; }

    private List<Country> countries;

    public Homework2() {
        countries = new CountryRepository().getAll();
    }

    /**
     * Returns the longest country name translation.
     */
    public Optional<String> streamPipeline1() {
        return countries.stream().flatMap(country -> country.getTranslations().values().stream()).max(Comparator.comparing(String::length));
    }

    /**
     * Returns the longest Italian (i.e., {@code "it"}) country name translation.
     */
    public Optional<String> streamPipeline2() {
        return countries.stream().filter(country -> country.getTranslations().containsKey("it")).map(country -> country.getTranslations().get("it")).max(Comparator.comparing(String::length));
    }

    /**
     * Prints the longest country name translation together with its language code in the form language=translation.
     */
    public void streamPipeline3() {
        countries.stream().flatMap(country -> country.getTranslations().entrySet().stream()).max(Comparator.comparing(translation -> translation.getValue().length())).get();
    }

    /**
     * Prints single word country names (i.e., country names that do not contain any space characters).
     */
    public void streamPipeline4() {
        countries.stream().filter(country -> country.getName().contains(" ") == false).map(Country::getName).forEach(System.out::println);
    }

    /**
     * Returns the country name with the most number of words.
     */
    public Optional<String> streamPipeline5() {
        return countries.stream().max(Comparator.comparing(country -> country.getName().split(" ", 0).length)).map(Country::getName);
    }

    /**
     * Returns whether there exists at least one capital that is a palindrome.
     */
    public boolean streamPipeline6() {
        return countries.stream().anyMatch(country -> new StringBuilder(country.getCapital()).equals(new StringBuilder(country.getCapital()).reverse()));
    }

    /**
     * Returns the country name with the most number of {@code 'e'} characters ignoring case.
     */
    public Optional<String> streamPipeline7() {
        return countries.stream().map(Country::getName).max(Comparator.comparing(country -> charCount(country.toLowerCase(), 'e')));
    }

    /**
     *  Returns the capital with the most number of English vowels (i.e., {@code 'a'}, {@code 'e'}, {@code 'i'}, {@code 'o'}, {@code 'u'}).
     */
    public Optional<String> streamPipeline8() {
        return countries.stream().map(Country::getCapital).max(Comparator.comparing(country -> vowelCount(country.toLowerCase())));
    }

    /**
     * Returns a map that contains for each character the number of occurrences in country names ignoring case.
     */
    public Map<Character, Long> streamPipeline9() {
        return countries.stream().flatMap(country -> country.getName().toLowerCase().chars().mapToObj(value -> (char) value)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns a map that contains the number of countries for each possible timezone.
     */
    public Map<ZoneId, Long> streamPipeline10() {
        return countries.stream().flatMap(country -> country.getTimezones().stream()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * Returns the number of country names by region that starts with their two-letter country code ignoring case.
     */
    public Map<Region, Long> streamPipeline11() {
        return countries.stream().filter(country -> country.getName().toLowerCase().substring(0, 2).equals(country.getCode().toLowerCase())).collect(Collectors.groupingBy(Country::getRegion, Collectors.counting()));
    }

    /**
     * Returns a map that contains the number of countries whose population is greater or equal than the population average versus the the number of number of countries with population below the average.
     */
    public Map<Boolean, Long> streamPipeline12() {
        return countries.stream().collect(Collectors.partitioningBy(country -> country.getPopulation() >= countries.stream().mapToLong(Country::getPopulation).average().getAsDouble(), Collectors.counting()));
    }

    /**
     * Returns a map that contains for each country code the name of the corresponding country in Portuguese ({@code "pt"}).
     */
    public Map<String, String> streamPipeline13() {
        return countries.stream().collect(Collectors.toMap(Country::getCode, country -> country.getTranslations().get("pt")));
    }

    /**
     * Returns the list of capitals by region whose name is the same is the same as the name of their country.
     */
    public Map<Region, List<String>> streamPipeline14() {
        return countries.stream().filter(country -> country.getName().equals(country.getCapital())).collect(Collectors.groupingBy(Country::getRegion, Collectors.mapping(Country::getCapital, Collectors.toList())));
    }

    /**
     *  Returns a map of country name-population density pairs.
     */
    public Map<String, Double> streamPipeline15() {
        return countries.stream().collect(Collectors.toMap(Country::getName, country -> country.getArea() != null ? country.getPopulation()/country.getArea().doubleValue():Double.NaN));
    }

}
