package catering.businesslogic.shift;

import catering.businesslogic.event.ServiceInfo;

import java.time.*;

public class ServiceShift extends AbstractShift {
    private final ServiceInfo service;
    private Long earlyTime;
    private Long postTime;

    public ServiceShift(ServiceInfo srv, Long earlyTime, Long postTime, Period daysToRet) {
        super.daysToRetire = daysToRet;
        // super.start; missing implementation
        // super.end; missing implementation
        this.service = srv;
        this.earlyTime = earlyTime;
        this.postTime = postTime;
    }

    public ServiceInfo getService() {
        return service;
    }

    public String getLocation() {
        //return service.getLocation;
        return "null";    // missing implementation!
    }

    public Long getEarlyTime() { return earlyTime; }

    public Long getPostTime() { return postTime; }

    public void setEarlyTime(Long earlyTime) {
        this.earlyTime = earlyTime;
    }

    public void setPostTime(Long postTime) {
        this.postTime = postTime;
    }
}
