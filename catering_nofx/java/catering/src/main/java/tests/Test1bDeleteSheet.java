package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.*;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import java.time.*;

public class Test1bDeleteSheet {
    public static void main(String[] args) {
        try {
            CatERing.getInstance().getUserManager().fakeLogin("Marinella");     // chef
            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(1);
            Service srv = new Service(m);
            srv.setId(42);
            srv.updateChef(CatERing.getInstance().getUserManager().getCurrentUser());
            srv.setStartTime(LocalDateTime.of(2024, 5,4, 11, 0));
            srv.setEndTime(LocalDateTime.of(2024, 5,4, 14, 30));

            // (creation made only to proceed with testing)
            CatERing.getInstance().getJobManager().createJobsSheet(srv);
            System.out.println("TEST Service and JobsSheet creation: " +
                    CatERing.getInstance().getJobManager().getCurrentSheet());
            System.out.println("---------------------\n");

            // it sleeps for 8 secs to let you see the data in the db
            System.out.println("Delete in 8 seconds.");
            for (int i = 7; i >= 0; i--) {
                System.out.println("Remaining: " + i);
                Thread.sleep(1000);
            }

            // 1B step - deletion
            CatERing.getInstance().getJobManager().deleteSheet(srv);
            JobsSheet js = CatERing.getInstance().getJobManager().getCurrentSheet();

            if (js == null)
                System.out.println("TEST OK.");
            else
                throw new RuntimeException("TEST FAILED: currentSheet is not null yet.");
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
