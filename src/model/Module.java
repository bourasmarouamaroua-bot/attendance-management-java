package model;

public class Module implements java.io.Serializable {

    private int moduleId;
    private String moduleName;
    private Teacher teacher;

    public Module(int moduleId,
                  String moduleName,
                  Teacher teacher) {

        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.teacher = teacher;
    }

    public int getModuleId() {
        return moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {

        return moduleName
                + " - Teacher: "
                + teacher.getName();
    }
}