package controllers;
import models.Person;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.ArrayList;
import java.util.List;

public class Accounts extends Controller
{
    public static void signup()
    {
        render("signup.html");
    }

    public static void login()
    {
        render("login.html");
    }

    public static void trainerLogin()
    {
        render("trainerlogin.html");
    }

    public static void register(String name,String gender,String address, String email, String password, float height, float startWeight)
    {
        Person personPart = registerUser(name, email, address, gender, password);//register person then get the person details and assign to memmber
        Logger.info("Registering new user " + email);
        Member member = new Member(personPart.getName(),personPart.getEmail(),personPart.getAddress(),personPart.getGender(),personPart.getPassword(),height, startWeight);//works
        Logger.info("Person get name  " + member);
        member.save();
        redirect("/");
    }

    public static void updateMember(String name, String email, String gender,String password)
    {

        Member member = Accounts.getLoggedInMember();
        Logger.info("Updating user details " + member.getName());
        member.setName(name);
        member.setGender(gender);
        member.setEmail(email);
        member.setPassword(password);
        member.save();

        session.clear();
        redirect("/accountsettings");
    }



      public static void authenticate(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect ("/dashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/login");
        }
    }

    public static void authenticateTrainer(String email, String password)
    {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Trainer trainer = Trainer.findByEmail(email);
        if ((trainer != null) && (trainer.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Trainerid", trainer.id);
            redirect ("/trainerdashboard");
        } else {
            Logger.info("Authentication failed");
            redirect("/trainerlogin");
        }
    }

    public static void logout()
    {
        session.clear();
        redirect ("/");
    }

    public static void settings()
    {
        Member member = Accounts.getLoggedInMember();
        member.setName(member.getName().toUpperCase());
        render("accountsettings.html",member);
    }

    public static Member getLoggedInMember()
    {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }

    public static Trainer getLoggedInTrainer()
    {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }

    private static Person  registerUser(String name,String email,String address,String gender,  String password) {

        Logger.info("Registering new user " + email);
        Person person = new Person(name, email, address, gender, password);//works
        return person;
    }
}