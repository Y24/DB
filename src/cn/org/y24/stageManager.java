/*
 * Copyright (c) 2019.
 *  Author: Y24
 *  All rights reserved.
 */

package cn.org.y24;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

class stageManager extends baseManager<sceneManager> {
    private LinkedList<sceneManager> currentSceneManagers = new LinkedList<>();
    private ArrayList<deliverer> messageBox = new ArrayList<>();
    public void sendBroadcastMessage(int senderID,Object message){
        messageBox.add(new deliverer(senderID,deliverer.broadcastFlag,message));
    }
    public Collection<deliverer> receiveBroadcastMessage() {
        Collection<deliverer> delivererCollection = new ArrayList<>();
        for (deliverer message : messageBox) {
            if (message.getReceiverHahCode() == deliverer.broadcastFlag) {
                delivererCollection.add(message);
            }
        }
        return delivererCollection;
    }
    boolean showAdditional(String sceneManagerName) {
        if (get(sceneManagerName) != null) {
            currentSceneManagers.add(get(sceneManagerName));
            get(sceneManagerName).getOwnerStage().show();
            return true;
        }
        System.err.println("cannot show the Stage associated with sceneManager named " + sceneManagerName + ".");
        return false;
    }

    boolean closeNewest() {
        if (currentSceneManagers != null) {
            currentSceneManagers.removeLast().getOwnerStage().close();
            return true;
        } else {
            System.err.println("No newest Stage can be selected.");
            return false;
        }

    }

    void convertTo(String sceneManagerName) {
        closeNewest();
        showAdditional(sceneManagerName);
    }

    void showOnly(String sceneManagerName) {
        closeAll();
        showAdditional(sceneManagerName);

    }

    void closeAll() {
        for (sceneManager each : currentSceneManagers) {
            each.getOwnerStage().close();
        }
        //Note: after all Stages have been closed,the program tends to die,so the following code is apparently unreachable.
        currentSceneManagers.clear();
    }
}
