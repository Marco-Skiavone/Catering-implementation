package catering.businesslogic;

import catering.businesslogic.event.EventManager;
import catering.businesslogic.menu.MenuManager;
import catering.businesslogic.recipe.RecipeManager;
import catering.businesslogic.user.UserManager;
import catering.businesslogic.jobs.JobManager;
import catering.businesslogic.shift.ShiftManager;
import catering.persistence.*;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private JobManager jobMgr;
    private EventManager eventMgr;
    private ShiftManager shiftMgr;

    private MenuPersistence menuPersistence;
    private UserPersistence userPersistence;
    private JobPersistence jobPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        jobMgr = new JobManager();
        eventMgr = new EventManager();
        shiftMgr = new ShiftManager();
        menuPersistence = new MenuPersistence();
        userPersistence = new UserPersistence();
        jobPersistence = new JobPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        userMgr.addEventReceiver(userPersistence);
        jobMgr.addEventReceiver(jobPersistence);
        // eventMgr.addEventReceiver(eventPersistence);
        // shiftMgr.addEventReceiver(shiftPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public JobManager getJobManager() { return jobMgr; }

    public EventManager getEventManager() { return eventMgr; }

    public ShiftManager getShiftManager() { return shiftMgr; }
}
