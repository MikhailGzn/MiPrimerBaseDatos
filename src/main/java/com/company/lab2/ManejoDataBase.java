package com.company.lab2;
import java.io.IOException;
import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.company.lab2.objects.Persona;
import com.company.lab2.objects.Caja;
import com.company.lab2.objects.Objeto;
import com.company.lab2.objects.Cuadricula;

import java.util.ArrayList;
import java.util.List;


public class ManejoDataBase {    
    String url; //= "jdbc:postgresql://localhost:5432/";
    String dataBaseName;// = "arqueologia";
    String username;// = "postgres";
    String password;// = "0197";
    String dbUrl;// = url + databaseName;
    String sqlFilePath;// = "src/main/java/com/company/lab2/insertSQL/insert.sql";
    //String insertSQL = readSQLFile(sqlFilePath);

    public ManejoDataBase(String url, String dataBaseName,String username,String password,String sqlFilePath) throws SQLException{
        this.url = url;
        this.dataBaseName = dataBaseName;
        this.username = username;
        this.password = password;
        this.dbUrl = url + dataBaseName;
        this.sqlFilePath = sqlFilePath;                    
    }
    
    public boolean checkDatabaseExists() throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String checkDatabaseExistsQuery = "SELECT COUNT(*) FROM pg_catalog.pg_database WHERE datname = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkDatabaseExistsQuery)) {
                preparedStatement.setString(1, dataBaseName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }    
            return false;
        }
    }

    public void createDatabase() throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, username, password)){
            String createDatabaseSql = "CREATE DATABASE " + dataBaseName;
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createDatabaseSql);        
            }
        }
    }

    public void inicializarDataBase() throws SQLException, IOException{
        try (Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)) {                
            // Sentencias SQL para crear las tablas                
            String[] createTableStatements = {                    
                "CREATE TABLE IF NOT EXISTS Sitios ("
                    +"S_Cod VARCHAR(30) PRIMARY KEY,"
                    +"S_Localidad VARCHAR(50) NOT NULL);",
                "CREATE TABLE IF NOT EXISTS Cuadriculas ("
                    +"Cu_Cod VARCHAR(50) PRIMARY KEY NOT NULL,"
                    +"S_Cod_Dividido VARCHAR(50) NOT NULL,"
                    +"FOREIGN KEY (S_Cod_Dividido) REFERENCES Sitios (s_cod));",
                "CREATE TABLE IF NOT EXISTS Personas ("
                    +"P_dni INT8 PRIMARY KEY," 
                    +"P_Nombre VARCHAR(50) NOT NULL,"
                    +"P_Apellido VARCHAR(50) NOT NULL,"
                    +"P_Email VARCHAR(30) NOT NULL,"
                    +"P_Telefono VARCHAR(30) NOT NULL);",
                "CREATE TABLE IF NOT EXISTS Cajas ("
                    + "Ca_Cod VARCHAR(50) PRIMARY KEY, "
                    + "Ca_Fecha VARCHAR(50) NOT NULL, "
                    + "Ca_Lugar VARCHAR(50) NOT NULL);",
                "CREATE TABLE IF NOT EXISTS Objetos ("
                    + "O_Cod VARCHAR(50) PRIMARY KEY NOT NULL ,"
                    + "O_Nombre VARCHAR(50) NOT NULL ,"
                    + "O_TipoExtraccion VARCHAR(50) NOT NULL ,"
                    + "O_Alto INT NOT NULL ,"
                    + "O_Largo INT NOT NULL ,"
                    + "O_Espesor INT NOT NULL ,"
                    + "O_Peso INT NOT NULL ,"
                    + "O_Cantidad INT NOT NULL ,"
                    + "O_Fecharegistro VARCHAR(50) NOT NULL ,"
                    + "O_Descripcion VARCHAR(50),"
                    + "O_Origen VARCHAR(50),"
                    + "CU_Cod_Asocia VARCHAR(50),"
                    + "Ca_Cod_Contiene VARCHAR(50),"
                    + "P_Dni_Ingresa INT,"
                    + "O_ES VARCHAR(1)," // L o C
                    + "FOREIGN KEY (CU_Cod_Asocia) REFERENCES Cuadriculas (Cu_Cod),"
                    + "FOREIGN KEY (Ca_Cod_Contiene) REFERENCES Cajas (Ca_Cod) ON DELETE CASCADE,"
                    + "FOREIGN KEY (P_Dni_Ingresa) REFERENCES Personas (P_Dni) ON DELETE CASCADE);",
                "CREATE TABLE IF NOT EXISTS Liticos ("
                    + "O_cod VARCHAR(50) NOT NULL ,"
                    + "L_Fechacreacion INT,"
                    + "FOREIGN KEY (O_cod) REFERENCES Objetos (O_Cod) ON DELETE CASCADE)",
                "CREATE TABLE IF NOT EXISTS Ceramicos ("
                + "O_cod VARCHAR(50) NOT NULL ,"
                + "C_Color Varchar(50) NOT NULL,"
                + "FOREIGN KEY (O_cod) REFERENCES Objetos (O_Cod) ON DELETE CASCADE)"
            };
            try (Statement statement = dbConnection.createStatement()) {
                    for (String sql : createTableStatements) {
                        statement.executeUpdate(sql);                    
                    }                    
            }
        }
    }
    private static String readSQLFile(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return new String(bytes);
    }
    // Para hacer una sola coneccion a la base de datos ejecutamos el archivo y agegamos los datos extras
    public void ejecutaArchivoSQL() throws IOException, SQLException{
        String sqlScript = readSQLFile(sqlFilePath);        
        try (Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)) {
            try (Statement statement = dbConnection.createStatement()) {
                statement.executeUpdate(sqlScript);
                statement.executeUpdate("INSERT INTO Personas VALUES"
                        + " (25544555,'Rodolphe','Rominov',"
                        + "'rrominovm@sciencedaily.com','7135986253');");
                System.out.println("Las tablas se crearon exitosamente.");                
                statement.executeUpdate("DELETE FROM personas "
                        + "WHERE p_nombre='Benji' and p_apellido='Colchett';");                                                            
            }            
        }        
    }
    public List<Persona> getAllPersons() throws SQLException{
        List <Persona> personas = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM personas");
                while(resultSet.next()){
                    personas.add(new Persona(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));
                }
            }
        }
        return personas;
    }
 
    public List<Caja> getAllBoxes() throws SQLException{
        List <Caja> cajas = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM cajas");
                while(resultSet.next()){
                    cajas.add(new Caja(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3)));
                }
            }
        }
        return cajas;
    }
    public List<Objeto> getAllObjectsEnCaja(String codigo) throws SQLException{
        List <Objeto> objetos = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM objetos WHERE ca_cod_contiene = '" + codigo + "'");
                while(resultSet.next()){
                    objetos.add(new Objeto(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5),
                    resultSet.getInt(6),resultSet.getInt(7),resultSet.getInt(8),
                    resultSet.getString(9),resultSet.getString(10),resultSet.getString(11),
                    resultSet.getString(12),resultSet.getString(13),resultSet.getInt(14),
                    resultSet.getString(15).charAt(0 )));
                }
            }
        }
        return objetos;
    }
    // Solo tiene codigo y nombre
    public List<Objeto> getAllObjectsEntreFechas(String fechaIncial, String fechaFinal) throws SQLException{
        List <Objeto> objetos = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                String sql =" SELECT o_cod,o_nombre,o_fecharegistro\n" +
                "FROM objetos\n"+
                "WHERE to_date(o_fecharegistro, 'dd-mm-yyyy') BETWEEN "+
                "to_date('"+fechaIncial+"', 'dd-mm-yyyy') AND to_date('"+fechaFinal+"', 'dd-mm-yyyy');";
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    objetos.add(new Objeto(resultSet.getString(1), resultSet.getString(2)));
                }
            }
        }
        return objetos;
    }
    public List<Persona> getPersonasMasCantObjetos() throws SQLException{
        List<Persona> personas = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                String sql = "SELECT p_nombre,p_apellido,COUNT(o_cod) AS cantidad_objetos\n" +
                             "FROM personas LEFT JOIN objetos ON p_dni = p_dni_ingresa\n"+
                             "GROUP BY p_dni\n"+
                             "ORDER BY p_apellido";
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    personas.add(new Persona(resultSet.getString(1), resultSet.getString(2),resultSet.getInt(3)));
                }
            }
        }
        return personas;
    }
    //No devuelve fecha
    public List<Caja> getCajasVacias() throws SQLException{
        List<Caja> cajas = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                String sql = "SELECT ca_cod,ca_lugar\n"+
                             "FROM cajas\n"+
                            "WHERE ca_cod NOT IN (SELECT ca_cod\n"+
                   			                     "FROM cajas,objetos\n"+
                 			                     "WHERE ca_cod=ca_cod_contiene\n"+
                                                "GROUP BY ca_cod);";                 
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    cajas.add(new Caja(resultSet.getString(1), resultSet.getString(2)));
                }
            }
        }
        return cajas;
    }

    public List<Caja> getCajasMasPeso() throws SQLException{
        List<Caja> cajas = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                String sql ="SELECT ca_cod,SUM(o_peso) AS peso_total\n"+
                            "FROM cajas LEFT JOIN objetos ON ca_cod=ca_cod_contiene\n"+
                            "GROUP BY ca_cod";              
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    cajas.add(new Caja(resultSet.getString(1), resultSet.getInt(2)));
                }
            }
        }
        return cajas;

    }
    public Persona getPersonaConDni(Integer dni) throws SQLException{
        Persona persona = null;
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM personas WHERE p_dni = '"+dni+"'");
                if(resultSet.next()){
                    persona = new Persona(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                }
            }
        }
        return persona;
    }

    public Caja getCajaConCod(String codigo) throws SQLException{
        Caja caja = null;
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM cajas WHERE ca_cod = '"+codigo+"'");
                if(resultSet.next()){
                    caja = new Caja(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3));
                }
            }
        }
        return caja;
    }
    // En caso de no encontrar el objeto retorna null
    public Objeto getObjetoConCod(String codigo) throws SQLException{
        System.out.println("Buscando objeto con codigo: "+codigo);  
        Objeto objeto = null;
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            System.out.println("Conexion exitosa");
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM objetos WHERE o_cod = '"+codigo+"'");                
                if(resultSet.next()){
                    System.out.println("Encontrado");
                    objeto = new Objeto(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5),
                    resultSet.getInt(6),resultSet.getInt(7),resultSet.getInt(8),
                    resultSet.getString(9),resultSet.getString(10),resultSet.getString(11),
                    resultSet.getString(12),resultSet.getString(13),resultSet.getInt(14),
                    resultSet.getString(15).charAt(0 ));                
                    System.out.println(objeto);
                }
                return objeto;
            }
        }
        

    }
    // En caso de no encontrar la cuadricula retorna null
    public Cuadricula getCuadriculaConCod(String codigo) throws SQLException{
        Cuadricula cuadricula = null;
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM cuadriculas WHERE cu_cod = '"+codigo+"'");
                if(resultSet.next()){
                    cuadricula = new Cuadricula(resultSet.getString(1), resultSet.getString(2));
                }
            }
        }
        return cuadricula;
    }
    
    public int getCantObjetosEnCaja(String codigo) throws SQLException{
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM objetos WHERE ca_cod_contiene = '"+codigo+"'");
                result.next();
                return result.getInt(1);
            }
                
        } 
    }
    public List<Integer> getCantObjetosXTipo() throws SQLException{
        List <Integer> cantXTipos = new ArrayList<>();
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT o_es,count(o_cod)\n" +
                "FROM objetos\n" +
                "GROUP BY o_es");
                result.next();
                cantXTipos.add(result.getInt(2));
                result.next();
                cantXTipos.add(result.getInt(2));
            }
                
        } 
        return cantXTipos;
    }
    public int getCantPersonas() throws SQLException{
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM personas");
                result.next();
                return result.getInt(1);
            }
                
        } 
    }   
    public int getCantCuadriculas() throws SQLException{
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM cuadriculas");
                result.next();
                return result.getInt(1);
            }
                
        } 
    }
    public int getCantObjetos() throws SQLException{
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM objetos");
                result.next();
                return result.getInt(1);
            }
                
        } 
    }
    public int getCantCajas() throws SQLException{
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM cajas");
                result.next();
                return result.getInt(1);
            }
                
        } 
    }
    public List<Float> getMaxMinAvgPesoObjetos() throws SQLException{
        List<Float> maxMinAvg = new ArrayList<>(); 
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                ResultSet result = statement.executeQuery("SELECT MAX(o_peso),MIN(o_peso),AVG(o_peso) FROM objetos");
                result.next();
                maxMinAvg.add(result.getFloat(1));
                maxMinAvg.add(result.getFloat(2));
                maxMinAvg.add(result.getFloat(3));
            }
                
        }
        return maxMinAvg;
    }
 
    public void insertarObjeto(Objeto o) throws SQLException{
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                String sql = String.format("INSERT INTO objetos "
                                    + "VALUES ('%s','%s','%s',%d,%d,%d,%d,%d,"
                                    + "'%s','%s','%s','%s','%s',"
                                    + "%d,'%c');", o.getCodigo() , o.getNombre(),
                                    o.getTipoExtracion(),
                                    o.getAlto(),o.getLargo(),o.getExpesor(),o.getPeso(),
                                    o.getCantidad(), o.getFechaRegistro(),
                                    o.getDescripcion(),o.getOrigen(),
                                    o.getCuCodAsocia(),o.getCaCodContiene(),
                                    o.getPDniIngresa(),o.getOEs());
                statement.executeUpdate(sql);
            }        
        }
    }
    public void eliminarCaja(String ca_cod) throws SQLException{
        String sql = String.format("DELETE FROM cajas\n" 
        + "WHERE ca_cod='%s';", ca_cod);                    
        try(Connection dbConnection = DriverManager.getConnection(dbUrl, username, password)){            
            try(Statement statement = dbConnection.createStatement()){
                statement.executeUpdate(sql);
            }
        }
    }
    
}