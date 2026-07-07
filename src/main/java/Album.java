import java.io.Serializable;
import java.util.List;

public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    private String albumName;
    private List<String> photoNames;

    public Album(String albumName, List<String> photoNames) {
        this.albumName = albumName;
        this.photoNames = photoNames;
    }

    @Override
    public String toString() {
        return "Album: " + albumName + ", photos: " + photoNames;
    }
}
