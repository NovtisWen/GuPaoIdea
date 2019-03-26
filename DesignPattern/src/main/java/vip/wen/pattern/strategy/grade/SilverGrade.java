package vip.wen.pattern.strategy.grade;

public class SilverGrade extends Grade{

    public SilverGrade(){
        this.setId(2);
        this.setGradeName("白银合伙人");
        this.setGradeKey("2");
        this.setInterest(0.3);
        this.setMinInvest(3000.0);
        this.setRecoNum(3);
    }

    @Override
    public Boolean calculateGrade(PartnerInfo info) {
        return null;
    }
}
