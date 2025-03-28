module edu.guilford {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.net.http; // Add this for HTTP/HTTPS support in Java 11+
    requires org.json; // JSON support

    opens edu.guilford.gui.controllers to javafx.fxml;
    exports edu.guilford;
}
