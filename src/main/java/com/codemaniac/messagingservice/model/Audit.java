package com.codemaniac.messagingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Calendar;

@Embeddable
public class Audit implements Serializable {
    public static final String PROGRAM = "MY-APP";
    public static final String SYSTEM = "SYSTEM";
    public static final String RECORD_STATUS_DELETED = "D";
    public static final String RECORD_STATUS_ACTIVE = "A";
    private static final long serialVersionUID = 1L;

    @Column(name = "CREATED_BY")
    private String createdBy = "";

    @Column(name = "CREATE_MODULE")
    private String createModule = "";

    @Column(name = "CREATE_TIMESTAMP")
    private Calendar createTimestamp;

    @Column(name = "RECORD_STATUS")
    private String recordStatus = RECORD_STATUS_ACTIVE;

    @Column(name = "UPDATED_BY")
    private String updatedBy = "";

    @Column(name = "UPDATE_MODULE")
    private String updateModule = "";

    @Column(name = "UPDATE_TIMESTAMP")
    private Calendar updateTimestamp;

    public Audit() {
        // Default.
    }

    public Audit(String newCreatedBy, String newCreateModule) {
        createdBy = newCreatedBy;
        createModule = newCreateModule;
        createTimestamp = Calendar.getInstance();
        recordStatus = RECORD_STATUS_ACTIVE;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("createdBy", this.createdBy);
        builder.append("createModule", this.createModule);
        builder.append("createTimestamp", (this.createTimestamp != null) ? this.createTimestamp.getTime() : null);

        builder.append("updatedBy", this.updatedBy);
        builder.append("updateModule", this.updateModule);
        builder.append("updateTimestamp", (this.updateTimestamp != null) ? this.updateTimestamp.getTime() : null);

        builder.append("recordStatus", this.recordStatus);

        return builder.toString();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreateModule() {
        return createModule;
    }

    public Calendar getCreateTimestamp() {
        return createTimestamp;
    }

    public String getLastModifiedBy() {
        if ((updatedBy != null) && (updatedBy.length() > 0)) {
            return updatedBy;
        }

        return createdBy;
    }

    public Calendar getLastModifiedByTimestamp() {
        if (updateTimestamp != null) {
            return updateTimestamp;
        }

        return createTimestamp;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUpdateModule() {
        return updateModule;
    }

    public Calendar getUpdateTimestamp() {
        return updateTimestamp;
    }

    public boolean isRecordActive() {
        return RECORD_STATUS_ACTIVE.equals(recordStatus);
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreateModule(String createModule) {
        this.createModule = createModule;
    }

    public void setCreates(String newCreatedBy, String newCreateModule) {
        createdBy = newCreatedBy;
        createModule = newCreateModule;
        createTimestamp = Calendar.getInstance();
    }

    public void setCreateTimestamp(Calendar createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdateModule(String updateModule) {
        this.updateModule = updateModule;
    }

    public void setUpdates(String newUpdatedBy, String newUpdateModule) {
        this.updatedBy = newUpdatedBy;
        this.updateModule = newUpdateModule;
        this.updateTimestamp = Calendar.getInstance();
    }

    public void setUpdateTimestamp(Calendar updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}

