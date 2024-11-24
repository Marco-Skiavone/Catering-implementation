package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.persistence.*;
import java.util.*;

public class Test1JobCreated {
    public static void main(String[] args) {
        try {
            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(2);
            System.out.println(m);
            Service s = new Service(m);
            System.out.println(s);
            JobsSheet js = new JobsSheet(s);

            Random rd = new Random();
            for(MenuItem mi : m.getAllMenuItems())
                js.addJob(mi.getItemRecipe());

            for (Job j : js.getAllJobs()) {
                j.setEta(120); // min
                j.setPortions(50);
                j.setDone(rd.nextBoolean());
            }

            System.out.println(js);
            System.out.println("\n\n------------------------------\n\n");

            new JobPersistence().updateJobsSheetCreated(s, js);

            System.out.println("\nTEST DEFINE SECTION");
        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
