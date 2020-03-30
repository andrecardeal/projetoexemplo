package ferramentas;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author acsantana
 */
public class PreencherJtableGenerico {

    
    public void FormatarJtable(JTable tabela, int valores[]) {
            for (int x = 0; x < valores.length; x++) {
                tabela.getColumnModel().getColumn(x).setPreferredWidth(valores[x]);
            }
    }
    
    

    public void PreencherJtableGenerico(JTable tabela, String campos[], ResultSet resultSet) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        try {
            while (resultSet.next()) {
                int len = campos.length;
                Object[] row = new Object[len];
                for (int i = 0; i < len; i++) {
                    row[i] = resultSet.getString(campos[i]);
                }
                modelo.addRow(row);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar no JTable " + erro);
        }
    }

    public void limparJtable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setNumRows(0);   
    }

}

