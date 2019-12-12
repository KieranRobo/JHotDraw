package CH.cdeditor;

import CH.ifa.draw.figure.RectangleFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.BoxHandleKit;

import java.awt.*;
import java.util.Vector;


public class ClassFigure extends RectangleFigure {

    public ClassFigure() {
        super();
        initilize();
    }

    private void initilize() {
        setAttribute("FillColor", Color.white);

    }

    @Override
    public Vector<Handle> handles() {
        Vector<Handle> handles = new Vector<>();
        BoxHandleKit.addCornerHandles(this, handles);
        return handles;
    }
}
