module cotelab.dupfilefinder2 {
	requires transitive clutilities;
    requires javafx.fxml;
	requires javafx.web;
	requires org.apache.commons.collections4;
	
    opens io.github.alantcote.dupfilefinder2 to javafx.fxml;
    
    exports io.github.alantcote.dupfilefinder2;
    exports io.github.alantcote.dupfilefinder2.beans.property;
    exports io.github.alantcote.dupfilefinder2.beans.value;
    exports io.github.alantcote.dupfilefinder2.javafx.animation;
    exports io.github.alantcote.dupfilefinder2.pipeline;
    exports io.github.alantcote.dupfilefinder2.pipeline.phase;
    exports io.github.alantcote.dupfilefinder2.pipeline.queueing;
    exports io.github.alantcote.dupfilefinder2.treeview;
}