package Generators;
import Structures.Vertex;
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import Structures.Edge;
import org.jgrapht.io.*;

/**
 * For drawing of graphs
 */
public class GraphDrawer extends JApplet{

    private static final long serialVersionUID = 2202072534703043194L;
    private static final Dimension DEFAULT_SIZE = new Dimension(1080, 720);
    private JGraphXAdapter <Integer, DefaultWeightedEdge> jgxAdapter;
    private static Graph<Integer, Edge> generatedgraph;


    /**
     * For using this tool from other
     * @param g
     */
    public static void setGraph(Graph<Integer, Edge> g){
        generatedgraph=g;
    }

    /**
     * Starting point
     * @param //args command line arguments
     */
    public static void main(String[] args)
    {
        GraphDrawer applet = new GraphDrawer();
        applet.init();
        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("GraphDrawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        applet.start();
        applet.paint(frame.getGraphics());





    }

    @Override
    public void init()
    {

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter(generatedgraph);
        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 300;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...


    }
}


