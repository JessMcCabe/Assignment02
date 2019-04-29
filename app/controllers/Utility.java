
package controllers;

import models.Assessment;
import models.Member;
import models.Trainer;

import java.util.List;
import java.util.Date;

public class Utility{

private static float maleBaseWeight = 50f;
private static float femaleBaseWeight = 45.5f;
private static float additionalWeight = 2.3f;
private static double inchesOver;
private static float weightAllowed;


    public static boolean isIdealBodyWeight(Member member, Assessment assessment){
        boolean ideal = false;
        if(member.getGender().toUpperCase().equals("MALE")){//if the member is male
            if(member.getHeight()<=5){

                if(assessment.getWeight() <= 50){
                    ideal = true;
                }
            }
            else if (member.getHeight() >5){

                inchesOver = member.getHeight() - 5;
                weightAllowed = (maleBaseWeight + ((float)inchesOver*additionalWeight));
                if(assessment.getWeight() <= weightAllowed){

                    ideal = true;
                }
            }




        }
        else     if(member.getGender().toUpperCase().equals("FEMALE")){//if the member is female
            if(member.getHeight()<=5){

                if(assessment.getWeight() <= 45.5){
                    ideal = true;
                }
            }
            else if (member.getHeight() >5){

                inchesOver = member.getHeight() - 5;
                weightAllowed = (femaleBaseWeight + ((float)inchesOver*additionalWeight));
                if(assessment.getWeight() <= weightAllowed){

                    ideal = true;
                }
            }




        }
        return ideal;// depending on ideal or not
    }

    public static float determineBMI(float height,float weight){
        //weight in kg over height (in cm squared)
        float heightM = height / 3.28f; //this is the height in meters
        return weight/(heightM*heightM);

    }

     /**BMI less than 16 (exclusive) is "SEVERELY UNDERWEIGHT"
        BMI between 16 (inclusive) and 18.5 (exclusive) is "UNDERWEIGHT"
        BMI between 18.5 (inclusive) and 25(exclusive) is "NORMAL"
        BMI between 25 (inclusive) and 30 (exclusive) is "OVERWEIGHT"
        BMI between 30 (inclusive) and 35 (exclusive) is "MODERATELY OBESE"
        BMI greater than 35 (inclusive) and is "SEVERELY OBESE"*/

     public static String determineBMICategory(float bmiValue){
        String category = "";


       if (bmiValue <16){
            category = "SEVERELY UNDERWEIGHT";
        }
       else  if (bmiValue >=16 & bmiValue <18.5 ){
             category = "UNDERWEIGHT";
       }
       else  if (bmiValue >=18.5 & bmiValue <25 ){
             category = "NORMAL";
         }
       else  if (bmiValue >=25 & bmiValue <30 ){
             category = "OVERWEIGHT";
         }
       else  if (bmiValue >=30 & bmiValue <35 ){
             category = "MODERATELY OBESE";
         }
       else  if (bmiValue >=35 ){
             category = "SEVERELY OBESE";
         }

       return category;



    }
}