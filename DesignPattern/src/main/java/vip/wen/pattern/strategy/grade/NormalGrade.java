package vip.wen.pattern.strategy.grade;

public class NormalGrade extends Grade{

    public NormalGrade(){
        this.setId(1);
        this.setGradeName("青铜合伙人");
        this.setGradeKey("1");
        this.setInterest(0.1);
        this.setMinInvest(1000.0);
        this.setRecoNum(1);
    }

    @Override
    public Boolean calculateGrade(PartnerInfo info) {
        return null;
    }
}
