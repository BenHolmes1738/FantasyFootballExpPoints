package com.exppoints.fantasy;

// abstract class for players
public abstract class Player {
    protected final String name;
    protected float rushYds;
    protected float recYds;
    protected float rec;
    protected float passYds;

    // constructor
    public Player(String name) {
        this.name = name;
        this.rushYds = 0;
        this.recYds = 0;
        this.rec = 0;
        this.passYds = 0;
    }

    // getters and setters
    public String getName() {
        return this.name;
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

    // abstract method to calculate expected points
    public abstract float getScore();
}