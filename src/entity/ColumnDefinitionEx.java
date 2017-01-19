package entity;

import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

/**
 * @author zhoul
 * @since 2017-01-19-12:45
 */
public class ColumnDefinitionEx {
    private String columnName;
    private ColDataType colDataType;
    private String columnNameDetail;

    public ColumnDefinitionEx() {
    }

    public ColumnDefinitionEx(String columnName, ColDataType colDataType, String columnNameDetail) {
        this.columnName = columnName;
        this.colDataType = colDataType;
        this.columnNameDetail = columnNameDetail;
    }

    public ColumnDefinitionEx(ColumnDefinition columnDefinition) {
        this.columnName = columnDefinition.getColumnName().replace("\"","");
        this.colDataType = columnDefinition.getColDataType();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColDataType getColDataType() {
        return colDataType;
    }

    public void setColDataType(ColDataType colDataType) {
        this.colDataType = colDataType;
    }

    public String getColumnNameDetail() {
        return columnNameDetail;
    }

    public void setColumnNameDetail(String columnNameDetail) {
        this.columnNameDetail = columnNameDetail;
    }

    @Override
    public String toString() {
        return "ColumnDefinitionEx{" +
                "columnName='" + columnName + '\'' +
                ", colDataType=" + colDataType +
                ", columnNameDetail='" + columnNameDetail + '\'' +
                '}';
    }
}
