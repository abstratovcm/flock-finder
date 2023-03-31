public class CattleWeight {
    private final int id;
    private String cattleId;
    private double weight;

    public CattleWeight(int id, String cattleId, double weight) {
        this.id = id;
        this.cattleId = cattleId;
        this.weight = weight;
    }

    // Getter for cattleId
    public String getCattleId() {
        return cattleId;
    }

    // Setter for cattleId
    public void setCattleId(String cattleId) {
        this.cattleId = cattleId;
    }

    // Getter for weight
    public double getWeight() {
        return weight;
    }

    // Setter for weight
    public void setWeight(double weight) {
        this.weight = weight;
    }
}