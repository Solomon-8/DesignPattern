package construction.composite;

public class Face extends HandSomeComposite{

    private String eyes;

    public Face(String eyes) {
        this.eyes = eyes;
    }

    @Override
    protected void printBefore() {
        System.out.print(eyes);
    }
}
