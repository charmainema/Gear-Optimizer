package main.model.gear;

// represents a gear stat with an integer boost value, school, and name
// stat boosts are considered equal if they have the same school and type
public class StatBoost {
    private int boost;
    private String school;
    private String type;

    // REQUIRES: school is one of "life", "death", "myth", "ice", "fire",
    // "storm", "balance" && boost >= 0
    // EFFECTS: constructs a stat boost with a boost value, school, and name
    public StatBoost(int boost, String school, String type) {
        this.boost = boost;
        this.school = school;
        this.type = type;
    }

    // REQUIRES: boost >= 0
    // MODIFIES: this
    // EFFECTS: sets this.boost to boost
    public void setBoost(int boost) {
        this.boost = boost;
    }

    public int getBoost() {
        return boost;
    }

    public String getSchool() {
        return school;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((school == null) ? 0 : school.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatBoost other = (StatBoost) obj;
        if (school == null) {
            if (other.school != null)
                return false;
        } else if (!school.equals(other.school))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}
