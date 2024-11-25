package catering.businesslogic.jobs;

public interface JobsEventReceiver {
    void updateJobsSheetCreated(JobsSheet js);
    void updateJobsSheetDeleted(JobsSheet js);
    void updateJobAdded(JobsSheet js, Job j);
    void updateJobModified(Job j);
    void updateJobDeleted(Job j);
    void updateJobAssigned(Job j);
    void updateAssignmentDeleted(Job j);
}
