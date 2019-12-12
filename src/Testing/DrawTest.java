package Testing;
import Generators.WeightGenerator;
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.demo.JGraphXAdapterDemo;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Set;
import Structures.Edge;

import Generators.RandomDirectedGenerator;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs. Applet based on
 * JGraphAdapterDemo.
 *
 */
public class DrawTest extends JApplet{

        private static final long serialVersionUID = 2202072534703043194L;

        private static final Dimension DEFAULT_SIZE = new Dimension(1080, 720);

        private JGraphXAdapter <String, DefaultWeightedEdge> jgxAdapter;

        /**
         * An alternative starting point for this demo, to also allow running this applet as an
         * application.
         *
         * @param args command line arguments
         */
        public static void main(String[] args)
        {
            DrawTest applet = new DrawTest();
            applet.init();
            JFrame frame = new JFrame();
            frame.getContentPane().add(applet);
            frame.setTitle("GraphDrawer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }

        @Override
        public void init()
        {
            RandomDirectedGenerator rdmgn= new RandomDirectedGenerator(9,10);
            Graph <Integer, Edge> generatedgraph = rdmgn.generateGraph();

            WeightGenerator wg= new WeightGenerator(generatedgraph);
            wg.generateWeights();

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

