
package com.mycompany.harjoitustyo2;

public class Vastaus {
    private Integer id;
    private Integer kysymys_id;
    private String vastausteksti;
    private Boolean oikein;

    
    public Vastaus(Integer id, Integer kysymys_id, String vastausteksti, Boolean oikein) {
        this.id = id;
        this.kysymys_id = kysymys_id;        
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;

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
    
    public String toString() {
        return vastausteksti;
    }
}
