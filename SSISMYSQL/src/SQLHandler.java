import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;

public class SQLHandler {
    private final String table;
    private final String[] headers;
    private final Connection con;
    String[] Student = {"Student ID", "First Name", "Last Name", "Gender", "Year Level", "Course"};
    String[] Program = {"Program Code", "Program Name", "College"};
    String[] College = {"College Code", "College Name"};


    public SQLHandler(String table, Connection con){
        this.table = table;
        this.headers = Headers();
        this.con = con;
    }


    public String[] Headers(){
        return switch (table) {
            case "student" -> Student;
            case "program" -> Program;
            case "college" -> College;
            default -> new String[0];
        };
    }
    public String[] getHeaders() {
        return headers;
    }

    public String[] SQLHeaders(){
        String[] sqlHeader = new String[headers.length];
        for (int i = 0; i < headers.length; i++){
            sqlHeader[i] = headers[i].toLowerCase().replace(" ", "_");
        }
        return sqlHeader;
    }

    public List<String[]> readSQL(){
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT * FROM ssis." + table;

        try(Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            int colCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                String[] row = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    row[i] = rs.getString(i+1);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public List<String[]> readPageSQL(int page, String search, String sort){
        List<String[]> data = new ArrayList<>();
        String[] sqlHeader = SQLHeaders();
        List<String> validateSort = List.of(sqlHeader);
        if (!validateSort.contains(sort)){
            sort = (sqlHeader[0]);
        }
        int pageSize = 17;
        int offset = (page - 1) * pageSize;

        String sql = "SELECT * FROM ssis." + table + " WHERE CONCAT_WS " + " (' ', " + String.join(",", sqlHeader) + ")" + " LIKE ?" +  " ORDER BY " + sort + " ASC LIMIT ? OFFSET ?";
        System.out.println(sql);
        String keyword = "%" + search + "%";
        System.out.println(keyword);

        try(PreparedStatement pst = con.prepareStatement(sql)){
            pst.setString(1, keyword);
            pst.setInt(2, pageSize);
            pst.setInt(3, offset);

            ResultSet rs = pst.executeQuery();
            int colCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                String[] row = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    row[i] = rs.getString(i+1);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public  void addtoDb(String[] newData){
        String[] sqlH = SQLHeaders();
        String placeholder = String.join(",", nCopies(sqlH.length, "?"));
        String sql = "INSERT INTO ssis." + table + " (" + String.join(",", sqlH) + ") VALUES (" + placeholder + ")";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            for (int i = 0; i < newData.length; i++) {
                pst.setString(i + 1, newData[i]);
            }
            int k = pst.executeUpdate();
            if (k == 1) {
                readSQL();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDb(String[] updatedData, String id){
        String[] sqlH = SQLHeaders();
        String idColumn = sqlH[0];
        StringBuilder sb = new StringBuilder();
        for (String col : sqlH) {
            sb.append(col).append(" = ?, ");
        }
        sb.setLength(sb.length()-2);

        String sql = "UPDATE ssis." + table + " SET " + sb + " WHERE " + idColumn + " = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)){
            for (int i = 0; i < updatedData.length; i++) {
                pst.setString(i+1, updatedData[i]);
            }
            pst.setString(updatedData.length + 1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalCount(){
        try {
            PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM ssis." + table);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count students: " + e.getMessage(), e);
        }
        return 0;
    }

    public void deletefromDb(String id) {
        String[] sqlH = SQLHeaders();
        String idColumn = sqlH[0];

        String sql = "DELETE FROM ssis." + table + " WHERE " + idColumn + " = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
