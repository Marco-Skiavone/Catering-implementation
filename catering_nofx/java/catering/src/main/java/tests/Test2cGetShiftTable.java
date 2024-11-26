package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.*;
import catering.businesslogic.event.*;
import catering.businesslogic.menu.*;

import java.util.*;

public class Test2cGetShiftTable {
    public static void main(String[] args) {
        try {
            Random rand  = new Random();
            CatERing.getInstance().getUserManager().fakeLogin("Marinella");     // chef
            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(3);
            Service srv = new Service(m);
            srv.setId(rand.nextInt(100) + 1);
            srv.updateChef(CatERing.getInstance().getUserManager().getCurrentUser());

            // step 1
            CatERing.getInstance().getJobManager().createJobsSheet(srv);
            System.out.println("---------------------\n");

            // step 2c
            //System.out.println(CatERing.getInstance().getJobManager().getShiftTable());
        } catch (UseCaseLogicException e) {
            System.err.println("Logic error in use case: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
