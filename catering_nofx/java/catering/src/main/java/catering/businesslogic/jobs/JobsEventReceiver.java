package catering.businesslogic.jobs;
import catering.businesslogic.event.*;
import catering.businesslogic.user.*;

public interface JobsEventReceiver {
    void updateJobsSheetCreated(Service srv, JobsSheet js);
    void updateJobsSheetDeleted(Service srv);
    void updateJobsAdded(JobsSheet js, Job j);
    void updateJobModified(JobsSheet js, Job j);
    void updateJobDeleted(JobsSheet js, Job j);
    void updateJobAssigned(JobsSheet js, Job j);
    void updateAssignmentDeleted(Job j, AbstractUser usr);
    void updateJobsSheetRearranged(JobsSheet js);
}
