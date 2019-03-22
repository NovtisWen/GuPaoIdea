package vip.wen.pattern.strategy.grade;

public abstract class Grade {

    //id
    private Integer id;
    //等级名称
    private String gradeName;

    private String gradeKey;
    //利率
    private Double interest;

    private Double minInvest;

    private Integer recoNum;

    public String getGradeKey() {
        return gradeKey;
    }

    public void setGradeKey(String gradeKey) {
        this.gradeKey = gradeKey;
    }

    public Double getMinInvest() {
        return minInvest;
    }

    public void setMinInvest(Double minInvest) {
        this.minInvest = minInvest;
    }

    public Integer getRecoNum() {
        return recoNum;
    }

    public void setRecoNum(Integer recoNum) {
        this.recoNum = recoNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest)
    {
        this.interest = interest;
    }

    public abstract Boolean calculateGrade(PartnerInfo info);
}
