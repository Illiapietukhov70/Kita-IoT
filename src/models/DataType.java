package models;

public enum DataType {
    BOOLEAN,
    INTEGER,
    STRING,
    DOUBLE,
    DATE,
    TIME,
    DATETIME,
    LIST,
    JSON,
    ;

    DataType() {
    }
    DataType getDataType(String dataType) {
        return DataType.valueOf(dataType.toUpperCase());
    }
}
