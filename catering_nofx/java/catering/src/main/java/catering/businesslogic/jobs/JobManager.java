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
    private final UserManager usrMgr;
    private final ShiftManager shfMgr;

    public JobManager() {
        eventReceivers = new HashSet<>();
        currentSheet = null;
        usrMgr = CatERing.getInstance().getUserManager();
        shfMgr = CatERing.getInstance().getShiftManager();
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
        AbstractUser usr = usrMgr.getCurrentUser();
        if (srv == null) throw new IllegalArgumentException();
        if (!usr.isChef())
            throw new UseCaseLogicException("Logged user is not a Chef!");
        if (!srv.isAssigned(usr))
            throw new SheetException("User is not assigned to the service!");
        this.currentSheet = new JobsSheet(srv);
        srv.setSummary(this.currentSheet);
        notifyJobsSheetCreated(srv, this.currentSheet);
    }

    public void modifySheet(JobsSheet sheet) {
        AbstractUser usr = usrMgr.getCurrentUser();
        if (sheet == null) throw new IllegalArgumentException();
        if (!usr.isChef())
            throw new UseCaseLogicException("Logged user is not a Chef!");
        if (!sheet.getService().isAssigned(usr))
            throw new SheetException("User is not assigned to the service!");
        this.currentSheet = sheet;
    }

    public void deleteSheet(Service srv) {
        AbstractUser usr = usrMgr.getCurrentUser();
        if (srv == null) throw new IllegalArgumentException();
        if (!usr.isChef())
            throw new UseCaseLogicException("Logged user is not a Chef!");
        if (!srv.isAssigned(usr))
            throw new SheetException("User is not assigned to the service!");
        if(srv.isExecuting())
            throw new SheetException("Service is executing!");
        JobsSheet sheet = srv.removeSummary();
        if (sheet == null) throw new NullPointerException();
        sheet.removeAllJobs();
        notifyJobsSheetDeleted(srv);
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
            notifyJobModified(currentSheet, j);
        } else throw new SheetException("Job is already done!");
        return j;
    }

    public void removeJob(Job j) {
        if (j == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        if (!j.isDone()) {
            currentSheet.removeJob(j);
            notifyJobDeleted(currentSheet, j);
        } else throw new SheetException("Job is already done!");
    }

    public ShiftTable getShiftTable() {
        return shfMgr.getShiftTable();
    }

    public void setWorker(Job j, AbstractUser usr) {
        if (j == null || usr == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        if (j.isDone() || j.isAssigned() || j.getOnShift() == null || !usr.isCook())
            throw new UseCaseLogicException("Exception inside setWorker method!");
        // NB: in the previous lines, "usr.isCook()" could be "usr.isStaff()"

        j.setWorker(usr);   // It can throw an Exception.
        notifyJobAssigned(currentSheet, j);
    }

    public void removeAssignment(Job j) {
        if (j == null) throw new IllegalArgumentException();
        if (currentSheet == null) throw new SheetException("Sheet not selected!");
        if (j.isDone() || !j.isAssigned())
            throw new UseCaseLogicException("Exception inside setWorker method!");
        AbstractUser usr = j.removeAssignment();
        notifyAssignmentDeleted(j, usr);
    }

    public void sortJobsSheet(Comparator<Job> cmp) {
        if (cmp == null) throw new IllegalArgumentException();
        if (this.currentSheet == null) throw new SheetException("Sheet not selected!");
        this.currentSheet.sortJobs(cmp);
        notifyJobsSheetRearranged(currentSheet);
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

    private void notifyJobsSheetCreated(Service srv, JobsSheet js) {
        eventReceivers.forEach(er -> er.updateJobsSheetCreated(srv, js));
    }

    private void notifyJobsSheetDeleted(Service srv) {
        eventReceivers.forEach(er -> er.updateJobsSheetDeleted(srv));
    }

    private void notifyJobAdded(JobsSheet js, Job j) {
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

    private void notifyAssignmentDeleted(Job j, AbstractUser usr) {
        eventReceivers.forEach(er -> er.updateAssignmentDeleted(j, usr));
    }

    private void notifyJobsSheetRearranged(JobsSheet js) {
        eventReceivers.forEach(er -> er.updateJobsSheetRearranged(js));
    }
}
