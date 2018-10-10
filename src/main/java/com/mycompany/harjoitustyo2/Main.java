/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.harjoitustyo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    
    public static void main(String[] args) throws Exception {
        
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        List<Kysymys> kysymykset = new ArrayList<>();
        
        Spark.get("*", (req, res) -> {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement("SELECT kysymysteksti FROM Kysymys");
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymykset);

            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/lisaa", (req, res) -> {
            Connection conn = getConnection();
            String kurssi = req.queryParams("kurssi");
            String aihe = req.queryParams("aihe");
            String teksti = req.queryParams("teksti");
            kysymykset.add(new Kysymys(kurssi, aihe, teksti));
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi, aihe, teksti) VALUES (?, ?, ?)");
            res.redirect("/");
            
            return "";
        });

        Spark.get("*", (req, res) -> {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement("SELECT kysymysteksti FROM Kysymys");
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymykset);

            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
 
    }
    
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:tk.db");
    }
}
