package aplications.villadiego.datainformation;

public class DBCreator {
    public static final String Name_DB = "Universidad";
    public static final String TABLE_USER = "user";
    public static final String DOCUMENT = "document";
    public static final String NAME = "name";
    public static final String STRATUM = "stratum";
    public static final String SALARY = "salary";
    public static final String EDUCATION = "education";

    public static final String CREATE_TABLE_USER="CREATE TABLE user(" +
            "document TEXT primary key," +
            "name TEXT not null,"+
            "stratum INTEGER not null," +
            "salary REAL not null," +
            "education TEXT not null" +
            ");";
}
