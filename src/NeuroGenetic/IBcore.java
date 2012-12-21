package NeuroGenetic;

/**
 *
 * @author TrayNo
 */

import java.sql.*;

public class IBcore {
    
    private Connection connect = null;
    private Statement statement = null;
    
    public boolean createConnection(String path) throws ClassNotFoundException{
        Class.forName("interbase.interclient.Driver");
        //System.out.println(path);
        try{
            connect = DriverManager.getConnection("jdbc:interbase://127.0.0.1:3050/"+path,"SYSDBA","masterkey");
            statement = connect.createStatement();
        }
        catch(Exception se){}
        return connect!=null?true:false;
    }
    
    public void closeConnection() throws SQLException {
        if (connect!=null) {
            connect.close();
        }
    }
    
    public ResultSet executeSQL(String sql){
        ResultSet set=null;
        try{
            set = statement.executeQuery(sql);
        }
        catch(Exception se){}
        return set!=null?set:null;
    }
  
}
