package demo.io.collecting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @BelongsProject: changgou
 * @BelongsPackage: demo.io.collecting
 * @Author: yang
 * @CreateTime: 2021-05-01 19:28
 * @Description: 下游收集器
 */
public class DownstreamCollectors {
    public static class City{
        private String name;
        private String state;
        private int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }
    }

    public static Stream<City> readCities(String fileName) throws IOException {
       return Files.lines(Paths.get(fileName)).map(l->l.split(","))
       .map(a -> new City(a[0],a[1],Integer.parseInt(a[2])));
    }

    public static void main(String[] args) throws IOException {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(Collectors.groupingBy(Locale::getCountry, Collectors.toSet()));
        System.out.println("countryToLocaleSet:"+countryToLocaleSet);

        locales=Stream.of(Locale.getAvailableLocales());
        Map<String,Long> countryToLocaleCounts =locales.collect(Collectors.groupingBy(Locale::getCountry,Collectors.counting()));
        System.out.println("收集到元素的个数:"+countryToLocaleCounts);

        Stream<City> cities = readCities("D:/log/cities.txt");
        Map<String, Integer> stateToCityPopulation = cities.collect(Collectors.groupingBy(City::getState, Collectors.summingInt(City::getPopulation)));
        System.out.println("接受一个函数作为引元,将该函数应用到下游元素中,并产生它们的和:" + stateToCityPopulation);


    }
}
