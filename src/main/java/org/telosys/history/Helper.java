package org.telosys.history;

import java.io.File;

public class Helper {

    /**
     * Check whether the history directory exists or not. If not, we create it
     */
    public static void checkHistoryDirectory(){
        File historyDir = new File(Configuration.PATH_PREFIX + File.separator + Configuration.HISTORY_FOLDER_PATH);
        if(!historyDir.exists() || !historyDir.isDirectory()) {
            historyDir.mkdirs();
        }
    }
}
