package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.jobs.JobsSheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.Task;
import catering.businesslogic.shift.AbstractShift;
import catering.businesslogic.shift.KitchenShift;
import catering.businesslogic.user.AbstractUser;
import catering.businesslogic.user.Staff;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Test2c2SetWorker {
    public static void main(String[] args) {
        try {
            Random rand = new Random();
            CatERing.getInstance().getUserManager().fakeLogin("Marinella");     // chef
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("---------------------\n");
            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(1);
            Service srv = new Service(m);
            srv.setId(rand.nextInt(143) + 43);
            srv.updateChef(CatERing.getInstance().getUserManager().getCurrentUser());
            // creation made only to proceed with testing
            CatERing.getInstance().getJobManager().createJobsSheet(srv);

            // step 1a
            CatERing.getInstance().getJobManager().modifySheet(srv.getSummary());
            // previous line makes the sheet modifiable
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
            ArrayList<AbstractUser> cookers = new ArrayList<AbstractUser>();
            AbstractUser guido = AbstractUser.loadUser("Guido");
            cookers.add(guido);
            AbstractUser antonietta = AbstractUser.loadUser("Antonietta");
            shift.setAvailableWorkers(cookers);

            // step 2c.2
            CatERing.getInstance().getJobManager().setWorker(js.getAllJobs().get(2), guido);   // "croissant"
            System.out.println("TEST setWorker (2c.2): " + js);

            // step 2c.2
            System.out.println("TEST setWorker err (2c.2): " + js);
            CatERing.getInstance().getJobManager().setWorker(js.getAllJobs().get(2), antonietta);   // "croissant"

            System.out.println("---------------------\nOK.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
