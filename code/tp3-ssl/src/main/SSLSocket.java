package main;

public interface SSLSocket {

    public String[] getSupportedProtocols() ;

    public String[] getEnabledProtocols();

    public void setEnabledProtocols(String[] protocols);

}
