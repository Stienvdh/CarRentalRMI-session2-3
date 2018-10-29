package session;

import rental.*;

import java.util.Date;

public abstract class Session {

    private String sessionId;
    private Date creationDate;
    private INamingService namingService;

    public Session(INamingService namingService, String sessionId){
        this.sessionId = sessionId;
        this.namingService = namingService;
        this.creationDate = new Date();
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public INamingService getNamingService() {
        return namingService;
    }
}
