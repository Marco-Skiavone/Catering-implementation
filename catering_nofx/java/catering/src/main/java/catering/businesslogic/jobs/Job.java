package catering.businesslogic.jobs;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.recipe.*;
import catering.businesslogic.shift.*;
import catering.businesslogic.user.*;

public class Job implements Comparable<Job> {
    private int id;
    private Task task;
    /* Estimated Time of Arrival */
    private int eta;        /* saved as minutes */
    private int portions;
    private boolean done;
    private AbstractUser worker;
    private AbstractShift onShift;

    public Job(Task t) {
        id = t.hashCode() + this.hashCode();
        this.task = t;
        this.eta = -1;
        this.portions = -1;
        this.done = false;
        this.onShift = null;
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

    public AbstractUser removeAssignment() {
        AbstractUser removed = this.worker;
        this.worker = null;
        return removed;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public AbstractUser getWorker() {
        return worker;
    }

    public void setWorker(AbstractUser worker) throws UseCaseLogicException {
        if (this.onShift.isWorkerAvailableIn(worker))
            this.worker = worker;
        else throw new UseCaseLogicException("Worker not available in selected shift!");
    }

    public AbstractShift getOnShift() {
        return onShift;
    }

    public void setOnShift(AbstractShift onShift) {
        this.onShift = onShift;
    }
}
