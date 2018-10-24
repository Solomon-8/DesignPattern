package construction.flyweight;

import java.util.HashMap;

/**
 * @author Solomon
 * @date 2018/10/23
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
public class AssholeFlyWeight {
    private final HashMap<String,Asshole> assholeHashMap;

    public AssholeFlyWeight() {
        this.assholeHashMap = new HashMap<>();
    }


    public Asshole createAsshole(String name){
        Asshole asshole = assholeHashMap.get(name);
        if (asshole == null){
            switch (name){
                case "pzf":
                    asshole = new PengAsshole();
                    assholeHashMap.put(name,asshole);
                    break;
                case "liu":
                    asshole = new LiuAsshole();
                    assholeHashMap.put(name,asshole);
                    break;
                case "ou":
                    asshole = new OuAsshole();
                    assholeHashMap.put(name,asshole);
                    break;
                default:
                    throw new RuntimeException("this asshole is not in.");
            }
        }
        return asshole;
    }
}
