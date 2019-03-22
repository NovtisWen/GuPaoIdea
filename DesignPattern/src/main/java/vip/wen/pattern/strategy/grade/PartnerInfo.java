package vip.wen.pattern.strategy.grade;

public class PartnerInfo {

    private Integer userId;

    private Double investAmt;

    private Integer reconNum;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(Double investAmt) {
        this.investAmt = investAmt;
    }

    public Integer getReconNum() {
        return reconNum;
    }

    public void setReconNum(Integer reconNum) {
        this.reconNum = reconNum;
    }

    public Boolean calculateGrade(String gradeKey){
        Grade grade = GradeStategy.getGradeByKey(gradeKey);
        PartnerInfo info = new PartnerInfo();
        Boolean result = grade.calculateGrade(info);
        return result;
    }
}
