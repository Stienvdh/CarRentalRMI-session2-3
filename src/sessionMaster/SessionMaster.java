package sessionMaster;

import session.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionMaster implements ISessionMaster {

    private List<Session> activeSessions = new ArrayList<Session>();

    public List<Session> getActiveSessions() {
        return this.activeSessions;
    }

}
