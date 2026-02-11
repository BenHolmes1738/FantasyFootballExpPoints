package com.exppoints.fantasy;
/**
 * team object
 */
public class FuturePlayer {
    private final String name;
    private float rushTds;
    private float recTds;
    private float rushYds;
    private float recYds;
    private float rec;
    private float passYds;
    private float passTds;
    private float ints;

    public FuturePlayer(String name) {
        this.name = name;
        this.rushTds = 0;
        this.recTds = 0;
        this.rushYds = 0;
        this.recYds = 0;
        this.rec = 0;
        this.passYds = 0;
        this.passTds = 0;
        this.ints = 0;
    }

    public String getName() {
        return this.name;
    }

    public float getRushTds() {
        return this.rushTds;
    }

    public float getRecTds() {
        return this.recTds;
    }

    public float getRushYds() {
        return this.rushYds;
    }

    public float getRecYds() {
        return this.recYds;
    }

    public float getRec() {
        return this.rec;
    }

    public float getPassYds() {
        return this.passYds;
    }

    public float getPassTds() {
        return this.passTds;
    }

    public float getInts() {
        return this.ints;
    }

    public float getScore() {
        return this.rushTds * 6 + this.recTds * 6 + this.rushYds * 0.1f + this.recYds * 0.1f + this.rec + this.passYds * 0.04f + this.passTds * 4 - this.ints * 2;
    }

    public void setRushTds(float rushTds) {
        this.rushTds = rushTds;
    }

    public void setRecTds(float recTds) {
        this.recTds = recTds;
    }

    public void setRushYds(float rushYds) {
        this.rushYds = rushYds;
    }

    public void setRecYds(float recYds) {
        this.recYds = recYds;
    }

    public void setRec(float rec) {
        this.rec = rec;
    }

    public void setPassYds(float passYds) {
        this.passYds = passYds;
    }

    public void setPassTds(float passTds) {
        this.passTds = passTds;
    }

    public void setInts(float ints) {
        this.ints = ints;
    }
}