/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.sena.yugi_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USUARIO
 */
public class YuGi_APIHelper {
    public static JSONArray getRandomCards(int cantidad) throws Exception {
        URL url = new URL("https://db.ygoprodeck.com/api/v7/cardinfo.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray todasLasCartas = jsonResponse.getJSONArray("data");

        // Mezclar aleatoriamente
        List<JSONObject> listaCartas = new ArrayList<>();
        for (int i = 0; i < todasLasCartas.length(); i++) {
            listaCartas.add(todasLasCartas.getJSONObject(i));
        }
        Collections.shuffle(listaCartas);

        // Seleccionar las n cartas
        JSONArray seleccionadas = new JSONArray();
        for (int i = 0; i < cantidad && i < listaCartas.size(); i++) {
            seleccionadas.put(listaCartas.get(i));
        }

        return seleccionadas;
    }
}
