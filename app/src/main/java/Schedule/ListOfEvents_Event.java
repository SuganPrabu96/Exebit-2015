package Schedule;

import com.example.exebit.exebit2k15.SecondActivity;

public class ListOfEvents_Event {
    private String eventtitle;
    private String eventsch;
    private String eventdesc;
    private String eventcateg;
    private String eventstitle;
    private String shortdesc;
    private String eventformat;
    private String eventfaqfull;
    private String coorddetails;




    public ListOfEvents_Event(String eventtitle, String eventsch, String eventdesc, String eventcateg,String shortdesc,String eventformat , String eventfaqfull , String coorddetails) {
        this.eventtitle = eventtitle;
        this.eventsch = eventsch;
        this.eventdesc = eventdesc;
        this.eventcateg = eventcateg;

        this.shortdesc = shortdesc;
        this.eventformat = eventformat;
        this.eventfaqfull = eventfaqfull;
        this.coorddetails = coorddetails;
    }

    public String getEventtitle()
    { return eventtitle;}
    public String getEventsch()
    { return eventsch;}
    public String getEventdesc()
    { return eventdesc;}
    public String getEventcateg()
    { return eventcateg;}


    public String getShortdesc()
    {return shortdesc;}
    public String getEventformat()
    {return  eventformat;}
    public String getEventfaqfull()
    {return eventfaqfull;}
    public String getCoorddetails()
    {return coorddetails;}
}
