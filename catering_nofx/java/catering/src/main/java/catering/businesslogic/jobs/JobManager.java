package catering.businesslogic.jobs;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.*;
import catering.businesslogic.recipe.*;
import catering.businesslogic.user.*;
import catering.businesslogic.shift.*;
import java.util.*;

public class JobManager {
    private JobsSheet currentSheet;
    private final HashSet<JobsEventReceiver> eventReceivers;

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

    public void createJobsSheet(Service srv) {
        AbstractUser usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (srv == null) throw new IllegalArgumentException();
        if (!usr.isChef())
            throw new UseCaseLogicException("Logged user is not a Chef!");
        if (!srv.isAssigned(usr))
            throw new SheetException("User is not assigned to the service!");
        this.currentSheet = new JobsSheet(srv);
        srv.setSummary(this.currentSheet);
        notifyJobsSheetCreated(this.currentSheet);
    }

    public void modifySheet(JobsSheet sheet) {
        AbstractUser usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (sheet == null) throw new IllegalArgumentException();
        if (!usr.isChef())
            throw new UseCaseLogicException("Logged user is not a Chef!");
        if (!sheet.getService().isAssigned(usr))
            throw new SheetException("User is not assigned to the service!");
        this.currentSheet = sheet;
    }

    public void deleteSheet(Service srv) {
        AbstractUser usr = CatERing.getInstance().getUserManager().getCurrentUser();
        if (srv == null) throw new IllegalArgumentException();
        if (!usr.isChef())
            throw new UseCaseLogicException("Logged user is not a Chef!");
        if (!srv.isAssigned(usr))
            throw new SheetException("User is not assigned to the service!");
        if(srv.isExecuting())
            throw new SheetException("Service is executing!");
        JobsSheet sheet = srv.removeSummary();
        if (sheet == null) throw new NullPointerException();
        if (currentSheet == sheet) currentSheet = null;
        sheet.removeAllJobs();
        notifyJobsSheetDeleted(sheet);
    }

    public Job insertJob(Task t) {
        if (t == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        Job j0 = currentSheet.addJob(t);
        notifyJobAdded(currentSheet, j0);
        t.getIngredients().forEach(prep -> notifyJobAdded(currentSheet, currentSheet.addJob(prep)));
        return j0;
    }

    public Job modifyJob(Job j, Integer eta, Integer port) {
        return modifyJob(j, eta, port, null);
    }

    public Job modifyJob(Job j, Boolean done) {
        return modifyJob(j, null, null, done);
    }

    public Job modifyJob(Job j, Integer eta, Integer port, Boolean done) {
        // User has already retrieved the job from the currentSheet.
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        if (!j.isDone()) {
            j.modifyJob(eta, port, done);
            notifyJobModified(j);
        } else throw new SheetException("Job is already done!");
        return j;
    }

    public void removeJob(Job j) {
        if (j == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        if (!j.isDone()) {
            currentSheet.removeJob(j);
            notifyJobDeleted(j);
        } else throw new SheetException("Job is already done!");
    }

    public ShiftTable getShiftTable() {
        return CatERing.getInstance().getShiftManager().getShiftTable();
    }

    public void setWorker(Job j, AbstractUser usr) {
        if (j == null || usr == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        if (j.isDone() || j.isAssigned() || j.getOnShift() == null || !usr.isCook())
            throw new UseCaseLogicException("Exception inside setWorker method!");
        // NB: in the previous lines, "usr.isCook()" could be "usr.isStaff()"

        j.setWorker(usr);   // It can throw an Exception.
        notifyJobAssigned(j);
    }

    public void removeAssignment(Job j) {
        if (j == null) throw new IllegalArgumentException();
        if (currentSheet == null) throw new SheetException("Sheet not selected!");
        if (j.isDone() || !j.isAssigned())
            throw new UseCaseLogicException("Exception inside setWorker method!");
        AbstractUser usr = j.removeAssignment();
        notifyAssignmentDeleted(j);
    }

    public void sortJobsSheet(Comparator<Job> cmp) {
        if (cmp == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        this.currentSheet.sortJobs(cmp);
    }

    // --- Event sender Methods ---

    /**
     * @throws IllegalArgumentException if argument passed is null.
     * */
    public void addEventReceiver(JobsEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        eventReceivers.add(er);
    }

    /**
     * @throws IllegalArgumentException if argument passed is null or not present in the set. */
    public void removeEventReceiver(JobsEventReceiver er) {
        if (er == null) throw new IllegalArgumentException("Null receiver passed!");
        if (!eventReceivers.remove(er))
            throw new IllegalArgumentException("Receiver does not exist!");
    }

    private void notifyJobsSheetCreated(JobsSheet js) {
        eventReceivers.forEach(er -> er.updateJobsSheetCreated(js));
    }

    private void notifyJobsSheetDeleted(JobsSheet js) {
        eventReceivers.forEach(er -> er.updateJobsSheetDeleted(js));
    }

    private void notifyJobAdded(JobsSheet js, Job j) {
        eventReceivers.forEach(er -> er.updateJobAdded(js, j));
    }

    private void notifyJobModified(Job j) {
        eventReceivers.forEach(er -> er.updateJobModified(j));
    }

    private void notifyJobDeleted(Job j) {
        eventReceivers.forEach(er -> er.updateJobDeleted(j));
    }

    private void notifyJobAssigned(Job j) {
        eventReceivers.forEach(er -> er.updateJobAssigned(j));
    }

    private void notifyAssignmentDeleted(Job j) {
        eventReceivers.forEach(er -> er.updateAssignmentDeleted(j));
    }
}
