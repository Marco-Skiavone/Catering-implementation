package tests;

import catering.businesslogic.*;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import java.util.*;

public class Test1aModifySheet {
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
            CatERing.getInstance().getJobManager().insertJob(Task.loadRecipeById(9));   // "croissant"
            System.out.println("TEST InsertJob (2): " + js);
            System.out.println("---------------------\n");

            // step 3
            CatERing.getInstance().getJobManager().sortJobsSheet(Comparator.comparing(j -> j.getTask().getId()));
            System.out.println("TEST sortJobsSheet: " + js);
            System.out.println("---------------------\nOK.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
