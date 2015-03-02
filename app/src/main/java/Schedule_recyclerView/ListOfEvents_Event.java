package Schedule_recyclerView;

public class ListOfEvents_Event {
    private String eventtitle;
    private String eventsch;
    private String eventdesc;
    private String eventcateg;

    public ListOfEvents_Event(String eventtitle, String eventsch, String eventdesc, String eventcateg) {
        this.eventtitle = eventtitle;
        this.eventsch = eventsch;
        this.eventdesc = eventdesc;
        this.eventcateg = eventcateg;
    }

    public String getEventtitle()
    { return eventtitle;}
    public String getEventsch()
    { return eventsch;}
    public String getEventdesc()
    { return eventdesc;}
    public String getEventcateg()
    { return eventcateg;}

}