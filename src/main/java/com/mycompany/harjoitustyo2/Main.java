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
        
        Spark.get("*", (req, res) -> {
            List<Kysymys> kysymykset = new ArrayList<>();
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
            ResultSet tulos = stmt.executeQuery();
            
            while (tulos.next()) {
                kysymykset.add(new Kysymys(tulos.getInt("id"), tulos.getString("kurssi"), tulos.getString("aihe"), tulos.getString("kysymysteksti")));
            }
            
            conn.close();
            
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymykset);
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kysymykset", (req, res) -> {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES (?, ?, ?)");
            stmt.setString(1, req.queryParams("kurssi"));
            stmt.setString(2, req.queryParams("aihe"));
            stmt.setString(3, req.queryParams("kysymysteksti"));
            
            stmt.executeUpdate();
            conn.close();
            res.redirect("/");
            
            return "";
        });

        Spark.get("*", (req, res) -> {
            List<Kysymys> kysymykset = new ArrayList<>();
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");
            ResultSet tulos = stmt.executeQuery();
            
            while (tulos.next()) {
                kysymykset.add(new Kysymys(tulos.getInt("id"), tulos.getString("kurssi"), tulos.getString("aihe"), tulos.getString("kysymysteksti")));
            }
            
            conn.close();
            
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymykset);
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/vastaukset", (req, res) -> {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (vastausteksti, oikein) VALUES (?, 0)");
            stmt.setString(1, req.queryParams("kurssi"));
            

            stmt.executeUpdate();
            conn.close();
            res.redirect("/");
            
            return "";
        });
        
        Spark.post("/poista/:id", (req, res) -> {
            
            Connection conn = getConnection();
            
            PreparedStatement stmt
                    = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));

            stmt.executeUpdate();

            conn.close();

            res.redirect("/");
            return "";
        });
        
        
 
    }
    
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:db/tk.db");
    }
}
