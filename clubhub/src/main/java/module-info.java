module edu.guilford {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens edu.guilford.gui.controllers to javafx.fxml;
    exports edu.guilford;
}
