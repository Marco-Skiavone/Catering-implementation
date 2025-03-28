package catering.businesslogic.recipe;

import catering.businesslogic.user.AbstractUser;
import catering.persistence.PersistenceManager;
import catering.persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Task {
    private static Map<Integer, Task> all = new HashMap<>();

    private int id;
    private String name;
    private AbstractUser owner;
    private String[] steps;
    private String[] notes;
    private ArrayList<Preparation> ingredients;

    protected Task() {
        ingredients = new ArrayList<>();
    }

    public Task(String name) {
        this();
        this.id = 0;
        this.name = name;
    }

    public boolean isOwner(AbstractUser user) {
        // Implement logic to check if the user is the owner
        return owner != null && owner.equals(user); // Placeholder return value
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ArrayList<Task> loadAllRecipes() {
        String query = "SELECT * FROM Tasks";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Task task = all.get(id);
                    task.name = rs.getString("name");
                    task.ingredients = new ArrayList<>();       // missing implementation (not required)
                    /* @todo create a function to retrieve ingredients
                    String[] ingr = (rs.getString("ingredients").split(","));
                    for(String s : ingr) {
                        task.ingredients.add(rs.parseInt(s));
                    }*/
                } else {
                    Recipe rec = new Recipe(rs.getString("name"));
                    rec.setId(id);
                    all.put(rec.getId(), rec);
                }
            }
        });
        ArrayList<Task> ret = new ArrayList<>(all.values());
        ret.sort(Comparator.comparing(Task::getName));
        return ret;
    }

    public static ArrayList<Task> getAllRecipes() {
        return new ArrayList<>(all.values());
    }

    public ArrayList<Preparation> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public static Task loadRecipeById(int id) {
        if (all.containsKey(id))
            return all.get(id);
        Task rep = new Task();
        String query = "SELECT * FROM Tasks WHERE id = " + id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                rep.setName(rs.getString("name"));
                rep.setId(id);
                all.put(rep.getId(), rep);
            }
        });
        return rep;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }
}
