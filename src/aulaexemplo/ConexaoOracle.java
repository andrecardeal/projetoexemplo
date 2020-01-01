package aulaexemplo;

//import java.sql.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class ConexaoOracle { // classe principal

    public static Connection ConexaoOracle;
    public static Statement statement;
    public static ResultSet resultset;
    public ResultSetMetaData metaData;
    public int retorno = 0;
    
     private final String driver = "org.postgresql.Driver";

    public  ConexaoOracle() { // metodo construtor
              conecta();
    }

    public static Connection conecta() {
        if (ConexaoOracle != null) {
            return ConexaoOracle;
        } else {
            try {
                Class.forName("org.postgresql.Driver");
            ConexaoOracle = DriverManager.getConnection("jdbc:postgresql://localhost:5432/AulaJava", "postgres", "acs1707$"); 
            
            System.out.println("Conectado");
                // JOptionPane.showMessageDialog(null, "Conectado com sucesso");
                return ConexaoOracle;

            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Driver não localizado: " + ex);
                ex.printStackTrace();
                return null;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro na conexão com a fonte"
                        + " de dados: ");
                ex.printStackTrace();
                return null;
            }
        }
    }

    public void desconecta() {
        boolean result = true;
        try {
            ConexaoOracle.close();
            // JOptionPane.showMessageDialog(null, "banco fechado");
        } catch (SQLException fecha) {
            JOptionPane.showMessageDialog(null, "Não foi possivel "
                    + "fechar o banco de dados: " + "\n" + fecha);
            result = false;
        }
    }

    public void incluirSQL(String sql) {
        try {
            statement = ConexaoOracle.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY'");
            resultset = statement.executeQuery(sql);
            retorno = 1;
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 00001) {
                JOptionPane.showMessageDialog(null, "O registro não pôde ser "
                        + "incluido pois já está cadastrado ");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível "
                        + "executar o comando sql ," + sqlex
                        + ", o sql passado foi " + sql);
            }
            retorno = 0;
        }
    }

    public void executeSQL(String sql) {
        try {
            statement = ConexaoOracle.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          //  statement.execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY'");
            resultset = statement.executeQuery(sql);
            retorno = 1;
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Não foi possível localizar o registro \n"
                    + sqlex);
        }
//        try {
//            metaData = resultset.getMetaData();
//        } catch (SQLException erro) {
//        }
    }

    public void executeSQL(String sql, int sqlx) {
        try {
            statement = ConexaoOracle.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.execute("ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YYYY'");
            resultset = statement.executeQuery(sql);
            retorno = 1;
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Não foi possível localizar o registro \n"
                    + sqlex);
        }
        try {
            metaData = resultset.getMetaData();
        } catch (SQLException erro) {
        }
    }

    public void deleteSQL(String sql) {
        try {
            statement = ConexaoOracle.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            retorno = 0;
            retorno = statement.executeUpdate(sql);
            if (retorno == 1) {
                JOptionPane.showMessageDialog(null, "Exclusao realizada com sucesso");
            }
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 2292) {
                JOptionPane.showMessageDialog(null, "O registro não pôde ser "
                        + "excluído porque ele está sendo utilizado em outro cadastro/movimento");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível "
                        + "executar o comando sql de exclusão," + sqlex + ", o sql passado foi "
                        + sql);
            }
            retorno = 0;
        }
    }

    public void atualizarSQL(String sql) {
        try {
            statement = ConexaoOracle.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            retorno = 0;
            retorno = statement.executeUpdate(sql);
            if (retorno == 1) {
                JOptionPane.showMessageDialog(null, "Atualização realizada com sucesso");
            }
        } catch (SQLException sqlex) {
            if (sqlex.getErrorCode() == 2292) {
                JOptionPane.showMessageDialog(null, "O registro não pôde ser "
                        + "atualizado porque ele está sendo utilizado em outro cadastro/movimento");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível "
                        + "executar o comando sql de exclusão," + sqlex + ", o sql passado foi "
                        + sql);
            }
            retorno = 0;
        }
    }

    public int ultimasequencia(String tabela, String atributo) {
        executeSQL("SELECT COALESCE(MAX(" + atributo + "),0) + 1 AS ULTIMO FROM "
                + tabela);
        int retorno = 0;
        try {
            resultset.first();
            retorno = resultset.getInt("ULTIMO");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Registro nao encontrado");
        }
        return retorno;
    }

    public String retornadescricao(String tabela, String retorno, String condicao,
            String comparacao) {
        executeSQL("SELECT " + retorno + " AS DESCRICAO FROM " + tabela + " WHERE "
                + condicao + " = " + comparacao);
        String ret = "";
        try {
            resultset.first();
            ret = resultset.getString("DESCRICAO");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Registro nao encontrado");
        }
        return ret;
    }

    public static void main(String[] args) {
        ConexaoOracle conexao = new ConexaoOracle();
        //   ModeloCadCidade modelocidade = new ModeloCadCidade();

        String sql = "SELECT * FROM CADCIDADE WHERE idcidade = 1";
        
        conexao.executeSQL(sql);

        try {
            conexao.resultset.first();
            String teste = resultset.getString("dscidade");
            JOptionPane.showMessageDialog(null, resultset.getString("dscidade"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
//        
        
        
    }
}
