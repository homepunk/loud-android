package homepunk.github.com.domain.model.discogs;

public class DiscogsArtist {
    private String anv;
    private int id;
    private String join;
    private String name;
    private String resourceUrl;
    private String role;
    private String tracks;

    public DiscogsArtist(String anv, int id, String join, String name, String resourceUrl, String role, String tracks) {
        this.anv = anv;
        this.id = id;
        this.join = join;
        this.name = name;
        this.resourceUrl = resourceUrl;
        this.role = role;
        this.tracks = tracks;
    }

    public String getAnv() {
        return anv;
    }

    public void setAnv(String anv) {
        this.anv = anv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }
}
