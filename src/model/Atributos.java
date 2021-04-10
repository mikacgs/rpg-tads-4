package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juliano
 */
@Entity
public class Atributos implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private int maxVida;
    private int PontosVida;
    private int forca;
    private int ataque;
    private int velocidade;
    private int defesa;

    public Atributos() {
    }

    public void initAtributos() {
        if (maxVida == 0) {
            maxVida = PontosVida;
        }
    }

    public Atributos(String nome, int PontosVida, int forca, int ataque, int velocidade, int defesa) {
        this.nome = nome;
        this.maxVida = PontosVida;
        this.PontosVida = PontosVida;
        this.forca = forca;
        this.ataque = ataque;
        this.velocidade = velocidade;
        this.defesa = defesa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontosVida() {
        return PontosVida;
    }

    public void setPontosVida(int PontosVida) {
        this.PontosVida = PontosVida;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getMaxVida() {
        return maxVida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
