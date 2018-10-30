package session;

import naming.INamingService;
import rental.*;
import sessionMaster.ISessionMaster;

import java.util.Date;

public abstract class Session {

    private String sessionId;
    private Date creationDate;
    private INamingService namingService;
    private ISessionMaster master;

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
