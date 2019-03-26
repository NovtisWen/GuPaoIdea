package vip.wen.pattern.strategy.grade;

public class PlatinumGrade extends Grade{

    public PlatinumGrade(){
        this.setId(4);
        this.setGradeName("铂金合伙人");
        this.setGradeKey("4");
        this.setInterest(0.7);
        this.setMinInvest(7000.0);
        this.setRecoNum(7);
    }

    @Override
    public Boolean calculateGrade(PartnerInfo info) {
        return null;
    }
}
