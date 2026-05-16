package model;

import enums.SessionStatus;

public class Session implements java.io.Serializable {

    private int sessionId;
    private String date;
    private String time;
    private Module module;
    private Group group;
    private SessionStatus status;
    private boolean locked;

    public Session(int sessionId,
                   String date,
                   String time,
                   Module module,
                   Group group) {

        this.sessionId = sessionId;
        this.date = date;
        this.time = time;
        this.module = module;
        this.group = group;
        this.status = SessionStatus.CLOSED;
        this.locked = false;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Module getModule() {
        return module;
    }

    public Group getGroup() {
        return group;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setDate(String date) {
        if (!locked) {
            this.date = date;
        }
    }

    public void setTime(String time) {
        if (!locked) {
            this.time = time;
        }
    }

    public void setStatus(SessionStatus status) {
        this.status = status;

        if (status == SessionStatus.CLOSED) {
            this.locked = true;
        }
    }

    public void openSession() {
        if (!locked) {
            this.status = SessionStatus.OPEN;
        }
    }

    public void closeSession() {
        this.status = SessionStatus.CLOSED;
        this.locked = true;
    }

    public boolean canEditAttendance() {
        return status == SessionStatus.OPEN && !locked;
    }

    @Override
    public String toString() {
        return "Session " + sessionId
                + " | " + module.getModuleName()
                + " | Teacher: " + module.getTeacher().getName()
                + " | Group: " + group.getGroupName()
                + " | " + date
                + " " + time
                + " | " + status;
    }
}