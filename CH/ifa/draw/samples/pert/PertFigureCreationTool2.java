/*
 * @(#)PertFigureCreationTool.java 5.1
 *
 */

package CH.ifa.draw.samples.pert;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.tool.CreationTool;

/**
 * A more efficient version of the generic Pert creation
 * tool that is not based on cloning.
 */

public  class PertFigureCreationTool2 extends CreationTool {
	
	Figure prototype;

    public PertFigureCreationTool2(DrawingView view, Figure prototype) {
        super(view);
    }

	protected Figure createFigure() {
        return (Figure) prototype.clone();
    }

}
