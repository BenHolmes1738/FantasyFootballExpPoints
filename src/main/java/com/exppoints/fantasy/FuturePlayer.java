package com.exppoints.fantasy;
/**
 * team object
 */
public class FuturePlayer extends Player{
    private float rushTds;
    private float recTds;
    private float passTds;
    private float ints;

    public FuturePlayer(String name) {
        super(name);
        this.rushTds = 0;
        this.recTds = 0;
        this.passTds = 0;
        this.ints = 0;
    }

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

    @Override
    public float getScore() {
        return this.rushTds * 6 + this.recTds * 6 + this.rushYds * 0.1f + this.recYds * 0.1f + this.rec + this.passYds * 0.04f + this.passTds * 4 - this.ints * 2;
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
}