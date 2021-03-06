package com.example.a390project.Model.TaskClasses;

public class InspectionTask {
    private int partCounted = 0;
    private int partAccepted = 0;
    private int partRejected = 0;

    InspectionTask(){}

    public int getPartCounted() {
        return partCounted;
    }

    public void setPartCounted(int partCounted) {
        this.partCounted = partCounted;
    }

    public int getPartAccepted() {
        return partAccepted;
    }

    public void setPartAccepted(int partAccepted) {
        this.partAccepted = partAccepted;
    }

    public int getPartRejected() {
        return partRejected;
    }

    public void setPartRejected(int partRejected) {
        this.partRejected = partRejected;
    }
}
