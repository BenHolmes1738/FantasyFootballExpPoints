package com.exppoints.fantasy;

// class for players to project future season
public class FuturePlayer extends Player{
    private float rushTds;
    private float recTds;
    private float passTds;
    private float ints;

    // constructor
    public FuturePlayer(String name) {
        super(name);
        this.rushTds = 0;
        this.recTds = 0;
        this.passTds = 0;
        this.ints = 0;
    }

    // getters and setters
    public float getRushTds() {
        return this.rushTds;
    }

    public float getRecTds() {
        return this.recTds;
    }

    public float getPassTds() {
        return this.passTds;
    }

    public float getInts() {
        return this.ints;
    }

    public void setRushTds(float rushTds) {
        this.rushTds = rushTds;
    }

    public void setRecTds(float recTds) {
        this.recTds = recTds;
    }

    public void setPassTds(float passTds) {
        this.passTds = passTds;
    }

    public void setInts(float ints) {
        this.ints = ints;
    }

    // calculate expected points based on PPR scoring
    @Override
    public float getScore() {
        float ret = this.rushTds * 6 + this.recTds * 6 + this.rushYds * 0.1f + this.recYds * 0.1f + this.rec + this.passYds * 0.04f + this.passTds * 4 - this.ints * 2;
        return Math.round(ret*100.0f) / 100.0f;
    }
}