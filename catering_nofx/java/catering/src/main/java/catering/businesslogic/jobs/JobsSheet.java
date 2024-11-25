package catering.businesslogic.jobs;

import catering.businesslogic.menu.*;
import catering.businesslogic.recipe.*;
import catering.businesslogic.user.*;
import catering.businesslogic.event.*;
import java.util.*;

public class JobsSheet {
    private final ArrayList<Job> jobs;
    private final Service service;

    public JobsSheet(Service service) {
        jobs = new ArrayList<>();
        if (service != null) {
            this.service = service;
            for (MenuItem elem : service.getMenu().getAllMenuItems()) {
                jobs.add(new Job(elem.getItemRecipe(), jobs.get(jobs.size()-1).getPos() +1));
                for (Preparation p : elem.getItemRecipe().getIngredients())
                    jobs.add(new Job(p, jobs.size()));
            }
        } else
            throw new IllegalArgumentException();
    }

    public Job addJob(Task t) {     // wrapper
        return addJob(t, null, null, null);
    }

    public Job addJob(Task t, Boolean done) {   // wrapper
        return addJob(t, null, null, done);
    }

    /**
     * @param eta can be null
     * @param port can be null */
    public Job addJob(Task t, Integer eta, Integer port, Boolean done) {
        if (t == null) throw new IllegalArgumentException("Task cannot be null");
        Job j = new Job(t);
        if (eta != null || port != null || done != null)
            j.modifyJob(eta, port, done);
        jobs.add(j);
        j.setPos(jobs.get(jobs.size()-1).getPos() +1);
        return j;
    }

    public void removeJob(Job j) {
        jobs.remove(j);
    }

    public void removeAllJobs() {
        jobs.clear();
    }

    public ArrayList<Job> getAllJobs( ) {
        return new ArrayList<>(jobs);
    }

    public Service getService() {
        return service;
    }

    public void sortJobs(Comparator<Job> cmp) {
        jobs.sort(cmp);
        jobs.forEach(el -> el.setPos(jobs.indexOf(el)));    // update pos for db!
    }

    @Override
    public String toString() {
        return "JobsSheet [" + jobs.toString() + "]";
    }
}
