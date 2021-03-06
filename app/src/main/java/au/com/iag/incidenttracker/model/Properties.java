package au.com.iag.incidenttracker.model;

import java.util.List;

public class Properties {

    String headline;

    String publicTransport;

    String adviceA;

    String adviceB;

    String otherAdvice;

    List<WebLink> webLinks;

    public String getHeadline()  {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getPublicTransport() {
        return publicTransport;
    }

    public void setPublicTransport(String publicTransport) {
        this.publicTransport = publicTransport;
    }

    public String getAdviceA() {
        return adviceA;
    }

    public void setAdviceA(String adviceA) {
        this.adviceA = adviceA;
    }

    public String getAdviceB() {
        return adviceB;
    }

    public void setAdviceB(String adviceB) {
        this.adviceB = adviceB;
    }

    public String getOtherAdvice() {
        return otherAdvice;
    }

    public void setOtherAdvice(String otherAdvice) {
        this.otherAdvice = otherAdvice;
    }

    public List<WebLink> getWebLinks() {
        return webLinks;
    }

    public void setWebLinks(List<WebLink> webLinks) {
        this.webLinks = webLinks;
    }
}
