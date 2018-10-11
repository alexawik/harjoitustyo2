
package com.mycompany.harjoitustyo2;

public class Vastaus {
    private Integer id;
    private String vastausteksti;
    private Boolean oikein;
    
    public Vastaus(Integer id, String vastausteksti, Boolean oikein) {
        this.id = id;
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
}
