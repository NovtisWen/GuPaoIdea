package vip.wen.pattern.strategy.grade;

public class GoldGrade extends Grade{

    public GoldGrade(){
        this.setId(3);
        this.setGradeName("黄金合伙人");
        this.setGradeKey("3");
        this.setInterest(0.5);
        this.setMinInvest(5000.0);
        this.setRecoNum(5);
    }

    @Override
    public Boolean calculateGrade(PartnerInfo info) {
        return null;
    }
}
