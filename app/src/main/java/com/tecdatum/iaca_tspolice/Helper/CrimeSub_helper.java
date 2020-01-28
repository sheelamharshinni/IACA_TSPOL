package com.tecdatum.iaca_tspolice.Helper;

/**
 * Created by HI on 1/12/2018.
 */

public class CrimeSub_helper {



    private String crimesubtypeid;
    private String crimetypeid;
    private String crimesubtypename;

    public String getCrimesubtypeid() {
        return crimesubtypeid;
    }

    public void setCrimesubtypeid(String crimesubtypeid) {
        this.crimesubtypeid = crimesubtypeid;
    }

    public String getCrimetypeid() {
        return crimetypeid;
    }

    public void setCrimetypeid(String crimetypeid) {
        this.crimetypeid = crimetypeid;
    }

    public String getCrimesubtypename() {
        return crimesubtypename;
    }

    public void setCrimesubtypename(String crimesubtypename) {
        this.crimesubtypename = crimesubtypename;
    }

    public CrimeSub_helper(String crimesubtypeid, String crimetypeid, String crimesubtypename) {
        this.crimesubtypeid = crimesubtypeid;
        this.crimetypeid = crimetypeid;
        this.crimesubtypename = crimesubtypename;
    }
}
