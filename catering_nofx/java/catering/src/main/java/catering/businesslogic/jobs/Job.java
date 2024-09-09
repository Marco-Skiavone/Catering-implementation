package catering.businesslogic.jobs;

public class Job implements Comparable<Job> {

    /* Estimated Time of Arrival */
    private int eta;
    private int portions;
    private boolean done;
    private Cook worker;

    // Constructor (Based on 'create' method)
    public Job(Task t, Integer eta, Integer portions, Boolean done) {
        // Assuming Task has some functionality that would provide eta, portions, done if null.
        this.eta = (eta != null) ? eta : t.getDefaultEta();
        this.portions = (portions != null) ? portions : t.getDefaultPortions();
        this.done = (done != null) ? done : false;
    }

    // Method to modify a job
    public void modifyJob(Integer eta, Integer portions, Boolean done) {
        if (eta != null) {
            this.eta = eta;
        }
        if (portions != null) {
            this.portions = portions;
        }
        if (done != null) {
            this.done = done;
        }
    }

    /** Method to check if a worker has been assigned */
    public boolean isAssigned() {
        return this.worker != null;
    }

    /** Method to remove the assignment of a cook */
    public void removeAssignment() {
        this.worker = null;
    }

    /** Implementing compareTo method for Comparable<Job> */
    @Override
    public int compareTo(Job otherJob) {
        return Integer.compare(this.eta, otherJob.eta);
    }

    /** Getters and Setters for eta, portions, and done (optional) */
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

class Task {
    public int getDefaultEta() {
        return 0; // Placeholder value
    }

    public int getDefaultPortions() {
        return 1; // Placeholder value
    }
}

// Assuming there's a Cook class (this was referenced in the diagram)
class Cook {
    // Cook-related attributes and methods
}

