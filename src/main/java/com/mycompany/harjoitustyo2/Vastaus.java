
package com.mycompany.harjoitustyo2;

public class Vastaus {
    private Integer id;
    private String vastausteksti;
    private Boolean oikein;
    private Integer kysymys_id;
    
    public Vastaus(Integer id, String vastausteksti, Boolean oikein, Integer kysymys_id) {
        this.id = id;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
        this.kysymys_id = kysymys_id;
    }

    public Integer getId() {
        return id;
    }

    public String getVastausteksti() {
        return vastausteksti;
    }

    public Boolean getOikein() {
        return oikein;
    }
}
