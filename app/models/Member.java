package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Person {


    private float height;
    private float startWeight;
    private double bmi;
    private String bmiCategory;


    private static String ideal;
    private static boolean isIdealBodyWeight;
    private static float maleBaseWeight = 50f;
    private static float femaleBaseWeight = 45.5f;
    private static float additionalWeight = 2.3f;
    private static float inchesOver;
    private static float weightAllowed;
    private static int numOfAssessments = 0;


    @OneToMany(cascade = CascadeType.ALL)
    public List<Assessment> assessments = new ArrayList<>();

    public Member(String name, String email, String address, String gender, String password, float height, float startWeight) {
        super(name, email, address, gender, password);
        this.height = height;
        this.startWeight = startWeight;

    }

    public static Member findByEmail(String email) {
        return find("email", email).first();
    }

    public boolean checkPassword(String password) {
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

    public List<Assessment> getAssessments() {
        return assessments;
    }


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        this.startWeight = startWeight;
    }


    public static String getIdeal() {
        return ideal;
    }

    public static void setIdeal(String ideal) {
        Member.ideal = ideal;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getBmiCategory() {
        return bmiCategory;
    }

    public void setBmiCategory(String bmiCategory) {
        this.bmiCategory = bmiCategory;
    }

    public int getNumOfAssessments() {
        List<Assessment> numOfAssessments = getAssessments();
        return numOfAssessments.size();

    }
}