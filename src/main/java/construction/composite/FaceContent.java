package construction.composite;

import java.util.List;

public class FaceContent extends HandSomeComposite{
    public FaceContent(List<Face> faces) {
        for (Face face : faces){
            this.add(face);
        }
    }

    @Override
    protected void printAfter() {
        System.out.print(" ");
    }
}
