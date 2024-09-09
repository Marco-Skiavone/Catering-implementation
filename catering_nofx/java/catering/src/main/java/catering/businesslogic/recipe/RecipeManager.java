package catering.businesslogic.recipe;

import java.util.ArrayList;

public class RecipeManager {
    ArrayList<Task> tasks;
    public RecipeManager() {
        this.tasks = Recipe.loadAllRecipes();
    }

    public ArrayList<Task> getRecipes() {
        return Task.getAllRecipes();
    }
}
