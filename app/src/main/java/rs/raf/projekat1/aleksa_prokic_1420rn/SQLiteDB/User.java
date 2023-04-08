package rs.raf.projekat1.aleksa_prokic_1420rn.SQLiteDB;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class User {

    private long id;
    private String email;
    private String username;
    private String password;

    public User(String email, String username, String password, long id) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return username + " " + password + " " + email + "\n";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null) return  false;

        return ((User)obj).getUsername().equals(this.username) &&
                ((User)obj).getPassword().equals(this.password) &&
                ((User)obj).getEmail().equals(this.email);
    }
}
