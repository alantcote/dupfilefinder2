module cotelab {
	requires transitive clutilities;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires transitive javafx.graphics;
	requires transitive javafx.base;
	requires java.desktop;
	
    opens cotelab.dupfilefinder2 to javafx.fxml;
    
    exports cotelab.dupfilefinder2;
    exports cotelab.dupfilefinder2.pipeline;
}