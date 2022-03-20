package demo.io.collectin;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.io.collectin
 * @Author: yang
 * @CreateTime: 2021-05-01 18:03
 * @Description: 将流的结果收集到映射表中   整理代码格式  ctrl+alt+l
 */
public class collectingIntoMaps {
    public static class Person {
        private int id;
        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getClass().getName() + "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static Stream<Person> people() {
        return Stream.of(new Person(1001, "齐青"),
                new Person(1002, "韩寒"), new Person(1003, "彤彤"));
    }

    public static void main(String[] args) {
        Map<Integer, String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName:" + idToName);

        Map<Integer, Person> idTOPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson:" + idTOPerson.getClass().getName() + idTOPerson);

        idTOPerson = people().collect(Collectors.toMap(Person::getId, Function.identity(), (existingValue, newValue) -> {
            throw new IllegalStateException();
        }, TreeMap::new));
        System.out.println("idToPersion1:" + idTOPerson.getClass().getName() + idTOPerson);

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(Collectors.toMap(Locale::getLanguage, l -> l.getDisplayLanguage(l), (existingValue, newValue) -> existingValue));
        System.out.println("languageNames: " + languageNames);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryLanguageSets = locales.collect(Collectors.toMap(Locale::getDisplayCountry,
                l -> Collections.singleton(l.getDisplayLanguage()), (a, b) -> {//union of a and b
                    Set<String> union = new HashSet<>(a);
                    union.addAll(b);
                    return union;
                }));
        System.out.println("countryLanguageSets: " + countryLanguageSets);

        Stream<Locale> locales1 = Stream.of(Locale.getAvailableLocales());
        Map<String, List<Locale>> countryToLocales = locales1.collect(
                Collectors.groupingBy(Locale::getCountry));
        System.out.println("通过国家来群组Locale" + countryToLocales);

        List<Locale> swissLocales = countryToLocales.get("CN");
        System.out.println("给定国家代码对应的所有地点" + swissLocales);

    }
}
