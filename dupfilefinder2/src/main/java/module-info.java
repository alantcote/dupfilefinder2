module cotelab {
	requires transitive clutilities;
	requires java.desktop;
	requires transitive javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
	requires transitive javafx.graphics;
    requires javafx.media;
	requires javafx.web;
	
    opens cotelab.dupfilefinder2 to javafx.fxml;
    
    exports cotelab.dupfilefinder2;
    exports cotelab.dupfilefinder2.pipeline;
}