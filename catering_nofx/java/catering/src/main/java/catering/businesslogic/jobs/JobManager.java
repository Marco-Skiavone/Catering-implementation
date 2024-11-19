package catering.businesslogic.jobs;

import catering.businesslogic.event.*;
import catering.businesslogic.user.*;
import java.util.*;

public class JobManager {
    private JobsSheet currentSheet;
    private HashSet<JobsEventReceiver> eventReceivers;

    public JobManager() {
        eventReceivers = new HashSet<>();
        currentSheet = null;
    }

    public JobsSheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(JobsSheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public List<JobsEventReceiver> getEventReceivers() {
        return new ArrayList<>(eventReceivers);
    }

    // --- Operation methods ---

    public void createJobsSheet(JobsSheet sheet) {
        // @todo [...]
    }


    // --- Event sender Methods ---

    /**
     * @throws IllegalArgumentException if argument passed is null.
     * */
    public void addReceiver(JobsEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        eventReceivers.add(er);
    }

    /**
     * @throws IllegalArgumentException if argument passed is null or not present in the set. */
    public void removeReceiver(JobsEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        if (!eventReceivers.remove(er))
            throw new IllegalArgumentException("Receiver does not exist!");
    }

    private void notifyJobsSheetCreated(Service srv, JobsSheet js) {
        eventReceivers.forEach(er -> er.updateJobsSheetCreated(srv, js));
    }

    private void notifyJobsSheetDeleted(Service srv) {
        eventReceivers.forEach(er -> er.updateJobsSheetDeleted(srv));
    }

    private void notifyJobsAdded(JobsSheet js, Job j) {
        eventReceivers.forEach(er -> er.updateJobsAdded(js, j));
    }

    private void notifyJobModified(JobsSheet js, Job j) {
        eventReceivers.forEach(er -> er.updateJobModified(js, j));
    }

    private void notifyJobDeleted(JobsSheet js, Job j) {
        eventReceivers.forEach(er -> er.updateJobDeleted(js, j));
    }

    private void notifyJobAssigned(JobsSheet js, Job j) {
        eventReceivers.forEach(er -> er.updateJobAssigned(js, j));
    }

    private void notifyAssignmentDeleted(Job j, User usr) {
        eventReceivers.forEach(er -> er.updateAssignmentDeleted(j, usr));
    }

    private void notifyJobsSheetRearranged(JobsSheet js) {
        eventReceivers.forEach(er -> er.updateJobsSheetRearranged(js));
    }
}
