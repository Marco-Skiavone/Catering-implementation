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
        String insertStr = "INSERT INTO catering.Jobs (service_id, eta, portions, done, pos, task_id) VALUES (?, ?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(insertStr, jobs.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, srv.getId());
                ps.setInt(2, jobs.get(batchCount).getEta());
                ps.setInt(3, jobs.get(batchCount).getPortions());
                ps.setInt(4, jobs.get(batchCount).isDone() ? 1 : 0);
                ps.setInt(5, batchCount);
                ps.setInt(6, jobs.get(batchCount).getTask().getId());
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

    /** We have to delete all the jobs from the Jobs table. */
    @Override
    public void updateJobsSheetDeleted(Service srv) {
        String updDel = "DELETE FROM Jobs WHERE service_id = " + srv.getId();
        if (PersistenceManager.executeUpdate(updDel) > 0)
            System.out.println("Jobs deleted.");
        else
            System.out.println("Features could not be deleted.");
    }

    @Override
    public void updateJobsAdded(JobsSheet js, Job j) {
        String insertJob = "INSERT INTO catering.Jobs (service_id, eta, portions, done, pos, task_id)" +
                " VALUES (" + js.getService().getId() +
                ", " + j.getEta() +
                ", " + j.getPortions() +
                ", " + j.isDone() +
                ", " + js.getAllJobs().indexOf(j) + /* good also j.getPos() */
                ", " + j.getTask().getId() + ");";
        if (PersistenceManager.executeUpdate(insertJob) > 0)
            System.out.println("Job " + j + " added.");
        else System.out.println("Job " + j + " could not be added.");
    }

    @Override
    public void updateJobModified(JobsSheet js, Job j) {
        String insertJob = "UPDATE catering.Jobs SET eta = " + j.getEta() +
                ", portions = " + j.getPortions() +
                ", done = " + j.isDone() +
                ", pos = " + j.getPos() +
                ", onShift = " + j.getOnShift() +
                "; WHERE id = " + j.getId();
        if (PersistenceManager.executeUpdate(insertJob) == 1)
            System.out.println("Job " + j + " modified.");
        else System.out.println("Job " + j + " could not be modified.");
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
