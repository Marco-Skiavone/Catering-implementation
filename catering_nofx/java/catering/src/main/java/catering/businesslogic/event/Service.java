package catering.businesslogic.event;

import catering.businesslogic.jobs.*;
import catering.businesslogic.menu.*;
import catering.businesslogic.user.*;
import java.time.*;

public class Service {
    private int id;
    String address;
    LocalDateTime startTime;
    LocalDateTime endTime;
    AbstractUser assignedChef;
    JobsSheet summary;
    Menu menuInUse;

    public Service() {
        this.id = 0;
        this.address = "";
        this.startTime = null;
        this.endTime = null;
        this.assignedChef = null;
        this.summary = null;
        this.menuInUse = null;
    }

    public Service(Menu menuInUse) {
        if (menuInUse == null) throw new IllegalArgumentException();
        this.menuInUse = menuInUse;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

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

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public void updateChef(AbstractUser usr) {
        this.assignedChef = usr;
    }

    public boolean isAssigned(AbstractUser usr) {
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

    @Override
    public String toString() {
        return "Service [" + "id:" + id + ", summary: " + summary + "]";
    }
}
