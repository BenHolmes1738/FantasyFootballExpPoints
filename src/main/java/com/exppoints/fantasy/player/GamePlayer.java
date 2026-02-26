package com.exppoints.fantasy.player;

// class for players to project upcoming game
public class GamePlayer extends Player {
    private float yds;
    private float tdPercent;
    private float expTD;
    private float pTDpercent;
    private float intPercent;
    private float exppTD;
    private float expInts;

    // constructor
    public GamePlayer(String name) {
        super(name);
        this.yds = 0;
        this.tdPercent = 0;
        this.pTDpercent = 0;
        this.expInts = 0;
        this.intPercent = 0;
    }

    // getters and setters
    public float getTdPercent() {
        return this.tdPercent;
    }

    public float getExpTds() {
        return Math.round(this.expTD*100.0f) / 100.0f;
    }

    public float getExpPTDs() {
        return Math.round(this.exppTD*100.0f) / 100.0f;
    }

    public float getExpInts() {
        return Math.round(this.expInts*100.0f) / 100.0f;
    }

    public void setYds(float yds) {
        this.yds = yds;
    }

    // calculate expected points based on PPR scoring
    @Override
    public float getScore() {
        float ret = (float)(this.expTD*6+super.rushYds*0.1+super.recYds*0.1+this.exppTD*4+super.passYds*0.04+this.expInts*-2);
        return Math.round(ret*100.0f) / 100.0f;
    }

    public void setTds(float odds) {
        this.tdPercent = convertTdOddsToPct(odds);
        this.expTD = (float) Math.log(1-this.tdPercent) * -1;
    }

    public void setExpTds(float expTD) {
        this.expTD = expTD;
    }

    public void setPassTds(float odds) {
        this.pTDpercent = convertTdOddsToPct(odds);
        this.exppTD = (float) Math.log(1-this.pTDpercent) * -1;
    }

    // sets pass td percent, then converts to expected pass tds
    public void setExpPTds(float expPTD) {
        this.exppTD = expPTD;
    }

    public void setInts(float odds) {
        this.intPercent = convertTdOddsToPct(odds);
        this.expInts = (float) Math.log(1-this.intPercent) * -1;
    }

    // sets int percent, then converts to expected ints
    public void setExpInts(float expInts) {
        this.expInts = expInts;
    }

    // converts odds to percent chance of scoring a td
    public float convertTdOddsToPct(float odds) {
        if (odds == 0) {
            return 0;
        } else if (odds < 0) {
            odds *= -1;
            return odds/(odds+100);
        } else {
            return 100/(100+odds);
        }
    }
}