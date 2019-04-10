/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.utils;

import tn.esprit.iservices.ReclamationFacadeRemote;
import tn.esprit.iservices.ReponseReclamationFacadeRemote;

/**
 *
 * @author ksamih
 */
public class Proxy {
    
    String  jndiRsponseReclamation ="Soluc-ejb/ReponseReclamationFacade!tn.esprit.iservices.ReponseReclamationFacadeRemote";
    String jndiReclamation  ="Soluc-ejb/ReclamationFacade!tn.esprit.iservices.ReclamationFacadeRemote";
    public Proxy(){
    }
    
    public ReclamationFacadeRemote getReclamationProxy()
    {
        return (ReclamationFacadeRemote) ContextCache
                .getInstance()
                .getProxy(jndiReclamation);
    }
    
   
    public ReponseReclamationFacadeRemote getResponseReclamationProxy()
    {
        return (ReponseReclamationFacadeRemote) ContextCache
                .getInstance()
                .getProxy(jndiRsponseReclamation);
    }
}
