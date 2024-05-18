package engine.response;

public class LoadXmlResponse implements Response{
    private final String XmlPath;

    public LoadXmlResponse(String i_XmlPath) {
        this.XmlPath = i_XmlPath;
    }

    public String getXmlPath() {
        return XmlPath;
    }
}
