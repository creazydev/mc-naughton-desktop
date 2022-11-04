module com.github.mcnaughtondesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.github.mcnaughtondesktop to javafx.fxml;
    exports com.github.mcnaughtondesktop;
}
