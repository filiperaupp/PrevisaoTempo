package com.example.filip.previsaotempo;

import java.io.Serializable;

public class Previsao implements Serializable {

    private String data;
    private String descricao;
    private String min;
    private String max;

    public Previsao(String data, String descricao, String min, String max) {
        this.data = data;
        this.descricao = descricao;
        this.min = min;
        this.max = max;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }
}
