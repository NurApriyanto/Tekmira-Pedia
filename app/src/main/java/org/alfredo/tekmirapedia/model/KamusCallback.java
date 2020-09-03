package org.alfredo.tekmirapedia.model;

import java.util.ArrayList;

public interface KamusCallback {
    void preExecute();
    void postExecute(ArrayList<Kamus> kamuses);
}
