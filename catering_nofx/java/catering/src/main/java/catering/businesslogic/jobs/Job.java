package catering.businesslogic.jobs;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.recipe.Task;
import catering.businesslogic.user.Cook;

public class Job implements Comparable<Job> {

    private Task task;
    /* Estimated Time of Arrival */
    private int eta;        /* saved as minutes */
    private int portions;
    private boolean done;
    private Cook worker;

    public Job(Task t) {
        this.task = t;
        this.eta = -1;
        this.portions = -1;
        this.done = false;
    }

    public void modifyJob(Integer eta, Integer portions, Boolean done) {
        if (eta != null)
            this.eta = eta;
        if (portions != null)
            this.portions = portions;
        if (done != null)
            this.done = done;
    }

    public boolean isAssigned() {
        return this.worker != null;
    }

    public void removeAssignment() {
        this.worker = null;
    }

    /** Implementing compareTo method for Comparable<Job> */
    @Override
    public int compareTo(Job otherJob) {
        return Integer.compare(this.eta, otherJob.eta);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Cook getWorker() {
        return worker;
    }

    public void setWorker(Cook worker) {
        this.worker = worker;
    }
}
