package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/11/9.
 */

public class PercentInfo {

    //能量
    private String energy;
    //蛋白质
    private String protein;
    //脂肪
    private String fat;
    //碳水化合物
    private String cWater;
    //钠
    private String na;

    public PercentInfo() {
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getcWater() {
        return cWater;
    }

    public void setcWater(String cWater) {
        this.cWater = cWater;
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }
}
