package edu.wpi.always.test;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import edu.wpi.always.Always;
import edu.wpi.always.user.UserModel;
import edu.wpi.always.user.UserUtils;
import edu.wpi.always.user.calendar.Calendar;
import edu.wpi.always.user.calendar.CalendarEntryImpl;
import edu.wpi.always.user.calendar.CalendarEntryTypeManager;
import edu.wpi.always.user.people.PeopleManager;
import edu.wpi.always.user.people.Person;
import edu.wpi.always.user.people.Person.Gender;
import edu.wpi.always.user.people.Person.Relationship;
import edu.wpi.always.user.places.PlaceManager;

public class TestUserGenerator {

   public static void main (String[] args) {
      Always always = Always.make(null, null, null);
      UserModel model = always.getContainer().getComponent(UserModel.class);
      generate(model);
      UserUtils.print(model, System.out);
      model.save();
      System.exit(0);
   }
      
   public static void generate (UserModel userModel) {
      userModel.setUserName("Diane Ferguson");
      PeopleManager peopleMgr = userModel.getPeopleManager();
      PlaceManager placeMgr = userModel.getPlaceManager();
      Calendar calendar = userModel.getCalendar();
      peopleMgr.getUser().setLocation(placeMgr.getPlace("02118"));
      peopleMgr.getUser().setGender(Gender.Female);
      Person daughter = addPerson(peopleMgr, "Ellen Lewis", Relationship.Daughter, null);
      daughter.setPhoneNumber("650-339-0221");
      daughter.setLocation(placeMgr.getPlace("92049"));
      Person daughterHusband = addPerson(peopleMgr, "Mike", null, null);
      daughterHusband.addRelated(daughter, Relationship.Wife);
      Person linda = addPerson(peopleMgr, "Linda", null, Gender.Female);
      linda.addRelated(daughterHusband, Relationship.Father);
      Person ed = addPerson(peopleMgr, "Ed", null, Gender.Male);
      ed.addRelated(daughterHusband, Relationship.Father);
      Person sister = addPerson(peopleMgr, "Linda Jefferson",
            Relationship.Sister, null);
      sister.setLocation(placeMgr.getPlace("38120"));
      sister.setPhoneNumber("615-334-7889");
      Person friend1 = addPerson(peopleMgr, "Harriet Jones",
            Relationship.Friend, Gender.Female);
      friend1.setLocation(placeMgr.getPlace("02118"));
      friend1.setPhoneNumber("617-324-0997");
      Person friend2 = addPerson(peopleMgr, "Marion Smith",
            Relationship.Friend, Gender.Female);
      friend2.setLocation(placeMgr.getPlace("02124"));
      friend2.setPhoneNumber("617-238-3779");
      Person friend3 = addPerson(peopleMgr, "Philip Morley",
            Relationship.Friend, Gender.Male);
      friend3.setLocation(placeMgr.getPlace("33604"));
      friend3.setPhoneNumber("727-671-4536");      
      calendar.create(new CalendarEntryImpl(null,
               CalendarEntryTypeManager.Types.MedicalAppointment,
               null, null,
               new DateTime(2014,1, 1, 10, 0), Hours.hours(1)));
   }
   
   private static Person addPerson (PeopleManager peopleMgr, String name, 
         Relationship relationship, Gender gender) {
      return peopleMgr.addPerson(name, relationship, gender, null, null, null, null, null, null);
   }
}

