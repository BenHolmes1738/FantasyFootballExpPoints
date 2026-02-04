/**
 * team object
 */
public class Player {
    private final String name;
    private final String position;
    private float tdPercent;
    private float yds;
    private float rec;
    private float expTD;
    private float pYds;
    private float pTDpercent;
    private float intPercent;
    private float exppTD;
    private float ints;
    private float expInts;

    public Player(String name, String position) {
        this.name = name;
        this.position = position;
        this.tdPercent = 0;
        this.yds = 0;
        this.rec = 0;
        this.pYds = 0;
        this.pTDpercent = 0;
        this.ints = 0;
        this.intPercent = 0;
    }

    public String getName() {
        return this.name;
    }

    public String getPosition() {
        return this.position;
    }

    public float getTdPercent() {
        return this.tdPercent;
    }

    public float getYds() {
        return this.yds;
    }

    public float getRec() {
        return this.rec;
    }

    public float getExpTds() {
        return this.expTD;
    }

    public float getPYds() {
        return this.pYds;
    }

    public float getExpPTDs() {
        return this.exppTD;
    }

    public float getExpInts() {
        return this.expInts;
    }

    public float getInts() {
        return this.ints;
    }

    public float getScore() {
        if (this.position.equals("QB")) {
            return (float)(this.getExpTds()*6+this.getYds()*0.1+this.getExpPTDs()*4+this.getPYds()*0.04+this.getExpInts()*-2);
        } else {
            return (float)(this.getExpTds()*6+this.getYds()*0.1+this.getRec());
        }
    }

    public float getQBScore() {
        return (float)(this.getExpTds()*6+this.getYds()*0.1+this.getExpPTDs()*4+this.getPYds()*0.04+this.getExpInts()*-2);
    }

    public void setTdPercent(float tdPercent) {
        this.tdPercent = tdPercent;
        this.expTD = (float) Math.log(1-tdPercent) * -1;
    }

    public void setYds(float yds) {
        this.yds = yds;
    }

    public void setRec(float rec) {
        this.rec = rec;
    }

    public void setPYds(float PYds) {
        this.pYds = PYds;
    }

    public void setPTdPercent(float ptdPercent) {
        this.pTDpercent = ptdPercent;
        this.exppTD = (float) Math.log(1-ptdPercent) * -1;
    }

    public void setIntPercent(float intPercent) {
        this.pTDpercent = intPercent;
        this.expInts = (float) Math.log(1-intPercent) * -1;
    }

    public void setInts(float ints) {
        this.ints = ints;
    }

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