module cotelab.dupfilefinder2 {
	requires transitive clutilities;
	requires java.desktop;
	requires transitive javafx.base;
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires transitive javafx.graphics;
    requires javafx.media;
	requires javafx.web;
	requires transitive java.prefs;
	requires org.apache.commons.collections4;
	
    opens cotelab.dupfilefinder2 to javafx.fxml;
    
    exports cotelab.dupfilefinder2;
    exports cotelab.dupfilefinder2.beans.property;
    exports cotelab.dupfilefinder2.beans.value;
    exports cotelab.dupfilefinder2.pipeline;
    exports cotelab.dupfilefinder2.pipeline.phase;
    exports cotelab.dupfilefinder2.pipeline.queueing;
    exports cotelab.dupfilefinder2.treeview;
}