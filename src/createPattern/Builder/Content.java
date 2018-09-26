package createPattern.Builder;

/**
 * @author Solomon
 * @date 2018/9/26
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
 */
public class Content {
    private String stringA;
    private String stringB;
    private String stringC;

    private Content(){};

    private Content(Builder builder){
        stringA = builder.stringA;
        stringB = builder.stringB;
        stringC = builder.stringC;
    }
    private Content(BuilderA builderA){
        stringA = builderA.stringA;
        stringB = builderA.stringB;
        stringC = builderA.stringC;
    }

    @Override
    public String toString() {
        return "Content{" +
                "stringA='" + stringA + '\'' +
                ", stringB='" + stringB + '\'' +
                ", stringC='" + stringC + '\'' +
                '}';
    }

    public static class Builder{
        private String stringA;
        private String stringB;
        private String stringC;
        private String builderName;

        public Builder(String builderName) {
            this.builderName = builderName;
        }

        public Builder buildA(String a){
            stringA = a;
            return this;
        }

        public Builder buildB(String b){
            stringB = b;
            return this;
        }

        public Builder buildC(String c){
            stringC = c;
            return this;
        }

        public Content build(){
            System.out.println("Build from builderName:" + builderName);
            return new Content(this);
        }

    }

    public static class BuilderA{
        private String stringA;
        private String stringB;
        private String stringC;
        private String builderName;

        public BuilderA(String builderName) {
            this.builderName = builderName;
        }

        public BuilderA buildStringA(String a){
            stringA = a;
            return this;
        }

        public BuilderA buildStringB(String b){
            stringB = b;
            return this;
        }

        public BuilderA buildStringC(String c){
            stringC = c;
            return this;
        }

        public Content build(){
            System.out.println(builderName);
            return new Content(this);
        }


        public static BuilderA createDefault(){
            return new BuilderA("builder which create by default");
        }
    }
}
