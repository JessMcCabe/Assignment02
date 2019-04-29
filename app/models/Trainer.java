package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trainer extends Person  {



    @OneToMany(cascade = CascadeType.ALL)
    public List<Member> members = new ArrayList<>();

    public Trainer(String name, String email, String address, String gender, String password)
    {
        super( name , email, address, gender,password);
    }

    public static Trainer findByEmail(String email)
    {
        return find("email", email).first();
    }



    public boolean checkPassword(String password)
    {
        return super.getPassword().equals(password);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }



    public String getGender() {
        return super.getGender();
    }

    public void setGender(String gender) {
        super.setGender(gender);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }
}