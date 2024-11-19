package catering.businesslogic.event;

import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.user.*;
import java.time.*;

public class Service {
    String address;
    LocalDateTime startTime;
    LocalDateTime endTime;
    User assignedChef;
    JobsSheet summary;
    Menu menuInUse;

    public Service(Menu menuInUse) {
        if (menuInUse == null) throw new IllegalArgumentException();
        this.menuInUse = menuInUse;
    }

    public JobsSheet getSummary() {
        return this.summary;
    }

    public void setSummary(JobsSheet sum) {
        if (sum != null)
            summary = sum;
        else throw new IllegalArgumentException();
    }

    public void setStartTime(LocalDateTime d) {
        if (d != null)
            startTime = d;
        else throw new IllegalArgumentException();
    }

    public void setEndTime(LocalDateTime d) {
        if (d != null)
            endTime = d;
        else throw new IllegalArgumentException();
    }

    public Menu getMenu() {
        return menuInUse;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void updateChef(User usr) {
        this.assignedChef = usr;
    }

    public boolean isAssigned(User usr) {
        return usr.equals(assignedChef);
    }

    public JobsSheet removeSummary()  {
        JobsSheet sheet = this.summary;
        summary = null;
        return sheet;
    }

    public boolean isExecuting() {
        return LocalDateTime.now().isBefore(endTime) &&
                LocalDateTime.now().isAfter(startTime);
    }
}
