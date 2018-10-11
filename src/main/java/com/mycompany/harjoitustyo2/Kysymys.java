
package com.mycompany.harjoitustyo2;


public class Kysymys {
    private Integer id;
    private String kurssi;
    private String aihe;
    private String kysymysteksti;
    
    public Kysymys(Integer id, String kurssi, String aihe, String kysymysteksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = kysymysteksti;
    }

    public int getId() {
        return id;
    }

    public String getKurssi() {
        return kurssi;
    }

    public String getAihe() {
        return aihe;
    }

    public String getKysymysteksti() {
        return kysymysteksti;
    }
    
    public String toString() {
        return "Kurssi: " + this.kurssi + " Aihe: " + this.aihe + " Kysymys: " + this.kysymysteksti;
    }
    
}
