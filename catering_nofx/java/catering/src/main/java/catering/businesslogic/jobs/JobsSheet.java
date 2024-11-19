package catering.businesslogic.jobs;

import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import catering.businesslogic.user.Cook;
import java.util.*;

public class JobsSheet {
    private HashMap<Integer, Job> jobs = new HashMap<>();
    private Menu currentMenu;

    public JobsSheet(Menu menu) {
        this.currentMenu = menu;
        if (menu != null)
            for(MenuItem elem : currentMenu.getFreeItems()) {
                Job j = new Job(elem.getItemRecipe());
                jobs.put(j.getId(), j);
            }
    }

    public Job addJob(Task t) {     // wrapper
        return addJob(t, null, null, null);
    }

    public Job addJob(Task t, Boolean done) {   // wrapper
        return addJob(t, null, null, done);
    }

    /**
     * @param eta can be null
     * @param port can be null
     * */
    public Job addJob(Task t, Integer eta, Integer port, Boolean done) {
        if (t == null) throw new IllegalArgumentException("Task cannot be null");
        Job j = new Job(t);
        if (eta != null || port != null || done != null)
            j.modifyJob(eta, port, done);
        jobs.put(j.getId(), j);
        return j;
    }

    public void removeJob(Job j) {

    }

    public ArrayList<Job> getAllJobs( ) {
        return new ArrayList<>(jobs.values());
    }

    public void moveJob(Job j, int pos) {

    }

    public void setWorker(Job job, Cook usr) {

    }

    public void sortJobs(Comparator<Job> cmp) {

    }
}
