
package aulaexemplo;

import javax.swing.JOptionPane;

/**
 *
 * @author acsantana
 */
public class ChamaAula {
   


    
    public static void main(String[] args){
        AulaExemplo aula = new AulaExemplo();
        aula.setValor(50);
        JOptionPane.showMessageDialog(null, aula.getValor());
    }
}
