package Generators;

import Interfaces.Status;
import JsonOrg.JSONArray;
import JsonOrg.JSONObject;
import Structures.Edge;
import Structures.MFCoreStatus;
import org.jgrapht.Graph;
import org.jgrapht.io.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public static String generateJsonFromStatus(MFCoreStatus startingStatus, MFCoreStatus status, int i){
        JSONObject jsonString= new JSONObject();
        JSONArray genericArray= new JSONArray();
        JSONObject istanza = new JSONObject();
        jsonString.
                put("CORE :", status.isStable()).
                put("Agents", status.getAgents()).
                put("STARTING PARTITION: ", startingStatus.getCoalitionStructure()).
                put("STARTING COALITION: ", startingStatus.getCoalitions()).
                put("LAST PARTITION", status.getCoalitionStructure()).
                put("LAST COALITION", status.getCoalitions());




        return jsonString.toString();

    }

    public static File jsonFilegenerator(){
            int k=0;
            File tmpDir = new File("output/executions/json/g0.json");
            while (tmpDir.exists()){
                tmpDir = new File("output/executions/json/g"+k+".json");
                k++;
        }
            return tmpDir;
    }

    public static File txtFileGenerator(){
        int k=0;
        File tmpDir = new File("output/executions/text/g0.txt");
        while (tmpDir.exists()){
            tmpDir = new File("output/executions/text/g"+k+".txt");
            k++;
        }
        return tmpDir;

    }
}


