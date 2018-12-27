package com.itsoul.lab.app.comnponents;

import com.it.soul.lab.sql.SQLExecutor;
import com.it.soul.lab.sql.query.*;
import com.it.soul.lab.sql.query.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class AppRestController {

    @Autowired
    @Qualifier("JDBConnectionPool")
    private SQLExecutor executor;

    @RequestMapping("/findByName")
    public ResponseEntity<String> helloMessage(@RequestParam("name") String name){

        String result = "Not Found!!!";

        if (name.isEmpty() == false){
            Predicate cluse = new Where("name").isLike(name);
            SQLSelectQuery query = new SQLQuery.Builder(QueryType.SELECT)
                    .columns("name", "age")
                    .from("Passenger").where(cluse).build();
            try {
                Table tab = executor.collection(executor.executeSelect(query));
                if (tab.getRows().size() > 0) {
                    result = "Hi There "
                            + tab.getRows().get(0).getProperties().get(0).getValue().toString()
                            + " (age:" + tab.getRows().get(0).getProperties().get(1).getValue().toString() + ")"
                            + "! Welcome.";
                }
            } catch (SQLException e) {
                return ResponseEntity.ok(e.getMessage());
            }

        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/insert")
    public ResponseEntity<String> insert(@RequestParam("name") String name
                    , @RequestParam("age") Integer age
                    , @RequestParam("sex") String sex){

        String result = "";

        Row row = new Row().add("name", name).add("age", age).add("sex", sex);

        SQLInsertQuery query = new SQLQuery.Builder(QueryType.INSERT)
                                    .into("Passenger")
                                    .values(row.getProperties().toArray(new Property[0]))
                                    .build();
        try {
            Integer rs = executor.executeInsert(true, query);
            result = "New ID: " + rs;
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping("/update")
    public ResponseEntity<String> update(@RequestParam("where") String where
            ,@RequestParam("property") String property
            , @RequestParam("value") Object value){

        String result = "";

        Row row = new Row().add(property, value);

        SQLUpdateQuery query = new SQLQuery.Builder(QueryType.UPDATE)
                .set(row.getProperties().toArray(new Property[0]))
                .from("Passenger")
                .where(new Where("name").isEqualTo(where))
                .build();
        try {
            Integer rs = executor.executeUpdate(query);
            result = "Updated: " + (rs > 0 ? "YES" : "NO");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("where") String where){
        String result = "";

        SQLDeleteQuery query = new SQLQuery.Builder(QueryType.DELETE)
                                    .rowsFrom("Passenger")
                                    .where(new Where("name").isEqualTo(where)).build();

        try {
            Integer rs = executor.executeDelete(query);
            result = "Deleted: " + (rs > 0 ? "YES" : "NO");
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }


}
