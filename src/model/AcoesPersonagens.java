/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.entidades.Personagem;

/**
 *
 * @author Michael
 */
public interface AcoesPersonagens {

    /**
     * Recebe o alvo do ataque
     *
     * @param alvo
     */
    public void atacar(Personagem alvo);

    /**
     * Recebe o atacante para saber a força que o heroi tem que suportar
     *
     * @param alvo
     */
    public void defender(Personagem alvo);

    /**
     * Recebe o alvo da magia pois pode ser uma de buffer ou de ataque
     *
     * @param alvo
     */
    public void usarMagia(Personagem alvo);

//não coloquei o metodo de mover pois vai ser uma classe abstrata que eu estou desenvolvendo para o personagem extender
}
