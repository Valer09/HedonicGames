package Testing;

import JsonOrg.JSONArray;
import JsonOrg.JSONObject;
import JsonOrg.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ResultCounter {
    public static void main(String [] args){
        int core=0; int not_core=0;
        int k=0;

        System.out.println("Inserisci l'indice del grafo di partenza di agenti: ");
        Scanner scan = new Scanner(System.in);
        k = scan.nextInt();
        File tmpDir = new File("output/executions/json/g0.json");
        while (tmpDir.exists()){
            String content="";
            try {
                content = new String ( Files.readAllBytes(Paths.get("output/executions/json/g"+k+".json")) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Reader reader = new StringReader(content);

            JSONArray test = new JSONArray(content);
            //JSONObject obj ;

            ArrayList <JSONObject> instances = new ArrayList<JSONObject>();
            ArrayList <Boolean> cores= new ArrayList<Boolean>();

            for (int i=0 ; i< test.length(); i++){
                instances.add((JSONObject) test.get(i));
            }

            for (int i=0; i<instances.size()-1; i++){
                JSONObject instance= instances.get(i);
                cores.add(instance.getBoolean("CORE :"));
            }


            for (Boolean c : cores){
                if (c==true){
                    core++;
                    System.out.println("CORE= "+core);
                }
                else{
                    not_core++;
                    System.out.println("NOT_CORE= "+not_core);
                }
            }

            k++;
            tmpDir = new File("output/executions/json/g"+k+".json");
        }
    }
}
