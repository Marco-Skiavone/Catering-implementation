package tests;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import catering.persistence.*;
import java.util.*;

public class Test1JobCreated {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();
            System.out.println("\n\n-----------------------------\n\n");

            Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(0);
            System.out.println(m);

            Service s = new Service(m);
            System.out.println(s);

            JobsSheet js = new JobsSheet(s);
            System.out.println(js);

            ArrayList<Task> t;

            m.getAllMenuItems().get(0).getItemRecipe();

            for(MenuItem mi : m.getAllMenuItems()){
                js.addJob(mi.getItemRecipe());
            }

            System.out.println("\n\n------------------------------\n\n");

            new JobPersistence().updateJobsSheetCreated(s, js);

            System.out.println("\nTEST DEFINE SECTION");
        } catch (UseCaseLogicException e) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
