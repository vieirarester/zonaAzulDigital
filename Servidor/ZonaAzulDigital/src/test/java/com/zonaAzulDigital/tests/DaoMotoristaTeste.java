/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zonaAzulDigital.tests;


import com.zonaAzulDigital.DAO.DaoMotoristaBD;
import com.zonaAzulDigital.Excecao.DaoException;
import com.zonaAzulDigital.Excecao.LoginException;
import com.zonaAzulDigital.Excecao.MotoristaException;
import com.zonaAzulDigital.entidades.Motorista;
import com.zonaAzulDigital.interfaces.DAOMotorista;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Carlos Eduardo
 */
public class DaoMotoristaTeste {
    public DAOMotorista dmbd;
    
    public Motorista m, m1, m2, m3;
    
    
    @Rule
    public ExpectedException excecao = ExpectedException.none();
    
    @Before
    public void before(){
        dmbd = new DaoMotoristaBD();
        m1 = new Motorista(0, "Antonio", "04982857406", BigDecimal.ZERO, "antonio");
        m2 = new Motorista(0, "Joao", "04982857507", BigDecimal.ZERO, "joao");
        m3 = new Motorista(0, "Fabricio", "04982557407", BigDecimal.ZERO, "fabricio");
     
    }
    
    @Test
    public void testandoCadastroMostorista() throws DaoException{
        
        m = new Motorista(0, "Carlos Eduardo", "04982857407", BigDecimal.ZERO, "carlos");
        
        Motorista mc = dmbd.cadastrar(m);
        
         assertEquals("Carlos Eduardo",mc.getNome());
         
    }
    
    @Test
    public void  disparaExcecaoDeCadastro() throws DaoException{
        excecao.expect(DaoException.class);
        
         Motorista mc = new Motorista();
         dmbd.cadastrar(mc);
    }
    
    @Test
    public void testandoMotoristaAtualizado() throws DaoException{
       
       m = new Motorista(0, "Samuel", "11791558402", BigDecimal.ZERO, "samuel");
       dmbd.cadastrar(m);
       m.setNome("Tony");
       
       Motorista mc = dmbd.atualizar(m);
        
        assertEquals("Tony", mc.getNome());
    }
    
    @Test
    public void testeRecuperarPorId() throws DaoException{
       
       m = new Motorista(0, "Jonas", "10654901430", BigDecimal.ZERO, "jonas");
       dmbd.cadastrar(m);
       
        
        Motorista mc = dmbd.recuperarPorId(1);
        
        assertEquals("Jonas", mc.getNome());
    }
    
    @Test
    public void testeRecuperarPorCpf() throws DaoException{
        
        m = new Motorista(0, "Castanha", "04882857407", BigDecimal.ZERO, "carlos");
        dmbd.cadastrar(m);
        
        Motorista mc = dmbd.recuperar("04882857407");
        
        assertEquals("Castanha", mc.getNome());
    }
    
    @Test
    public void disparaExcecaoDeRecuperar()throws DaoException{
        excecao.expect(DaoException.class);
        excecao.expectMessage(MotoristaException.NAOENCONTRADO);
         dmbd.recuperar("04882");
        
    }
    
    @Test
    public void testeDeLogin() throws LoginException, DaoException{
        
        m = new Motorista(0, "Eduardo", "11792558402", BigDecimal.ZERO, "edu");
        
        Motorista motorista = dmbd.cadastrar(m);
        
        Motorista motorista1 = dmbd.login(motorista.getCpf(), motorista.getSenha());
        
        assertEquals("Eduardo", motorista1.getNome());
    }
    
    
    @Test
    public void disparaExcecaoDeLoginCpfErrado() throws  DaoException, LoginException{
        excecao.expect(LoginException.class);
        excecao.expectMessage(LoginException.FALHOU);
        m = new Motorista(0, "Evandro", "11793558402", BigDecimal.ZERO, "eva");
        
        Motorista motorista = dmbd.cadastrar(m);
        
        dmbd.login("12312312312", motorista.getSenha());
    }
    
    @Test
    public void disparaExcecaoDeLoginSenhaErrado() throws DaoException, LoginException{
        excecao.expect(LoginException.class);
        excecao.expectMessage(LoginException.FALHOU);
        
        m = new Motorista(0, "Izaquias", "11793658402", BigDecimal.ZERO, "eva");
        
        Motorista motorista = dmbd.cadastrar(m);
        
        dmbd.login(motorista.getCpf(), "asd");
    }
    
    @Test
    public void testeDeListarTodos() throws DaoException{
        
        dmbd.cadastrar(m1);
        dmbd.cadastrar(m2);
        dmbd.cadastrar(m3);
        
        List<Motorista> lm =  dmbd.listarTudo();
        
        assertEquals("Antonio", lm.get(7).getNome());
        assertEquals("Joao", lm.get(8).getNome());
        assertEquals("Fabricio", lm.get(9).getNome());
        
    }
}