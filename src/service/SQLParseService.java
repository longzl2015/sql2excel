package service;

import entity.ColumnDefinitionEx;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.poi.util.IOUtils;
import util.ConfigUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoul
 * @since 2017-01-18-19:01
 */
public class SQLParseService {
    protected final static Logger logger = LogManager.getLogger();

    //todo something
    List<List<String>> parse() {
        logger.info("start parse sql...");
        List<ColumnDefinitionEx> columnDefinitionExList = new ArrayList<>();
        InputStream inputStream = null;

        //get column name,type
        try {
            inputStream = new FileInputStream(ConfigUtil.getSqlPath());
            CreateTable createTable = (CreateTable) CCJSqlParserUtil.parse(inputStream);
            List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();

            // 将 ColumnDefinition 转为 ColumnDefinitionEx
            for (ColumnDefinition columnDefinition : columnDefinitions) {
                columnDefinitionExList.add(new ColumnDefinitionEx(columnDefinition));
            }
        } catch (FileNotFoundException | JSQLParserException e) {
            e.printStackTrace();
        }

        //get column chinese name
        addColumnDetail(columnDefinitionExList);

        return columnDefinitionExToList(columnDefinitionExList);
    }

    private void addColumnDetail(List<ColumnDefinitionEx> columnDefinitionExList) {
        @SuppressWarnings("unchecked")
        Map<String, String> columnDetailMap = new HashMap();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ConfigUtil.getSqlCommentPath()));
            String line = reader.readLine();
            while (line != null) {
                Pattern pattern = Pattern.compile("(.*)(\".*\")( *(?i)is *)(\'.*\')");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find() && matcher.groupCount() == 4) {
                    columnDetailMap.put(matcher.group(2).replace("\"", ""), matcher.group(4).replace("\'", ""));
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ColumnDefinitionEx columnDefinitionEx : columnDefinitionExList) {
            String colName = columnDefinitionEx.getColumnName();
            if (columnDetailMap.containsKey(colName)) {
                columnDefinitionEx.setColumnNameDetail(columnDetailMap.get(colName));
            }
        }
    }

    private List<List<String>> columnDefinitionExToList(List<ColumnDefinitionEx> columnDefinitionices) {

        List<List<String>> listList = new ArrayList<>();

        //for first head
        List<String> headHist = new ArrayList<>();
        headHist.add("字段英文名");
        headHist.add("类型及长度");
        headHist.add("中文字段名");
        listList.add(headHist);

        //data body
        for (ColumnDefinitionEx columnDefinitionice : columnDefinitionices) {
            List<String> dataList = new ArrayList<>();
            dataList.add(columnDefinitionice.getColumnName());
            dataList.add(columnDefinitionice.getColDataType().toString());
            dataList.add(columnDefinitionice.getColumnNameDetail());
            listList.add(dataList);
        }
        return listList;
    }


}
