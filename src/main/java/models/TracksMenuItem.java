package models;

public class TracksMenuItem implements MenuItem{
//    TOWARDS_WHITE_NIGHTS,
//    NACH_OKTOBERFEST,
//    ROUTE_SAINT_TROPEZ,
//    FOGGY_WEATHER,
//    ORIENTAL_ADVENTURE,
//    UKRAINE_LONG_AND_WIDE,
//    ARRIVING_ITALY,
//    EASTERN_COURSE;
    private String name;

    public TracksMenuItem(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return this.name;
    }
}

//public enum TracksMenuItem {
//    "Ku białym nocom",
//    "Na Oktoberfest",
//    "Kierunek Saint-Tropez",
//    "Szklana pogoda",
//    "Zew Orientu",
//    "Na stepowej Ukrainie",
//    "U bram Italii",
//    "Kurs na Wschód"
//}
