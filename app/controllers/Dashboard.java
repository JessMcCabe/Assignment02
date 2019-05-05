package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Date;
//import java.util.Collections;

public class Dashboard extends Controller
{

private  static  Date date = new Date();
private static boolean isIdealBodyWeight;
private static String ideal;

  public static void addAssessment(float weight, double chest,double thigh,double upperArm,double waist,double hips,String comments)
  {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight,chest,thigh,upperArm,waist,hips,comments,date);
    member.assessments.add(assessment);
    member.setBmiCategory(Utility.determineBMICategory(Utility.determineBMI(member.getHeight(),assessment.getWeight())));
    member.setBmi(Utility.determineBMI(member.getHeight(),assessment.getWeight()));

    List<Assessment> assessments = member.assessments;
    assessment.setTrend(Utility.determineTrend(assessment.getTotalMeasure(),assessments.get(assessments.size()-2).getTotalMeasure()));
    member.save();
    Logger.info("Adding Assessment: Weight: " + weight + " Chest: "+ chest + " Thigh: " + thigh + " UpperArm: " + upperArm + " Waist: " + waist + " Hips: " + hips + " On: " + date);
    redirect("/dashboard");
  }

  public static void deleteAssessment(Long assessmentid)
  {

    Member member = Accounts.getLoggedInMember();
    Assessment assessment = Assessment.findById(assessmentid);
    member.assessments.remove(assessment);
    member.save();
    assessment.delete();
    Logger.info("Deleting " + assessmentid);
    redirect("/dashboard");
  }

  public static void indexTrainer()
  {
    Logger.info("Rendering Trainer Dashboard");
    Trainer trainer = Accounts.getLoggedInTrainer();
    trainer.setName (trainer.getName().toUpperCase());
    //add all members to the trainer

      List<Member> members = members.findAll();
      //Assessment assessments = members.assessment;

    render("trainerdashboard.html", trainer, members);
  }

  public static void index()
  {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    member.setName( member.getName().toUpperCase());
      List<Assessment> assessments = member.assessments;
      member.setBmiCategory(Utility.determineBMICategory(Utility.determineBMI(member.getHeight(), assessments.get(assessments.size() - 1).getWeight())));
      member.setBmi(Utility.determineBMI(member.getHeight(), assessments.get(assessments.size() - 1).getWeight()));
      isIdealBodyWeight = Utility.isIdealBodyWeight(member, assessments.get(assessments.size() - 1));
      if (isIdealBodyWeight) {
        member.setIdeal("green");
      } else {
        member.setIdeal("red");
      }

    render("dashboard.html", member, assessments);
  }

  public static void deleteMember(Long memberid)
  {

    Trainer trainer = Accounts.getLoggedInTrainer();
    Member member = Member.findById(memberid);
    trainer.members.remove(member);
    trainer.save();
    member.delete();
    Logger.info("Deleting " + memberid);
    redirect("/trainerdashboard");
  }

  public static void listMemberAssessments(Long membersid)
  {

    Trainer trainer = Accounts.getLoggedInTrainer();
    Member member = Member.findById(membersid);
    member.setName( member.getName().toUpperCase());
    List<Assessment> assessments = member.assessments;
    //Collections.sort(assessments);
    try {
      member.setBmiCategory(Utility.determineBMICategory(Utility.determineBMI(member.getHeight(), assessments.get(assessments.size() - 1).getWeight())));
      member.setBmi(Utility.determineBMI(member.getHeight(), assessments.get(assessments.size() - 1).getWeight()));
      isIdealBodyWeight = Utility.isIdealBodyWeight(member, assessments.get(assessments.size() - 1)); //run this against the most recent assessment available
      if (isIdealBodyWeight) {
        member.setIdeal("green");
      } else {
        member.setIdeal("red");
      }
    }catch (Exception e){
      Logger.info("No assessments to show for member" + member.getName());
      render("trainerviewofassessments.html", member,assessments);
    }
    render("trainerviewofassessments.html", member,assessments);

  }

  public static void addAssessmentComment(Long assessmentid,String Comments)
  {
    Trainer trainer = Accounts.getLoggedInTrainer();
    //Member member = Member.findById(membersid);
    Assessment assessment = Assessment.findById(assessmentid);
    //List<Assessment> assessments = member.assessments;
    assessment.setComments(Comments);
    //member.save();
    assessment.save();
    Logger.info("Adding Assessment Comment " + Comments);

    redirect("/trainerdashboard");
  }

  public static void trainerHome()
  {

    redirect("/trainerdashboard");
  }
}
