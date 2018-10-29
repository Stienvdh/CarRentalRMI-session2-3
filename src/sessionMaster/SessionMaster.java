package sessionMaster;

import naming.INamingService;
import session.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionMaster implements ISessionMaster {

    private List<Session> activeSessions = new ArrayList<Session>();
    private INamingService namingService;

    public SessionMaster(INamingService namingService) {
        this.namingService = namingService;
    }

    public List<Session> getActiveSessions() {
        return this.activeSessions;
    }

}
