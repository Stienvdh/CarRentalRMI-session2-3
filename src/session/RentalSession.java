package session;

public class RentalSession extends Session implements IRentalSession {

    private String clientName;

    public RentalSession(INamingService namingService, String sessionId, String clientName) {
        super(namingService, sessionId);
        this.clientName = clientName;
    }

    public Quote createQuote() {
        return null;
    }

    public void confirmQuote(ReservationConstraints constraints) {

    }

}
