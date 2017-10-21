package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Route;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class RouteService {
    public static Route getRouteFromFile(String filename) throws IOException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        Scanner sc = new Scanner(is);
        sc.useDelimiter("\\Z");
        String data = sc.next();
        System.out.println(data);

        Route result = null;
        ObjectMapper objectMapper = new ObjectMapper();

        result = objectMapper.readValue(data, Route.class);
        return result;
    }
}
