package entity;

public abstract class User {
    private static int idCounter = 1;
    protected String id;
    protected String name;
    protected String email;
    protected String password;

    public User(String name, String email, String password) {
        this.id = String.valueOf(idCounter++);  // auto-generate ID
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
