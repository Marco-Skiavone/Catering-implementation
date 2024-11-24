package catering.persistence;

import catering.businesslogic.event.*;
import catering.businesslogic.jobs.*;
import catering.businesslogic.user.*;

import java.sql.*;
import java.util.*;

public class JobPersistence implements JobsEventReceiver {
    // TODO

    @Override
    public void updateJobsSheetCreated(Service srv, JobsSheet js) {
        ArrayList<Job> jobs = js.getAllJobs();
        String insertStr = "INSERT INTO catering.Jobs (service_id, eta, portions, done, task_id) VALUES (?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(insertStr, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, srv.getId());
                ps.setInt(2, jobs.get(batchCount).getEta());
                ps.setInt(3, jobs.get(batchCount).getPortions());
                ps.setInt(4, jobs.get(batchCount).isDone() ? 1 : 0);
                ps.setInt(5, jobs.get(batchCount).getTask().getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                jobs.get(count).setId(rs.getInt(1));
            }
        });

        if (result != null && result.length > 0 && result[0] > 0) // jobs added
            System.out.println("Jobs added in database.");
        else
            System.out.println("Jobs could not be added in database.");
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
