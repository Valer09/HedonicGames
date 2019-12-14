package Generators;

import Structures.Edge;
import org.jgrapht.Graph;
import org.jgrapht.io.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileGenerator {
    public static void graphFileGenerator(Graph g){
        ComponentNameProvider<Integer> vertexIdProvider = new ComponentNameProvider<Integer>()
        {
            public String getName(Integer ID)
            {
                return ID.toString();
            }
        };
        ComponentNameProvider<Integer> vertexLabelProvider = new ComponentNameProvider<Integer>()
        {
            public String getName(Integer ID)
            {
                return ID.toString();
            }
        };

        ComponentNameProvider<Edge> edgeLabelProvider = new ComponentNameProvider<Edge>()
        {
            @Override
            public String getName(Edge edge) {
                String s= String.valueOf(edge.getWeight());
                System.out.println(edge.getWeight());
                return s;
            }

        };

        ComponentAttributeProvider<Edge> edgeAttributeProvider = new ComponentAttributeProvider<Edge>()
        {
            @Override
            public Map<String, Attribute> getComponentAttributes(Edge edge) {
                Map<String, Attribute> temp = new HashMap<>();
                temp.put(edge.toString(), DefaultAttribute.createAttribute(String.valueOf(edge.getWeight())) );
                return null;
            }
        };

        GraphExporter<Integer, Edge> exporter = new DOTExporter<Integer, Edge>(vertexIdProvider,vertexLabelProvider,edgeLabelProvider);

        try {

            int i=0;
            File out = new File("output/graphs/g"+i+".gv");
            while (out.exists()){
                out = new File("output/graphs/g"+i+".gv");
                i++;
            }

            exporter.exportGraph(g,out);


        } catch (ExportException e) {
            e.printStackTrace();
        }
    }

}


