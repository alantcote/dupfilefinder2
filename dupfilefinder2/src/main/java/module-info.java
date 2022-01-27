module cotelab.dupfilefinder2 {
	requires transitive clutilities;
    requires javafx.fxml;
	requires javafx.web;
	requires org.apache.commons.collections4;
	
    opens cotelab.dupfilefinder2 to javafx.fxml;
    
    exports cotelab.dupfilefinder2;
    exports cotelab.dupfilefinder2.beans.property;
    exports cotelab.dupfilefinder2.beans.value;
    exports cotelab.dupfilefinder2.javafx.animation;
    exports cotelab.dupfilefinder2.pipeline;
    exports cotelab.dupfilefinder2.pipeline.phase;
    exports cotelab.dupfilefinder2.pipeline.queueing;
    exports cotelab.dupfilefinder2.treeview;
}