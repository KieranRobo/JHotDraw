package CH.cdeditor;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.tool.ConnectedTextTool;
import CH.ifa.draw.tool.ConnectionTool;
import CH.ifa.draw.tool.CreationTool;

import javax.swing.*;

public class CDEditorApp extends DrawApplication {

    private static final long serialVersionUID = -97233389025989950L;

    public static void main(String[] args) {
        DrawApplication window = new CDEditorApp();
        window.open();
    }

    CDEditorApp() {
        super("CDEditor");
    }

    //-- main -----------------------------------------------------------

    @Override
    protected void createTools(JPanel palette) {
        super.createTools(palette);

        Tool tool = new CreationTool(view(), new ClassFigure());
        palette.add(createToolButton(IMAGES+"RECT", "Class Tool", tool));

        tool = new CreationTool(view(), new CompositeClassFigure());
        palette.add(createToolButton(IMAGES+"PERT", "Composite Class Tool", tool));

        tool = new ConnectionTool(view(), new LineConnection());
        palette.add(createToolButton(IMAGES + "CONN", "Connection Tool", tool));

        tool = new ConnectedTextTool(view(), new TextFigure());
        palette.add(createToolButton(IMAGES + "ATEXT", "Connected Text Tool",
                tool));

    }
}
