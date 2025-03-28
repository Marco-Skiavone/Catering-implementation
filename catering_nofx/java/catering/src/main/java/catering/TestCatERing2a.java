package catering;
import catering.businesslogic.CatERing;
import catering.businesslogic.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import java.util.*;

public class TestCatERing2a {
    public static void main(String[] args) {
        try {
            /* System.out.println("TEST DATABASE CONNECTION");
            PersistenceManager.testSQLConnection();*/
            CatERing.getInstance().getUserManager().fakeLogin("Lidia");
            System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());
            Menu m = CatERing.getInstance().getMenuManager().createMenu("Menu Pinco Pallino");
            Section antipasti = CatERing.getInstance().getMenuManager().defineSection("Antipasti");
            Section primi = CatERing.getInstance().getMenuManager().defineSection("Primi");
            Section secondi = CatERing.getInstance().getMenuManager().defineSection("Secondi");

            ArrayList<Task> recipes = CatERing.getInstance().getRecipeManager().getRecipes();
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(0), antipasti);
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(1), antipasti);
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(2), antipasti);
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(6), secondi);
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(7), secondi);
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(3));
            CatERing.getInstance().getMenuManager().insertItem((Recipe)recipes.get(4));
            System.out.println(m.testString());

            System.out.println("\nTEST DELETE SECTION WITH ITEMS");
            CatERing.getInstance().getMenuManager().deleteSection(antipasti, true);
            System.out.println(m.testString());

            System.out.println("\nTEST DELETE SECTION WITHOUT ITEMS");
            CatERing.getInstance().getMenuManager().deleteSection(primi, true);
            System.out.println(m.testString());

        } catch (UseCaseLogicException ex) {
            System.out.println("Errore di logica nello use case");
        }
    }
}
