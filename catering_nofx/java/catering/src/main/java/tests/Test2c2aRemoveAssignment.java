package tests;

import catering.businesslogic.*;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import catering.businesslogic.shift.*;
import catering.businesslogic.user.*;
import java.time.*;
import java.util.*;

public class Test2c2aRemoveAssignment {
    public static void main(String[] args) {
        try {
            Random rand = new Random();
            CatERing.getInstance().getUserManager().fakeLogin("Marinella");     // chef
            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(1);
            Service srv = new Service(m);
            srv.setId(rand.nextInt(143) + 43);
            srv.updateChef(CatERing.getInstance().getUserManager().getCurrentUser());
            CatERing.getInstance().getJobManager().createJobsSheet(srv);

            // step 1a
            CatERing.getInstance().getJobManager().modifySheet(srv.getSummary());
            JobsSheet js = CatERing.getInstance().getJobManager().getCurrentSheet();
            // step 2
            CatERing.getInstance().getJobManager().insertJob(Task.loadRecipeById(18)); // "sorbetto limone"
            System.out.println("---------------------");

            // setting job onShift
            AbstractShift shift = new KitchenShift(
                    LocalDateTime.of(2025, 2, 28,1, 0),
                    LocalDateTime.of(2025, 2, 28,2, 0),
                    Period.ofDays(15), null);
            js.getAllJobs().get(2).setOnShift(shift);

            //set cookers Guido and Antonietta available on shift
            ArrayList<AbstractUser> cookers = new ArrayList<>();
            AbstractUser guido = AbstractUser.loadUser("Guido");
            cookers.add(guido);
            AbstractUser antonietta = AbstractUser.loadUser("Antonietta");
            shift.setAvailableWorkers(cookers);

            // step 2c.2
            CatERing.getInstance().getJobManager().setWorker(js.getAllJobs().get(2), guido);   // "Bigné farciti"

            // step 2c.2a
            CatERing.getInstance().getJobManager().removeAssignment(js.getAllJobs().get(2));   // "Bigné farciti"
            System.out.println("TEST removeAssignment (2c.2a): " + js);

            System.out.println("---------------------\nOK.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
