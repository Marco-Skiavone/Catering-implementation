package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.*;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import java.util.*;

public class Test2bRemoveJob {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Marinella");     // chef
            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(1);
            Service srv = new Service(m);
            srv.setId(42);
            srv.updateChef(CatERing.getInstance().getUserManager().getCurrentUser());

            // step 1
            CatERing.getInstance().getJobManager().createJobsSheet(srv);
            JobsSheet js =  CatERing.getInstance().getJobManager().getCurrentSheet();
            System.out.println("---------------------\n");

            ArrayList<Job> jobs = js.getAllJobs();
            Job elem = jobs.get(2);
            System.out.println("Removing: " + elem + " from\n" + js);
            // step 2b
            CatERing.getInstance().getJobManager().removeJob(elem);
            System.out.println("JobsSheet after: " + js);
            if (js.getAllJobs().contains(elem))
                throw new RuntimeException("TEST FAILED: " + elem + " still in JobsSheet.");
            else
                System.out.println("---------------------\nTEST OK.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
