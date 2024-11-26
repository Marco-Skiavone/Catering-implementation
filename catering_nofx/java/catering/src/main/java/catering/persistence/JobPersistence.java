package catering.persistence;

import catering.businesslogic.jobs.*;

import java.sql.*;
import java.util.*;

public class JobPersistence implements JobsEventReceiver {
    @Override
    public void updateJobsSheetCreated(JobsSheet js) {
        ArrayList<Job> jobs = js.getAllJobs();
        String insertStr = "INSERT INTO catering.Jobs (service_id, eta, portions, done, task_id) VALUES (?, ?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(insertStr, jobs.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, js.getService().getId());
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
            System.out.println("PERSISTENCE: Jobs added in database.");
        else
            System.err.println("PERSISTENCE: Jobs could not be added in database.");
    }

    /** We have to delete all the jobs from the Jobs table. */
    @Override
    public void updateJobsSheetDeleted(JobsSheet js) {
        String updDel = "DELETE FROM Jobs WHERE service_id = " + js.getService().getId() + ";";
        if (PersistenceManager.executeUpdate(updDel) > 0)
            System.out.println("PERSISTENCE: Jobs deleted.");
        else
            System.err.println("PERSISTENCE: Features could not be deleted.");
    }

    @Override
    public void updateJobAdded(JobsSheet js, Job j) {
        String insertJob = "INSERT INTO catering.Jobs (service_id, eta, portions, done, task_id)" +
                " VALUES (" + js.getService().getId() +
                ", " + j.getEta() +
                ", " + j.getPortions() +
                ", " + j.isDone() +
                ", " + j.getTask().getId() + ");";
        if (PersistenceManager.executeUpdate(insertJob) > 0)
            System.out.println("PERSISTENCE: Job " + j + " added.");
        else System.err.println("PERSISTENCE: Job " + j + " could not be added.");
    }

    @Override
    public void updateJobModified(Job j) {
        String insertJob = "UPDATE catering.Jobs SET eta = " + j.getEta() +
                ", portions = " + j.getPortions() +
                ", done = " + j.isDone() +
                ", onShift = " + j.getOnShift() +
                " WHERE id = " + j.getId() + ";";
        if (PersistenceManager.executeUpdate(insertJob) == 1)
            System.out.println("PERSISTENCE: Job " + j + " modified.");
        else System.err.println("PERSISTENCE: Job " + j + " could not be modified.");
    }

    @Override
    public void updateJobDeleted(Job j) {
        String insertJob = "DELETE catering.Jobs WHERE = " + j.getId() + ";";
        if (PersistenceManager.executeUpdate(insertJob) == 1)
            System.out.println("PERSISTENCE: Job " + j + " deleted.");
        else System.err.println("PERSISTENCE: Job " + j + " could not be deleted.");
    }

    @Override
    public void updateJobAssigned(Job j) {
        String insertJob = "UPDATE catering.Jobs SET worker_id = " + j.getWorker().getId() +
                " WHERE id = " + j.getId() + ";";
        if (PersistenceManager.executeUpdate(insertJob) > 0)
            System.out.println("PERSISTENCE: Job " + j + " modified.");
        else System.err.println("PERSISTENCE: Job " + j + " could not be modified.");
    }

    @Override
    public void updateAssignmentDeleted(Job j) {
        String insertJob = "UPDATE catering.Jobs SET worker_id = NULL" +
                " WHERE id = " + j.getId() + ";";
        if (PersistenceManager.executeUpdate(insertJob) == 1)
            System.out.println("PERSISTENCE: Job " + j + " modified.");
        else System.err.println("PERSISTENCE: Job " + j + " could not be modified.");
    }
}
