package behaviorpattern.composite;

import java.util.List;

public class FaceContentContent extends HandSomeComposite{
    public FaceContentContent(List<FaceContent> faceContents) {
        for (FaceContent faceContent : faceContents){
            this.add(faceContent);
        }
    }

    @Override
    protected void printAfter() {
        System.out.print(".");
    }
}
