package upenn.edu.cis350.anon;

public class Genre {
    String genreId;
    String name;

    public Genre(String genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public String getGenreId() { return genreId; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
