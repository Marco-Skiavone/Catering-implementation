package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.Service;
import catering.businesslogic.jobs.JobsSheet;
import catering.businesslogic.menu.Menu;
import catering.businesslogic.recipe.Task;

import java.util.Comparator;
import java.util.Random;

public class Test2aModifyJob {
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

            // step 2 (2)
            CatERing.getInstance().getJobManager().modifyJob(js.getAllJobs().get(2), 15, 20, true);   // "croissant"
            System.out.println("TEST modifyJob (2a): " + js);
            System.out.println("---------------------\n");

            System.out.println("---------------------\nOK.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
