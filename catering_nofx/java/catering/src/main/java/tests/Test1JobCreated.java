package tests;

import catering.businesslogic.*;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import java.util.*;

public class Test1JobCreated {
    public static void main(String[] args) {
        try {
            Random rd = new Random();
            System.out.println("TEST Fake login");
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");     // chef
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            System.out.println("---------------------\n");

            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(1);
            Service srv = new Service(m);
            srv.setId(rd.nextInt(100) + 1);
            srv.updateChef(CatERing.getInstance().getUserManager().getCurrentUser());

            // step 1
            CatERing.getInstance().getJobManager().createJobsSheet(srv);
            JobsSheet js =  CatERing.getInstance().getJobManager().getCurrentSheet();

            System.out.println("TEST Service: " + js);
            System.out.println("---------------------\n");

            // step 2
            CatERing.getInstance().getJobManager().insertJob(Task.loadRecipeById(10)); // pane al cioccolato
            System.out.println("TEST InsertJob: " + js);
            System.out.println("---------------------\n");

            // step 2 (2)
            CatERing.getInstance().getJobManager().insertJob(Task.loadRecipeById(20));
            System.out.println("TEST InsertJob (2): " + js);
            System.out.println("---------------------\n");

            // ArrayList<Job> jobs = CatERing.getInstance().getJobManager().getCurrentSheet().getAllJobs();
            // CatERing.getInstance().getJobManager().modifyJob(jobs.get(3), 15, 20);
            // CatERing.getInstance().getJobManager().modifyJob(jobs.get(2), 18, 25);

            // step 3
            CatERing.getInstance().getJobManager().sortJobsSheet(Comparator.comparing(j -> j.getTask().getId()));
            System.out.println("TEST sortJobsSheet: " + js);
            System.out.println("---------------------\nOK.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }
}
