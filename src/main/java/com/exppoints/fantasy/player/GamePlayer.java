package com.exppoints.fantasy.player;

// class for players to project upcoming game
public class GamePlayer extends Player {
    private float tdPercent;
    private float expTD;
    private float pTDpercent;
    private float intPercent;
    private float exppTD;
    private float expInts;

    // constructor
    public GamePlayer(String name) {
        super(name);
        this.tdPercent = 0;
        this.pTDpercent = 0;
        this.expInts = 0;
        this.intPercent = 0;
    }

    // getters
    public float getExpTds() {
        return Math.round(this.expTD*100.0f) / 100.0f;
    }

    public float getExpPTds() {
        return Math.round(this.exppTD*100.0f) / 100.0f;
    }

    public float getExpInts() {
        return Math.round(this.expInts*100.0f) / 100.0f;
    }

    @Override
    public float getScore() {
        float ret = (float)(this.expTD*6+super.rushYds*0.1+super.recYds*0.1+this.exppTD*4+super.passYds*0.04+this.expInts*-2);
        return Math.round(ret*100.0f) / 100.0f;
    }

    // setters
    public void setTds(float odds) {
        this.tdPercent = convertTdOddsToPct(odds);
        this.expTD = (float) Math.log(1-this.tdPercent) * -1;
    }

    public void setExpTds(float expTD) {
        this.expTD = expTD;
    }

    public void setPTds(float odds) {
        this.pTDpercent = convertTdOddsToPct(odds);
        this.exppTD = (float) Math.log(1-this.pTDpercent) * -1;
    }

    public void setExpPTds(float expPTD) {
        this.exppTD = expPTD;
    }

    public void setPTdsLambda(float odds, float line) {
        float percent = convertTdOddsToPct(odds);
        int lineInt = (int)(line += 0.5f);
        this.exppTD = solveLambda(percent, lineInt);
    }

    public void setInts(float odds) {
        this.intPercent = convertTdOddsToPct(odds);
        this.expInts = (float) Math.log(1-this.intPercent) * -1;
    }

    public void setExpInts(float expInts) {
        this.expInts = expInts;
    }

    public void setIntsLambda(float odds, float line) {
        float percent = convertTdOddsToPct(odds);
        int lineInt = (int)(line += 0.5f);
        this.expInts = solveLambda(percent, lineInt);
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

    public float solveLambda(float p, int n) {
        float low = 0.0f;
        float high = 10.0f;

        for (int iter = 0; iter < 100; iter++) {
            float mid = (low + high) / 2;

            float poissonTail = 1.0f - poissonCDF(mid, n-1);

            if (poissonTail > p) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return (low + high) / 2;
    }

    private float poissonCDF(float lambda, int k) {
        float sum = 0.0f;
        double term = Math.exp(-lambda);

        sum += term;

        for (int i = 1; i <= k; i++) {
            term *= lambda / i;
            sum += term;
        }

        return sum;
    }
}