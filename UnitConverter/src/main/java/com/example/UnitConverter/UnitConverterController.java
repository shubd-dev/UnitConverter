package com.example.UnitConverter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UnitConverterController {

    @PostMapping("/convert")
    public ResponseEntity<Map<String, Double>> convert(@RequestBody ConversionRequest req){
        double result;
       // System.out.println("Received: " + req.getType() + ", " + req.getValue() + ", " + req.getFrom() + ", " + req.getTo());
        try{
            result = performConversion(req);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        Map<String, Double> response = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.###");
        response.put("result", Double.parseDouble(df.format(result)));
        return ResponseEntity.ok(response);
    }

    private double performConversion(ConversionRequest req) {
        String type = req.getType().toLowerCase();
        double value = req.getValue();
        String from = req.getFrom().toLowerCase();
        String to = req.getTo().toLowerCase();

        switch(type){
            case "length" -> {
                return convertLength(value, to , from);
            }
            case "weight" -> {
                return convertWeight(value, to, from);
            }
            case "temperature" -> {
                return convertTemperature(value, to, from);
            }
            default -> throw new IllegalArgumentException("Invalid type");
        }

    }

    private double convertTemperature(double value, String to, String from) {
        double celsius;
        switch(from){
            case "celsius" -> celsius = value;
            case "fahrenheit" -> celsius = (value-32)* 5/9 ;
            case "kelvin" -> celsius = value - 273;
            default -> throw new IllegalArgumentException();
        }

        switch(to){
            case "celsius" -> {
                return celsius;
            }
            case "fahrenheit" -> {
                return celsius * 9/5 + 32;
            }
            case "kelvin" -> {
                return celsius + 273;
            }
            default -> throw new IllegalArgumentException();

        }
    }

    private double convertLength(double value, String to, String from) {
        Map<String, Double> factors = Map.of(
                "millimeter", 0.001,
                "centimeter", 0.01,
                "meter", 1.0,
                "kilometer", 1000.0,
                "inch", 0.0254,
                "foot", 0.3048,
                "yard", 0.9144,
                "mile", 1609.344
        );
        if (!factors.containsKey(from) || !factors.containsKey(to)) throw new IllegalArgumentException();
        double meters = value * factors.get(from);
        return meters / factors.get(to);
    }

    private double convertWeight(double value, String to, String from) {
        Map<String, Double> factors = Map.of(
                "milligram", 0.000001,
                "gram", 0.001,
                "kilogram", 1.0,
                "ounce", 0.0283495,
                "pound", 0.453592
        );
        if (!factors.containsKey(from) || !factors.containsKey(to)) throw new IllegalArgumentException();
        double kg = value * factors.get(from);
        return kg / factors.get(to);
    }
}
