
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
        
        Spark.post("/vastaukset/:id", (req, res) -> {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (?, ?, ?)");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));
            stmt.setString(2, req.queryParams("vastausteksti"));
            if (req.queryParams("oikein") != null) {
                stmt.setBoolean(3, true);
            } else {
                stmt.setBoolean(3, false);
            }
            

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
        
        Spark.post("/poistava/:id", (req, res) -> {
            Connection conn = getConnection(); 
            
            PreparedStatement stmt
                    = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));
            
            stmt.executeUpdate();
            
            conn.close();
            
            res.redirect("/");
            return "";
        });
        
        Spark.post("/kyssari/:id", (req, res) -> {
            List<Vastaus> vastaukset = new ArrayList<>();
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
            stmt.setInt(1, Integer.parseInt(req.params(":id")));
            ResultSet tulos = stmt.executeQuery();
            
            while (tulos.next()) {
                vastaukset.add(new Vastaus(tulos.getInt("id"), tulos.getInt("kysymys_id"), tulos.getString("vastausteksti"), tulos.getBoolean("oikein")));
            }
            

            List<Kysymys> kysymykset = new ArrayList<>();

            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
            stmt2.setInt(1, Integer.parseInt(req.params(":id")));
            ResultSet tulos2 = stmt2.executeQuery();
            
            while (tulos2.next()) {
                kysymykset.add(new Kysymys(tulos2.getInt("id"), tulos2.getString("kurssi"), tulos2.getString("aihe"), tulos2.getString("kysymysteksti")));
            }
            
            conn.close();
            HashMap map = new HashMap<>();
            map.put("vastaukset", vastaukset);
            map.put("kysymykset", kysymykset);
            
            return new ModelAndView(map, "index2");
        }, new ThymeleafTemplateEngine());
 
    }
    
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:db/tk.db");
    }
}
