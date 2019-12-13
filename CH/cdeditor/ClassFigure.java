package CH.cdeditor;

import CH.ifa.draw.figure.CompositeFigure;
import CH.ifa.draw.figure.PolyLineFigure;
import CH.ifa.draw.figure.RectangleFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.BoxHandleKit;
import CH.ifa.draw.handle.ConnectionHandle;
import CH.ifa.draw.locator.RelativeLocator;
import CH.ifa.draw.samples.pert.PertDependency;

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

        LineConnection connection = new LineConnection();
        connection.setAttribute("ArrowMode", PolyLineFigure.ARROW_TIP_END);

        handles.addElement(new ConnectionHandle(this, RelativeLocator.east(),
                connection)
        );
        handles.addElement(new ConnectionHandle(this, RelativeLocator.west(),
                connection)
        );
        handles.addElement(new ConnectionHandle(this, RelativeLocator.north(),
                connection)
        );
        handles.addElement(new ConnectionHandle(this, RelativeLocator.south(),
                connection)
        );

        return handles;
    }
}
