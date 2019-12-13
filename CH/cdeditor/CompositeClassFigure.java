/*
 * @(#)PertFigure.java 5.1
 *
 */

package CH.cdeditor;

import CH.ifa.draw.figure.CompositeFigure;
import CH.ifa.draw.figure.NumberTextFigure;
import CH.ifa.draw.figure.PolyLineFigure;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.BoxHandleKit;
import CH.ifa.draw.handle.ConnectionHandle;
import CH.ifa.draw.handle.NullHandle;
import CH.ifa.draw.locator.RelativeLocator;
import CH.ifa.draw.samples.pert.PertDependency;
import CH.ifa.draw.storable.Storable;
import CH.ifa.draw.storable.StorableInput;
import CH.ifa.draw.storable.StorableOutput;

import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;


public class CompositeClassFigure extends CompositeFigure {
    private static final int BORDER = 3;
    private Rectangle fDisplayBox;

    /*
     * Serialization support.
     */
    private static final long serialVersionUID = -7877776240236946511L;
    @SuppressWarnings("unused")
	private int pertFigureSerializedDataVersion = 1;

    public CompositeClassFigure() {
        initialize();
    }




    private int asInt(int figureIndex) {
        NumberTextFigure t = (NumberTextFigure)figureAt(figureIndex);
        return t.getValue();
    }


    private void setInt(int figureIndex, int value) {
        NumberTextFigure t = (NumberTextFigure)figureAt(figureIndex);
        t.setValue(value);
    }

    protected void basicMoveBy(int x, int y) {
	    fDisplayBox.translate(x, y);
	    super.basicMoveBy(x, y);
	}

    public Rectangle displayBox() {
        return new Rectangle(
            fDisplayBox.x,
            fDisplayBox.y,
            fDisplayBox.width,
            fDisplayBox.height);
    }

    public void basicDisplayBox(Point origin, Point corner) {
        fDisplayBox = new Rectangle(origin);
        fDisplayBox.add(corner);
        layout();
    }

    private void drawBorder(Graphics g) {
        super.draw(g);

        Rectangle r = displayBox();

        Figure f = figureAt(0);
        Rectangle rf = f.displayBox();
        g.setColor(Color.black);
        g.drawLine(r.x, r.y+rf.height+2, r.x+r.width, r.y + rf.height+2);
        g.setColor(Color.black);
        g.drawLine(r.x, r.y+rf.height+3, r.x+r.width, r.y + rf.height+3);

        g.setColor(Color.black);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(Color.black);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x , r.y + r.height, r.x + r.width, r.y + r.height);
    }

    public void draw(Graphics g) {
        drawBorder(g);
        super.draw(g);
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

    private void initialize() {
        fDisplayBox = new Rectangle(0, 0, 0, 0);

        Font f = new Font("Helvetica", Font.PLAIN, 12);
        Font fb = new Font("Helvetica", Font.BOLD, 12);

        TextFigure name = new TextFigure();
        name.setFont(fb);
        name.setText("Class Name");
        name.setAttribute("TextColor",Color.black);
        add(name);

        TextFigure method = new TextFigure();
        method.setFont(fb);
        method.setText("Method");
        method.setAttribute("TextColor",Color.black);
        add(method);


    }

    private void layout() {
	    Point partOrigin = new Point(fDisplayBox.x, fDisplayBox.y);
	    partOrigin.translate(BORDER, BORDER);
	    Dimension extent = new Dimension(0, 0);

        Enumeration<Figure> k = figures();
        while (k.hasMoreElements()) {
            Figure f = k.nextElement();

		    Dimension partExtent = f.size();
		    Point corner = new Point(
		                        partOrigin.x+partExtent.width,
		                        partOrigin.y+partExtent.height);
    		f.basicDisplayBox(partOrigin, corner);

		    extent.width = Math.max(extent.width, partExtent.width);
		    extent.height += partExtent.height;
		    partOrigin.y += partExtent.height;
        }
	    fDisplayBox.width = extent.width + 2*BORDER;
	    fDisplayBox.height = extent.height + 2*BORDER;
    }

    private boolean needsLayout() {
	    Dimension extent = new Dimension(0, 0);

        Enumeration<Figure> k = figures();
        while (k.hasMoreElements()) {
            Figure f = k.nextElement();
		    extent.width = Math.max(extent.width, f.size().width);
        }
        int newExtent = extent.width + 2*BORDER;
        return newExtent != fDisplayBox.width;
    }

    public void update(FigureChangeEvent e) {
        if (needsLayout()) {
            layout();
            changed();
        }
    }

    public void figureChanged(FigureChangeEvent e) {
        update(e);
    }


    public void figureRemoved(FigureChangeEvent e) {
        update(e);
    }

    
    public Insets connectionInsets() {
        Rectangle r = fDisplayBox;
        int cx = r.width/2;
        int cy = r.height/2;
        return new Insets(cy, cx, cy, cx);
    }
    
    //-- store / load ----------------------------------------------

    public void write(StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
    }

    public void writeTasks(StorableOutput dw, Vector<CompositeClassFigure> v) {
        dw.writeInt(v.size());
        Enumeration<CompositeClassFigure> i = v.elements();
        while (i.hasMoreElements())
            dw.writeStorable((Storable) i.nextElement());
    }

    public void read(StorableInput dr) throws IOException {
        super.read(dr);
        fDisplayBox = new Rectangle(
            dr.readInt(),
            dr.readInt(),
            dr.readInt(),
            dr.readInt());
        layout();
    }
}
