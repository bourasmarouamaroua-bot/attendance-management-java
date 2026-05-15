package model;

public class Session implements java.io.Serializable {

    private int sessionId;
    private String date;
    private String time;
    private Module module;
    private Group group;

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

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return module.getModuleName()
                + " | "
                + group.getGroupName()
                + " | "
                + date
                + " "
                + time;
    }
}