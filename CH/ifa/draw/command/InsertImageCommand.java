/*
 * @(#)InsertImageCommand.java 5.1
 *
 */

package CH.ifa.draw.command;

import java.awt.Component;
import java.awt.Image;

import CH.ifa.draw.figure.ImageFigure;
import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.util.Iconkit;

/**
 * Command to insert a named image.
 */
public class InsertImageCommand extends Command {

    private DrawingView  fView;
    private String       fImage;

   /**
    * Constructs an insert image command.
    * @param name the command name
    * @param image the pathname of the image
    * @param view the target view
    */
    public InsertImageCommand(String name, String image, DrawingView view) {
        super(name);
        fImage = image;
        fView = view;
    }

    @Override
	public void execute() {
        // ugly cast to component, but AWT wants an Component instead of an ImageObserver...
        Image image = Iconkit.instance().registerAndLoadImage((Component)fView, fImage);
        ImageFigure figure = new ImageFigure(image, fImage, fView.lastClick());
        fView.add(figure);
        fView.clearSelection();
        fView.addToSelection(figure);
        fView.checkDamage();
    }
}



