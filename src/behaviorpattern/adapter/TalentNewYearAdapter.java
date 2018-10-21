package behaviorpattern.adapter;

/**
 * @author Solomon
 * @date 2018/10/21
 * if you founded any bugs in my code
 * look at my face
 * that's a feature
 * ─ wow ──▌▒█───────────▄▀▒▌───
 * ────────▌▒▒▀▄───────▄▀▒▒▒▐───
 * ───────▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐───
 * ─────▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐───
 * ───▄▀▒▒▒▒▒▒ such difference ─
 * ──▐▒▒▒▄▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▒▌──
 * ──▌▒▒▐▄█▀▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐──
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▌██▀▒▒▒▒▒▒▒▒▀▄▌─
 * ─▌▒▀▄██▄▒▒▒▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─
 * ─▌▀▐▄█▄█▌▄▒▀▒▒▒▒▒▒░░░░░░▒▒▒▐─
 * ▐▒▀▐▀▐▀▒▒▄▄▒▄▒▒▒ electrons ▒▌
 * ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒░░░░░░▒▒▒▐─
 * ─▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒▒▒░░░░▒▒▒▒▌─
 * ─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐──
 * ──▀ amaze ▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▌──
 * ────▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀───
 * ───▐▀▒▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀─────
 * ──▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▀────────
 * " "
 */
public class TalentNewYearAdapter extends PengYuYanOfTaoYuan implements BegLuckyMoney {

    private BegLuckyMoney begLuckyMoney;

    public TalentNewYearAdapter(BegLuckyMoney begLuckyMoney) {
        this.begLuckyMoney = begLuckyMoney;
    }

    @Override
    public void newYear() {
        begLuckyMoney.newYear();
    }

    public static void main(String[] args) {
        TalentNewYearAdapter newYearAdapter = new TalentNewYearAdapter(new ChildBegMoney());
        newYearAdapter.newYear();
        newYearAdapter.handsome();
    }

}
