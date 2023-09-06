module com.hdct.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;

    opens com.hdct.supermarket to javafx.fxml;
    exports com.hdct.supermarket;
    exports com.hdct.supermarket.controller;
    exports  com.hdct.supermarket.conf;
    opens com.hdct.supermarket.controller to javafx.fxml;
    opens com.hdct.supermarket.pojo to javafx.base;
    exports com.hdct.supermarket.pojo;
    opens com.hdct.supermarket.service to javafx.base;
    exports com.hdct.supermarket.service;
//    opens com.hdct.supermarket.conf to javafx.base;
//    exports com.hdct.supermarket.conf;
}