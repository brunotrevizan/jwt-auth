package br.bookstore.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CourseUserMap {
    public static void main(String[] args) {
        // Caminho do arquivo JSON
        String filePath = "/Users/brunosouza/Result_54.json";

        // Criar o mapa para armazenar courseid e userids
        Map<Integer, List<String>> courseUserMap = new HashMap<>();

        try {
            // Criar o objeto ObjectMapper para leitura do JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Iterar sobre os elementos do JSON
            for (JsonNode node : rootNode) {
                int courseId = node.get("courseid").asInt(); // Convertendo courseid para Integer
                String userIdsStr = node.get("userids").asText(); // Obtendo a string de userids
                List<String> userIdsList = Arrays.asList(userIdsStr.split(",")); // Separando por vírgula

                // Adicionando ao mapa
                courseUserMap.put(courseId, userIdsList);
//                Arrays.asList(input.split(",")
                System.out.println("courseUserMap.put(" + courseId + ", Arrays.asList(\"" + String.join(", ", userIdsList) + "\".split(\",\")));");
            }


            // Exibir o mapa para verificação
//            for (Map.Entry<Integer, List<String>> entry : courseUserMap.entrySet()) {
//                System.out.println("Course ID: " + entry.getKey() + " -> User IDs: " + entry.getValue());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
