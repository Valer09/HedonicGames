package App;

import JsonOrg.JSONArray;
import JsonOrg.JSONObject;
import Structures.Record;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ResultCounter_two {

    public static void main(String [] args){
        int core=0; int not_core=0;
        int k=0;
        int z=0;
        HashMap <Integer, List<Record>> table = new HashMap<Integer, List<Record> >();
        ArrayList<Record> ar= new ArrayList<>();

        System.out.println("Inserisci l'indice del grafo di partenza di agenti: ");
        Scanner scan = new Scanner(System.in);
        z = scan.nextInt();
        File tmpDir = new File("output/executions/json/g"+z+".json");
        while (tmpDir.exists()){
            ar= new ArrayList<>();
            String content="";
            try {
                content = new String ( Files.readAllBytes(Paths.get("output/executions/json/g"+z+".json")) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Reader reader = new StringReader(content);

            //datas are into an a cell of an array which lenght is 1
            JSONArray test = new JSONArray(content);

            //into array there are many objects
            ArrayList<JSONObject> instances = new ArrayList<JSONObject>();
            ArrayList <Boolean> cores= new ArrayList<Boolean>();


            for (int i=0 ; i< test.length(); i++){
                instances.add((JSONObject) test.get(i));
            }

            for (int i=0; i<instances.size()-1; i++){
                JSONObject instance= instances.get(i);
                k=instance.getInt("Relazioni :");
                Record r= new Record(
                        instance.getInt("Relazioni :"),
                        instance.getInt("Q : "),
                        instance.getDouble("Time : "),
                        instance.getBoolean("Modalità dinamica :"),
                        instance.getBoolean("CORE :")
                );
                ar.add(r);
                cores.add(instance.getBoolean("CORE :"));
            }
            table.put(k,ar);
            z++;
            tmpDir = new File("output/executions/json/g"+z+".json");
        }
        System.out.println(table.toString());

        /*for (Map.Entry<Integer, List<Record>> entry : table.entrySet()){
            System.out.println("---- K: "+entry.getKey()+" ----"+"\n");
            System.out.println("---- K: "+entry.getKey()+" ----"+"\n");
        }*/


    }
}

