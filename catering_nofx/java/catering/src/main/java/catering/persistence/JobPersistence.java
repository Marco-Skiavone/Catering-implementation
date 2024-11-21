package catering.persistence;

import catering.businesslogic.event.Service;
import catering.businesslogic.jobs.Job;
import catering.businesslogic.jobs.JobsEventReceiver;
import catering.businesslogic.jobs.JobsSheet;
import catering.businesslogic.user.AbstractUser;

public class JobPersistence implements JobsEventReceiver {
    // TODO

    @Override
    public void updateJobsSheetCreated(Service srv, JobsSheet js) {

    }

    @Override
    public void updateJobsSheetDeleted(Service srv) {

    }

    @Override
    public void updateJobsAdded(JobsSheet js, Job j) {

    }

    @Override
    public void updateJobModified(JobsSheet js, Job j) {

    }

    @Override
    public void updateJobDeleted(JobsSheet js, Job j) {

    }

    @Override
    public void updateJobAssigned(JobsSheet js, Job j) {

    }

    @Override
    public void updateAssignmentDeleted(Job j, AbstractUser usr) {

    }

    @Override
    public void updateJobsSheetRearranged(JobsSheet js) {

    }
}
