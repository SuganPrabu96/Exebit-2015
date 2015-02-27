package Schedule_recyclerView;

public class ListOfEvents_Event {
    private String eventtitle;
    private String eventsch;
    private String eventdesc;

    public ListOfEvents_Event(String eventtitle, String eventsch, String eventdesc) {
        this.eventtitle = eventtitle;
        this.eventsch = eventsch;
        this.eventdesc = eventdesc;
    }

    public String getEventtitle()
    { return eventtitle;}
    public String getEventsch()
    { return eventsch;}
    public String getEventdesc()
    { return eventdesc;}

}