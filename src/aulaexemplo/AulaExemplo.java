
package aulaexemplo;

import javax.swing.JOptionPane;

/**
 *
 * @author acsantana
 */
public class AulaExemplo {

    private int valor = 0;

    
    
    public int getValor() {

        return valor ;
    }

    public void setValor(int valor) {
 
        this.valor = valor * 10;
    }
    
    
    
    
    public static void main(String[] args) {
         AulaExemplo aula = new AulaExemplo();
        aula.setValor(50);
        JOptionPane.showMessageDialog(null, aula.getValor());
     
    }
    
}
