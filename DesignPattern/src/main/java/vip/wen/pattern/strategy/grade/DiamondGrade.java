package vip.wen.pattern.strategy.grade;

public class DiamondGrade extends Grade{

    public DiamondGrade(){
        this.setId(5);
        this.setGradeName("钻石合伙人");
        this.setGradeKey("5");
        this.setInterest(1.0);
        this.setMinInvest(10000.0);
        this.setRecoNum(10);
    }

    @Override
    public Boolean calculateGrade(PartnerInfo info) {
        return null;
    }
}
